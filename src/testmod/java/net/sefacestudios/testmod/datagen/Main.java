package net.sefacestudios.testmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.sefacestudios.datagen_extras.provider.CrowdinLanguageProvider;
import net.sefacestudios.datagen_extras.utils.ForgedModLoaders;
import net.sefacestudios.testmod.datagen.provider.ModCompostablesDataMapProvider;
import net.sefacestudios.testmod.datagen.provider.ModForgeBiomeModifierProvider;
import net.sefacestudios.testmod.datagen.provider.ModCopperBehaviorDataMapProvider;
import net.sefacestudios.testmod.datagen.provider.ModFurnaceFuelsDataMapProvider;

public class Main implements DataGeneratorEntrypoint {

  @Override
  public void onInitializeDataGenerator(FabricDataGenerator datagen) {
    final FabricDataGenerator.Pack pack = datagen.createPack();

    pack.addProvider(ModCopperBehaviorDataMapProvider::new);
    pack.addProvider(ModFurnaceFuelsDataMapProvider::new);
    pack.addProvider(ModCompostablesDataMapProvider::new);
    pack.addProvider(ModForgeBiomeModifierProvider::new);
    pack.addProvider((o, rf) -> new ModForgeBiomeModifierProvider(o, rf).setForgedModLoader(ForgedModLoaders.NEOFORGE));

    pack.addProvider(CrowdinLanguageProvider::defaultLanguage);
    pack.addProvider((o, rf) -> CrowdinLanguageProvider.language(o, rf, "pt_br", "pt-BR"));
    //pack.addProvider((o, rf) -> CrowdinLanguageProvider.language(o, rf, "be_by", "be"));
    //pack.addProvider((o, rf) -> CrowdinLanguageProvider.language(o, rf, "zn_cn", "zn-CN"));
    //pack.addProvider((o, rf) -> CrowdinLanguageProvider.language(o, rf, "fr_fr", "fr"));
    //pack.addProvider((o, rf) -> CrowdinLanguageProvider.language(o, rf, "de_de", "de"));
    //pack.addProvider((o, rf) -> CrowdinLanguageProvider.language(o, rf, "it_it", "it"));
    //pack.addProvider((o, rf) -> CrowdinLanguageProvider.language(o, rf, "ja_jp", "ja"));
    //pack.addProvider((o, rf) -> CrowdinLanguageProvider.language(o, rf, "pl_pl", "pl"));
    //pack.addProvider((o, rf) -> CrowdinLanguageProvider.language(o, rf, "ru_ru", "ru"));
    //pack.addProvider((o, rf) -> CrowdinLanguageProvider.language(o, rf, "es_es", "es-ES"));
    //pack.addProvider((o, rf) -> CrowdinLanguageProvider.language(o, rf, "tr_tr", "tr"));
    //pack.addProvider((o, rf) -> CrowdinLanguageProvider.language(o, rf, "uk_ua", "uk"));
  }
}
