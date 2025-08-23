package net.sefacestudios.datagen_extras.data_maps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;

public record FurnaceFuelsDataMap(Holder<Item> item, int burnTime) {
  public static Codec<FurnaceFuelsDataMap> CODEC = RecordCodecBuilder.create((instance) -> {
    return instance.group(
      Item.CODEC.fieldOf("item").forGetter(dataMap -> dataMap.item),
      Codec.INT.fieldOf("chance").forGetter(FurnaceFuelsDataMap::burnTime)
    ).apply(instance, FurnaceFuelsDataMap::new);
  });
}
