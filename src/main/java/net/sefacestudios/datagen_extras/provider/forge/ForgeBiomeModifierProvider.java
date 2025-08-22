package net.sefacestudios.datagen_extras.provider.forge;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.sefacestudios.datagen_extras.modifiers.ForgedBiomeModifier;
import net.sefacestudios.datagen_extras.utils.ForgedModLoaders;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class ForgeBiomeModifierProvider implements DataProvider {
  private final FabricDataOutput output;
  private final CompletableFuture<HolderLookup.Provider> registryLookup;

  private PackOutput.PathProvider pathResolver;
  protected ForgedModLoaders loader = ForgedModLoaders.FORGE;

  public ForgeBiomeModifierProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    this.output = output;
    this.registryLookup = registryLookup;
  }

  public ForgeBiomeModifierProvider setForgedModLoader(ForgedModLoaders loader) {
    this.loader = loader;
    return this;
  }

  public abstract void generate(HolderLookup.Provider registryLookup, Consumer<ForgedBiomeModifier> consumer);

  @Override
  public CompletableFuture<?> run(CachedOutput writer) {
    this.pathResolver = this.output.createPathProvider(PackOutput.Target.DATA_PACK, this.loader.getId() + "/biome_modifier");

    return this.registryLookup.thenCompose(lookup -> {
      final Set<ResourceLocation> identifiers = Sets.newHashSet();
      final Set<ForgedBiomeModifier> modifiers = Sets.newHashSet();

      this.generate(lookup, modifiers::add);

      RegistryOps<JsonElement> ops = lookup.createSerializationContext(JsonOps.INSTANCE);
      final List<CompletableFuture<?>> futures = new ArrayList<>();

      for (ForgedBiomeModifier modifier : modifiers) {
        if (!identifiers.add(modifier.getId())) {
          throw new IllegalStateException("Duplicate biome modifier " + modifier.getId());
        }

        JsonObject biomeModifierJson = ForgedBiomeModifier.CODEC
          .encodeStart(ops, modifier)
          .getOrThrow(IllegalStateException::new).getAsJsonObject();

        futures.add(DataProvider.saveStable(writer, biomeModifierJson, getOutputPath(modifier)));
      }

      return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    });
  }

  private Path getOutputPath(ForgedBiomeModifier modifier) {
    return pathResolver.json(modifier.getId());
  }

  @Override
  public String getName() {
    return this.loader.getName() + " Biome Modifier";
  }
}
