package net.sefacestudios.datagen_extras.provider.worldgen;


import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.ArrayList;
import java.util.List;

public abstract class FeatureProvider<FC extends FeatureConfiguration> {
  private HolderGetter<ConfiguredFeature<?, ?>> holder;
  private final Feature<FC> feature;
  private List<PlacementModifier> modifiers;

  private ResourceKey<PlacedFeature> placedFeatureKey;
  private ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureKey;

  protected FeatureProvider(Feature<FC> feature) {
    this.feature = feature;
    this.modifiers = new ArrayList<>();
  }

  /**
   * All the placed features configuration for this features.
   * @param modifiers The modifiers;
   */
  protected abstract void placed(List<PlacementModifier> modifiers);

  /**
   * All the configured features configuration for this features.
   */
  protected abstract FC configuration();

  @SuppressWarnings("unchecked")
  public <T extends FeatureProvider<?>> T setPlacedFeatureKey(ResourceKey<PlacedFeature> key) {
    this.placedFeatureKey = key;
    return (T) this;
  }

  @SuppressWarnings("unchecked")
  public <T extends FeatureProvider<?>> T setConfiguredFeatureKey(ResourceKey<ConfiguredFeature<?, ?>> key) {
    this.configuredFeatureKey = key;
    return (T) this;
  }

  public PlacedFeature getPlacedFeature() {
    this.placed(this.modifiers);
    return new PlacedFeature(this.holder.getOrThrow(this.configuredFeatureKey), this.modifiers);
  }

  public ConfiguredFeature<?, ?> getConfiguredFeature() {
    return new ConfiguredFeature<>(this.feature, this.configuration());
  }

  /**
   * Register the Placed Feature to the provider.
   * @param context The bootstrap context to register the Placed Feature.
   */
  @Deprecated
  public void registerPlaceFeature(BootstrapContext<PlacedFeature> context) {
    this.holder = context.lookup(Registries.CONFIGURED_FEATURE);
    context.register(this.placedFeatureKey, this.getPlacedFeature());
  }

  /**
   * Register the Configured Feature to the provider.
   * @param context The bootstrap context to register the Configured Feature.
   */
  @Deprecated
  public void registerConfiguredFeature(BootstrapContext<ConfiguredFeature<?, ?>> context) {
    context.register(this.configuredFeatureKey, this.getConfiguredFeature());
  }
}
