package net.sefacestudios.datagen_extras.provider.worldgen;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.ArrayList;
import java.util.List;

public abstract class FeatureProvider<FC extends FeatureConfig> {
  private Registry<ConfiguredFeature<?, ?>> holder;
  private final Feature<FC> feature;
  private List<PlacementModifier> modifiers;

  private RegistryKey<PlacedFeature> placedFeatureKey;
  private RegistryKey<ConfiguredFeature<?, ?>> configuredFeatureKey;

  protected FeatureProvider(Feature<FC> feature) {
    this.feature = feature;
    this.modifiers = new ArrayList<>();
  }

  /**
   * All the placed feature configuration for this feature.
   * @param modifiers The modifiers;
   */
  protected abstract void placed(List<PlacementModifier> modifiers);

  /**
   * All the configured feature configuration for this feature.
   */
  protected abstract FC configuration();

  @SuppressWarnings("unchecked")
  public <T extends FeatureProvider<?>> T setPlacedFeatureKey(RegistryKey<PlacedFeature> key) {
    this.placedFeatureKey = key;
    return (T) this;
  }

  @SuppressWarnings("unchecked")
  public <T extends FeatureProvider<?>> T setConfiguredFeatureKey(RegistryKey<ConfiguredFeature<?, ?>> key) {
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
}
