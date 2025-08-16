package net.sefacestudios.testmod.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.sefacestudios.datagenextras.core.modifiers.ForgedBiomeModifier;
import net.sefacestudios.datagenextras.core.modifiers.ForgedBiomeModifierTypes;
import net.sefacestudios.datagenextras.fabric.api.datagen.v1.provider.forge.ForgeBiomeModifierProvider;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModForgeBiomeModifierProvider extends ForgeBiomeModifierProvider {

  public ModForgeBiomeModifierProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(output, registryLookup);
  }

  @Override
  public void generate(HolderLookup.Provider lookup, Consumer<ForgedBiomeModifier> gen) {
    ForgedBiomeModifier.Builder.biomeModifier(this.loader)
      .type(ForgedBiomeModifierTypes.ADD_FEATURES)
      .biomes(Biomes.PLAINS)
      .feature(VegetationFeatures.BAMBOO_NO_PODZOL)
      .step(GenerationStep.Decoration.VEGETAL_DECORATION)
      .save(gen);

    ForgedBiomeModifier.Builder.biomeModifier(this.loader)
      .type(ForgedBiomeModifierTypes.ADD_FEATURES)
      .biomes(Biomes.FOREST, Biomes.BIRCH_FOREST)
      .feature(VegetationFeatures.FLOWER_PLAIN)
      .step(GenerationStep.Decoration.VEGETAL_DECORATION)
      .save(gen);

    ForgedBiomeModifier.Builder.biomeModifier(this.loader)
      .type(ForgedBiomeModifierTypes.ADD_FEATURES)
      .biomes(BiomeTags.IS_FOREST)
      .feature(VegetationFeatures.FLOWER_DEFAULT)
      .step(GenerationStep.Decoration.VEGETAL_DECORATION)
      .save(gen);
  }
}
