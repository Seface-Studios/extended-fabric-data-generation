package net.sefacestudios.testmod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMod implements ModInitializer {
  public static final String MOD_ID = "testmod";
  public static final String MOD_NAME = "Test Mod";
  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

  @Override
  public void onInitialize() {}

  public static Identifier id(String path) {
    return Identifier.of(MOD_ID, path);
  }

  public static <T> RegistryKey<T> key(RegistryKey<? extends Registry<T>> registry, String path) {
    return RegistryKey.of(registry, TestMod.id(path));
  }
}
