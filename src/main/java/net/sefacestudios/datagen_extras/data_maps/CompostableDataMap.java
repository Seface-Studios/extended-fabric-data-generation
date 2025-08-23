package net.sefacestudios.datagen_extras.data_maps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;

public record CompostableDataMap(Holder<Item> item, float chance) {
  public static Codec<CompostableDataMap> CODEC = RecordCodecBuilder.create((instance) -> {
    return instance.group(
      Item.CODEC.fieldOf("item").forGetter(dataMap -> dataMap.item),
      Codec.floatRange(0, 1).fieldOf("chance").forGetter(CompostableDataMap::chance)
    ).apply(instance, CompostableDataMap::new);
  });
}
