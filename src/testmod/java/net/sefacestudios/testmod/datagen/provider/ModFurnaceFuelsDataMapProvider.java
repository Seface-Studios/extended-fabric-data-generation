package net.sefacestudios.testmod.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.sefacestudios.datagen_extras.data_maps.FurnaceFuelsDataMap;
import net.sefacestudios.datagen_extras.provider.neoforge.FurnaceFuelsDataMapProvider;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModFurnaceFuelsDataMapProvider extends FurnaceFuelsDataMapProvider {
  public ModFurnaceFuelsDataMapProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(output, registryLookup);
  }

  @Override
  public void generate(HolderLookup.Provider registryLookup, Consumer<FurnaceFuelsDataMap> consumer) {
    this.addFuel(Items.GLOW_BERRIES, 20);
    this.addFuel(Blocks.STONECUTTER.asItem(), 5);
  }
}
