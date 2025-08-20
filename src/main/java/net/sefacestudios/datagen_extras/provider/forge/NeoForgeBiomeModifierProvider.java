package net.sefacestudios.datagen_extras.provider.forge;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;
import net.sefacestudios.datagen_extras.utils.ForgedModLoaders;

import java.util.concurrent.CompletableFuture;

public abstract class NeoForgeBiomeModifierProvider extends ForgeBiomeModifierProvider {
  public NeoForgeBiomeModifierProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
    super(output, registryLookup);
    this.loader = ForgedModLoaders.NEOFORGE;
  }
}
