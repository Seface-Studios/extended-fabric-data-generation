package net.sefacestudios.datagen_extras.provider;

import com.crowdin.client.Client;
import com.crowdin.client.core.model.Credentials;
import com.crowdin.client.core.model.ResponseObject;
import com.crowdin.client.sourcestrings.model.ListSourceStringsParams;
import com.crowdin.client.sourcestrings.model.SourceString;
import com.crowdin.client.stringtranslations.model.LanguageTranslations;
import com.crowdin.client.stringtranslations.model.ListLanguageTranslationsOptions;
import com.crowdin.client.stringtranslations.model.PlainLanguageTranslations;
import com.crowdin.client.stringtranslations.model.User;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CrowdinLanguageProvider extends FabricLanguageProvider {
  private final List<User> contributors;

  private final String token = System.getenv("CROWDIN_TOKEN");
  private final Long projectId = Long.valueOf(System.getenv("CROWDIN_PROJECT_ID"));

  private final String crowdinLanguageCode;

  @Nullable
  private final String organization = parseOrganization(System.getenv("CROWDIN_ORGANIZATION"));

  public CrowdinLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup, String mcLanguageCode, String crowdinLanguageCode) {
    super(dataOutput, mcLanguageCode, registryLookup);

    this.crowdinLanguageCode = crowdinLanguageCode;
    this.contributors = new ArrayList<>();
  }

  /**
   * Generates the English (United States) language file with source values from Crowdin.
   */
  public static CrowdinLanguageProvider defaultLanguage(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
    return CrowdinLanguageProvider.defaultLanguage(dataOutput, registryLookup, "en_us");
  }

  /**
   * Generates the language with source values from Crowdin.
   * @param mcLanguageCode The Minecraft language code.
   */
  public static CrowdinLanguageProvider defaultLanguage(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup, String mcLanguageCode) {
    return CrowdinLanguageProvider.language(dataOutput, registryLookup, mcLanguageCode, "source");
  }

  /**
   * Generates the language with source values from Crowdin.
   * @param mcLanguageCode The Minecraft language code.
   * @param crowdinLanguageCode The Crowdin language code.
   */
  public static CrowdinLanguageProvider language(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup, String mcLanguageCode, String crowdinLanguageCode) {
    return new CrowdinLanguageProvider(dataOutput, registryLookup, mcLanguageCode, crowdinLanguageCode);
  }

  @Override
  public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder builder) {
    Credentials credentials = new Credentials(this.token, this.organization);
    Client client = new Client(credentials);

    // Preload all source strings
    Map<Long, Map.Entry<String, String>> sources = new HashMap<>();
    ListSourceStringsParams sourceOptions = ListSourceStringsParams.builder()
      .limit(500)
      .offset(0)
      .build();

    while (true) {
      List<ResponseObject<SourceString>> sourcePage = client.getSourceStringsApi()
        .listSourceStrings(this.projectId, sourceOptions)
        .getData();

      if (sourcePage.isEmpty()) break;

      for (ResponseObject<SourceString> ss : sourcePage) {
        SourceString data = ss.getData();
        Long id = data.getId();
        String key = data.getIdentifier().replaceAll("\"", "");
        String defaultValue = data.getText().toString();

        sources.put(id, Map.entry(key, defaultValue));
      }

      sourceOptions.setOffset(sourceOptions.getOffset() + sourceOptions.getLimit());
    }

    // Paginate all the translations
    ListLanguageTranslationsOptions translationsOptions = new ListLanguageTranslationsOptions();
    translationsOptions.setLimit(500);
    translationsOptions.setOffset(0);

    while (true) {
      List<ResponseObject<LanguageTranslations>> translations = client.getStringTranslationsApi()
        .listLanguageTranslations(this.projectId, "pt-BR", translationsOptions)
        .getData();

      if (translations.isEmpty()) break;

      for (ResponseObject<LanguageTranslations> res : translations) {
        PlainLanguageTranslations translation = (PlainLanguageTranslations) res.getData();

        String key = sources.get(translation.getStringId()).getKey();
        String value = this.crowdinLanguageCode.equals("source")
          ? sources.get(translation.getStringId()).getValue()
          : translation.getText();

        User translator = translation.getUser();

        if (!this.contributors.contains(translator)) {
          this.contributors.add(translator);
        }

        if (key != null && value != null && !value.isBlank()) {
          builder.add(key, value);
        }
      }

      translationsOptions.setOffset(translationsOptions.getOffset() + translationsOptions.getLimit());
    }

    System.out.println("Thanks for the following contributors:");
    for (User contributor : this.contributors) {
      System.out.println(" - " + contributor.getFullName() + " (" + contributor.getUsername() + ")");
    }
  }

  @Nullable
  private static String parseOrganization(String value) {
    if (value.isEmpty() || value.equals("null") || value.equals("none")) {
      return null;
    }

    return value;
  }
}
