package net.sefacestudios.testmod.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.sefacestudios.datagenextras.core.modifiers.ForgedBiomeModifier;
import net.sefacestudios.datagenextras.core.modifiers.ForgedBiomeModifierTypes;
import net.sefacestudios.datagenextras.fabric.api.datagen.v1.provider.ForgeBiomeModifierProvider;
import net.sefacestudios.datagenextras.core.modifiers.ForgedBiomeModifierHolder;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModForgeBiomeModifierProvider extends ForgeBiomeModifierProvider {

  public ModForgeBiomeModifierProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(output, registryLookup);
  }

  @Override
  public void generate(HolderLookup.Provider lookup, Consumer<ForgedBiomeModifierHolder> gen) {
    ForgedBiomeModifier.Builder.biomeModifier(this.loader)
      .type(ForgedBiomeModifierTypes.ADD_FEATURES)
      .biomes(HolderSet.empty())
      .feature(VegetationFeatures.BAMBOO_NO_PODZOL)
      .step(GenerationStep.Decoration.VEGETAL_DECORATION)
      .save(gen);

    ForgedBiomeModifier.Builder.biomeModifier(this.loader)
      .type(ForgedBiomeModifierTypes.REMOVE_FEATURES)
      .biomes(HolderSet.empty())
      .feature(VegetationFeatures.FLOWER_PALE_GARDEN)
      .step(GenerationStep.Decoration.VEGETAL_DECORATION)
      .save(gen);
  }
}
