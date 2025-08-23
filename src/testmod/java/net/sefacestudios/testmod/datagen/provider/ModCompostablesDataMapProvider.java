package net.sefacestudios.testmod.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.sefacestudios.datagen_extras.data_maps.CompostableDataMap;
import net.sefacestudios.datagen_extras.provider.neoforge.CompostablesDataMapProvider;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModCompostablesDataMapProvider extends CompostablesDataMapProvider {
  public ModCompostablesDataMapProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(output, registryLookup);
  }

  @Override
  public void generate(HolderLookup.Provider registryLookup, Consumer<CompostableDataMap> consumer) {
    this.addCompostable(Items.ACACIA_BOAT, 0.25F);
    this.addCompostable(Blocks.ACACIA_LOG.asItem(), 1.0F);
  }
}
