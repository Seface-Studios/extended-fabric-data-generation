package net.sefacestudios.testmod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMod implements ModInitializer {
  public static final String MOD_ID = "testmod";
  public static final String MOD_NAME = "Test Mod";
  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

  @Override
  public void onInitialize() {}

  public static ResourceLocation id(String path) {
    return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
  }

  public static <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> registry, String path) {
    return ResourceKey.create(registry, TestMod.id(path));
  }
}
