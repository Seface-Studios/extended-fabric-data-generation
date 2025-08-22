package net.sefacestudios.datagen_extras.provider.worldgen;

import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class EmptyFeatureProvider extends FeatureProvider<NoneFeatureConfiguration> {

  protected EmptyFeatureProvider(net.minecraft.world.level.levelgen.feature.Feature<NoneFeatureConfiguration> feature) {
    super(feature);
  }

  @Override
  protected void placed(List<PlacementModifier> modifiers) {}

  @Override
  protected NoneFeatureConfiguration configuration() {
    return new NoneFeatureConfiguration();
  }
}
