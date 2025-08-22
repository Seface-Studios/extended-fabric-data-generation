package net.sefacestudios.testmod.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Blocks;
import net.sefacestudios.datagen_extras.data_maps.CopperBlockBehaviorDataMap;
import net.sefacestudios.datagen_extras.provider.neoforge.CopperBehaviorDataMapProvider;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModCopperBehaviorDataMapProvider extends CopperBehaviorDataMapProvider {
  public ModCopperBehaviorDataMapProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(output, registryLookup);
  }

  @Override
  public void generate(HolderLookup.Provider registryLookup, Consumer<CopperBlockBehaviorDataMap> consumer) {
    addOxidizable(Blocks.COPPER_BLOCK, Blocks.WEATHERED_COPPER);
    addOxidizable(Blocks.WEATHERED_COPPER, Blocks.EXPOSED_COPPER);

    addWaxable(Blocks.EXPOSED_COPPER, Blocks.WEATHERED_COPPER);
    addWaxable(Blocks.WEATHERED_COPPER, Blocks.COPPER_BLOCK);
  }
}
