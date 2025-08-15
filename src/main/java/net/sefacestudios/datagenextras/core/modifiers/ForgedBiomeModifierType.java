package net.sefacestudios.datagenextras.core.modifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.sefacestudios.datagenextras.core.utils.ForgedModLoaders;

public class ForgedBiomeModifierType {
  public static Codec<ForgedBiomeModifierType> CODEC = Codec.STRING.comapFlatMap(ForgedBiomeModifierType::read, ForgedBiomeModifierType::getId).stable();

  private String id;

  private ForgedBiomeModifierType(String id) {
    this.id = id;
  }

  public static ForgedBiomeModifierType create(String id) {
    return new ForgedBiomeModifierType(id);
  }

  public String getId() {
    return this.id;
  }

  public ForgedBiomeModifierType appendModLoaderPrefix(ForgedModLoaders loader) {
    String namespace = loader.getId() + ":";

    if (this.id != null && this.id.contains(":")) {
      this.id = namespace + this.id.substring(this.id.indexOf(":") + 1);
    } else {
      this.id = namespace + this.id;
    }

    return this;
  }

  public static DataResult<ForgedBiomeModifierType> read(String id) {
    return DataResult.success(new ForgedBiomeModifierType(id));
  }
}
