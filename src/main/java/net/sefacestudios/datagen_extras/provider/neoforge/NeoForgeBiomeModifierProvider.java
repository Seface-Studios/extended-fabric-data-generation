package net.sefacestudios.datagen_extras.provider.neoforge;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.sefacestudios.datagen_extras.provider.forge.ForgeBiomeModifierProvider;
import net.sefacestudios.datagen_extras.utils.ForgedModLoaders;

import java.util.concurrent.CompletableFuture;

public abstract class NeoForgeBiomeModifierProvider extends ForgeBiomeModifierProvider {
  public NeoForgeBiomeModifierProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(output, registryLookup);
    this.loader = ForgedModLoaders.NEOFORGE;
  }
}
