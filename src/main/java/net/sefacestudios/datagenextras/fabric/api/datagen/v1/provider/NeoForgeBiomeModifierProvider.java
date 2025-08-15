package net.sefacestudios.datagenextras.fabric.api.datagen.v1.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.sefacestudios.datagenextras.core.utils.ForgedModLoaders;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public abstract class NeoForgeBiomeModifierProvider extends ForgeBiomeModifierProvider {
  public NeoForgeBiomeModifierProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(output, registryLookup);
    this.loader = ForgedModLoaders.NEOFORGE;
  }
}
