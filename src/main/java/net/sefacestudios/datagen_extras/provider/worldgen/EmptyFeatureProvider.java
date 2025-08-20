package net.sefacestudios.datagen_extras.provider.worldgen;

import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;

public class EmptyFeatureProvider extends FeatureProvider<DefaultFeatureConfig> {

  protected EmptyFeatureProvider(Feature<DefaultFeatureConfig> feature) {
    super(feature);
  }

  @Override
  protected void placed(List<PlacementModifier> modifiers) {}

  @Override
  protected DefaultFeatureConfig configuration() {
    return new DefaultFeatureConfig();
  }
}
