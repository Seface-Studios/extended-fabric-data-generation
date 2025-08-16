package net.sefacestudios.datagenextras.fabric.api.datagen.v1.provider.worldgen;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class EmptyFeatureProvider extends FeatureProvider<NoneFeatureConfiguration> {

  protected EmptyFeatureProvider(Feature<NoneFeatureConfiguration> feature) {
    super(feature);
  }

  @Override
  protected void placed(List<PlacementModifier> modifiers) {}

  @Override
  protected NoneFeatureConfiguration configuration() {
    return new NoneFeatureConfiguration();
  }
}
