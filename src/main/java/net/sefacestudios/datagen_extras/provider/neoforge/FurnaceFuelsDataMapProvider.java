package net.sefacestudios.datagen_extras.provider.neoforge;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.sefacestudios.datagen_extras.data_maps.FurnaceFuelsDataMap;

import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class FurnaceFuelsDataMapProvider implements DataProvider {
  private final FabricDataOutput output;
  private final CompletableFuture<HolderLookup.Provider> registryLookup;
  private Consumer<FurnaceFuelsDataMap> consumer;

  private PackOutput.PathProvider pathResolver;

  public FurnaceFuelsDataMapProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    this.output = output;
    this.registryLookup = registryLookup;
  }

  public abstract void generate(HolderLookup.Provider registryLookup, Consumer<FurnaceFuelsDataMap> consumer);

  @Override
  public CompletableFuture<?> run(CachedOutput writer) {
    this.pathResolver = this.output.createPathProvider(PackOutput.Target.DATA_PACK, "data_maps/item");

    return this.registryLookup.thenCompose(lookup -> {
      final Set<FurnaceFuelsDataMap> furnaceFuels = Sets.newHashSet();

      this.consumer = furnaceFuels::add;

      this.generate(lookup, this.consumer);

      final JsonObject root = new JsonObject();
      final JsonObject values = new JsonObject();

      for (FurnaceFuelsDataMap modifier : furnaceFuels) {
        Holder<Item> item = modifier.item();
        int burnTime = modifier.burnTime();

        JsonObject entry = new JsonObject();
        entry.addProperty("burn_time", burnTime);

        values.add(item.getRegisteredName(), entry);
      }

      root.add("values", values);

      return DataProvider.saveStable(writer, root, getOutputPath());
    });
  }

  private Path getOutputPath() {
    return pathResolver.json(ResourceLocation.fromNamespaceAndPath("neoforge", "furnace_fuels"));
  }

  /**
   * Registers an item as burn item with specific burn time.
   * @param item The item to be considered fuel.
   * @param burnTime The burn time that this item should have.
   */
  public void addFuel(Item item, int burnTime) {
    this.consumer.accept(new FurnaceFuelsDataMap(item.builtInRegistryHolder(), burnTime));
  }

  @Override
  public String getName() {
    return "(NeoForge only) Data Maps/Furnace Fuels";
  }
}
