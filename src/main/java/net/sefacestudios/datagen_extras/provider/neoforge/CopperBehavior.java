package net.sefacestudios.datagen_extras.provider.neoforge;

/**
 * Represent the Oxidizable or Waxable Copper behaviors.
 */
public enum CopperBehavior {
  OXIDIZABLE(0,  "Oxidizable","block/oxidizables", "next_oxidation_stage"),
  WAXABLE(1 ,  "Waxable","block/waxables","waxed");

  private final int index;
  private final String name;
  private final String fileName;
  private final String field;

  CopperBehavior(int index, String name, String fileName, String field) {
    this.index = index;
    this.name = name;
    this.fileName = fileName;
    this.field = field;
  }

  public boolean is(CopperBehavior func) {
    return this.equals(func);
  }

  public String getFileName() {
    return this.fileName;
  }

  public String getName() {
    return this.name;
  }

  public String getField() {
    return this.field;
  }
}
