package net.sefacestudios.datagen_extras.utils;

public enum ForgedModLoaders {
  FORGE(0, "forge", "Minecraft Forge"),
  NEOFORGE(1, "neoforge", "NeoForge");

  private final int index;
  private final String id;
  private final String name;

  ForgedModLoaders(int index, String id, String name) {
    this.index = index;
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public boolean is(ForgedModLoaders expect) {
    return this == expect;
  }

  public boolean is(String expectId) {
    return this.id.equals(expectId);
  }
}
