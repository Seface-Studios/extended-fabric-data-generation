package net.sefacestudios.datagen_extras.provider.neoforge;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.sefacestudios.datagen_extras.data_maps.CopperBlockBehaviorDataMap;

import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class CopperBehaviorDataMapProvider implements DataProvider {
  private final FabricDataOutput output;
  private final CompletableFuture<HolderLookup.Provider> registryLookup;
  private Consumer<CopperBlockBehaviorDataMap> consumer;

  private PackOutput.PathProvider pathResolver;

  public CopperBehaviorDataMapProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    this.output = output;
    this.registryLookup = registryLookup;
  }

  public abstract void generate(HolderLookup.Provider registryLookup, Consumer<CopperBlockBehaviorDataMap> consumer);

  @Override
  public CompletableFuture<?> run(CachedOutput writer) {
    this.pathResolver = this.output.createPathProvider(PackOutput.Target.DATA_PACK, "data_maps");

    return this.registryLookup.thenCompose(lookup -> {
      final Set<CopperBlockBehaviorDataMap> oxidizableBlocks = Sets.newHashSet();

      this.consumer = oxidizableBlocks::add;

      this.generate(lookup, this.consumer);

      final JsonObject rootOxidizable = new JsonObject();
      final JsonObject valuesOxidizable = new JsonObject();

      final JsonObject rootWaxable = new JsonObject();
      final JsonObject valuesWaxable = new JsonObject();

      for (CopperBlockBehaviorDataMap modifier : oxidizableBlocks) {
        CopperBehavior behavior = modifier.behavior();
        ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(modifier.currentBlock());
        ResourceLocation nextId = BuiltInRegistries.BLOCK.getKey(modifier.nextOrPreviousBlock());

        JsonObject entry = new JsonObject();
        entry.addProperty(behavior.getField(), nextId.toString());

        if (behavior.is(CopperBehavior.OXIDIZABLE)) {
          valuesOxidizable.add(blockId.toString(), entry);
        } else if (behavior.is(CopperBehavior.WAXABLE)) {
          valuesWaxable.add(blockId.toString(), entry);
        }
      }

      rootOxidizable.add("values", valuesOxidizable);
      rootWaxable.add("values", valuesWaxable);

      CompletableFuture<?> oxidizableFuture = DataProvider.saveStable(writer, rootOxidizable, getOutputPath(CopperBehavior.OXIDIZABLE));
      CompletableFuture<?> waxableFuture = DataProvider.saveStable(writer, rootWaxable, getOutputPath(CopperBehavior.WAXABLE));

      return CompletableFuture.allOf(oxidizableFuture, waxableFuture);
    });
  }

  private Path getOutputPath(CopperBehavior func) {
    return pathResolver.json(ResourceLocation.fromNamespaceAndPath("neoforge", func.getFileName()));
  }

  /**
   * Adds a block to have Oxidizable behavior.
   * @param block The current block.
   * @param nextBlock The next block.
   */
  public void addOxidizable(Block block, Block nextBlock) {
    this.consumer.accept(new CopperBlockBehaviorDataMap(CopperBehavior.OXIDIZABLE, block, nextBlock));
  }

  /**
   * Adds a block to have Waxable behavior.
   * @param block The current block.
   * @param nextBlock The next block.
   */
  public void addWaxable(Block block, Block nextBlock) {
    this.consumer.accept(new CopperBlockBehaviorDataMap(CopperBehavior.WAXABLE, block, nextBlock));
  }

  @Override
  public String getName() {
    return "Copper Block Behavior data map";
  }
}
