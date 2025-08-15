package net.sefacestudios.datagenextras.core.modifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

public class ForgedBiomeModifierBiome {
  public static Codec<ForgedBiomeModifierBiome> CODEC = Codec.STRING.comapFlatMap(ForgedBiomeModifierBiome::read, ForgedBiomeModifierBiome::getId).stable();

  private final String id;

  private ForgedBiomeModifierBiome(String id) {
    this.id = id;
  }

  public static ForgedBiomeModifierBiome create(String id) {
    return new ForgedBiomeModifierBiome(id);
  }

  public String getId() {
    return this.id;
  }

  public static DataResult<ForgedBiomeModifierBiome> read(String id) {
    return DataResult.success(new ForgedBiomeModifierBiome(id));
  }
}
