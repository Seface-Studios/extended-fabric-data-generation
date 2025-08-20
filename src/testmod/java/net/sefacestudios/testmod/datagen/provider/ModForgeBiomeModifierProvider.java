package net.sefacestudios.testmod.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.sefacestudios.datagen_extras.modifiers.ForgedBiomeModifier;
import net.sefacestudios.datagen_extras.modifiers.ForgedBiomeModifierTypes;
import net.sefacestudios.datagen_extras.provider.forge.ForgeBiomeModifierProvider;
import net.sefacestudios.testmod.TestMod;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModForgeBiomeModifierProvider extends ForgeBiomeModifierProvider {
  private static final RegistryKey<ConfiguredFeature<?, ?>> CUSTOM_FEATURE_1 = TestMod.key(RegistryKeys.CONFIGURED_FEATURE, "custom_feature_1");
  private static final RegistryKey<ConfiguredFeature<?, ?>> CUSTOM_FEATURE_2 = TestMod.key(RegistryKeys.CONFIGURED_FEATURE, "custom_feature_2");
  private static final RegistryKey<ConfiguredFeature<?, ?>> CUSTOM_FEATURE_3 = TestMod.key(RegistryKeys.CONFIGURED_FEATURE, "custom_feature_3");

  public ModForgeBiomeModifierProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
    super(output, registryLookup);
  }

  @Override
  public void generate(RegistryWrapper.WrapperLookup registryLookup, Consumer<ForgedBiomeModifier> consumer) {
    /* Using TagKey<Biome> */
    ForgedBiomeModifier.Builder.biomeModifier(this.loader)
      .type(ForgedBiomeModifierTypes.ADD_FEATURES)
      .biomes(BiomeTags.IS_FOREST)
      .feature(ModForgeBiomeModifierProvider.CUSTOM_FEATURE_1)
      .step(GenerationStep.Feature.VEGETAL_DECORATION)
      .save(consumer);

    /* Using ResourceKey<Biome> */
    ForgedBiomeModifier.Builder.biomeModifier(this.loader)
      .type(ForgedBiomeModifierTypes.ADD_FEATURES)
      .biomes(BiomeKeys.FOREST)
      .feature(ModForgeBiomeModifierProvider.CUSTOM_FEATURE_2)
      .step(GenerationStep.Feature.VEGETAL_DECORATION)
      .save(consumer);

    /* Using ResourceKey<Biome> array */
    ForgedBiomeModifier.Builder.biomeModifier(this.loader)
      .type(ForgedBiomeModifierTypes.ADD_FEATURES)
      .biomes(BiomeKeys.BADLANDS, BiomeKeys.DESERT)
      .feature(ModForgeBiomeModifierProvider.CUSTOM_FEATURE_3)
      .step(GenerationStep.Feature.VEGETAL_DECORATION)
      .save(consumer);
  }
}
