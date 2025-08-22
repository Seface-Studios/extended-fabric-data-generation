package net.sefacestudios.datagen_extras.data_maps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Block;
import net.sefacestudios.datagen_extras.provider.neoforge.CopperBehavior;

public record CopperBlockBehaviorDataMap(CopperBehavior behavior, Block currentBlock, Block nextOrPreviousBlock) {
  private static final Codec<CopperBehavior> COPPER_FUNC_CODEC = Codec.STRING.flatXmap((name) -> {
    try {
      return DataResult.success(CopperBehavior.valueOf(name));
    } catch (IllegalArgumentException e) {
      return DataResult.error(() -> "Invalid CopperFunc name: " + name);
    }
  }, copperFunc -> DataResult.success(copperFunc.getName()));

  public static Codec<CopperBlockBehaviorDataMap> CODEC = RecordCodecBuilder.create((instance) -> {
    return instance.group(
      COPPER_FUNC_CODEC.fieldOf("behavior").forGetter(CopperBlockBehaviorDataMap::behavior),
      Block.CODEC.fieldOf("currentBlock").forGetter(CopperBlockBehaviorDataMap::currentBlock),
      Block.CODEC.fieldOf("nextOrPreviousBlock").forGetter(CopperBlockBehaviorDataMap::nextOrPreviousBlock)
    ).apply(instance, CopperBlockBehaviorDataMap::new);
  });
}
