package net.sefacestudios.testmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.sefacestudios.datagen_extras.utils.ForgedModLoaders;
import net.sefacestudios.testmod.datagen.provider.ModCompostablesDataMapProvider;
import net.sefacestudios.testmod.datagen.provider.ModForgeBiomeModifierProvider;
import net.sefacestudios.testmod.datagen.provider.ModCopperBehaviorDataMapProvider;
import net.sefacestudios.testmod.datagen.provider.ModFurnaceFuelsDataMapProvider;

public class Main implements DataGeneratorEntrypoint {

  @Override
  public void onInitializeDataGenerator(FabricDataGenerator datagen) {
    final FabricDataGenerator.Pack pack = datagen.createPack();

    pack.addProvider(ModCopperBehaviorDataMapProvider::new);
    pack.addProvider(ModFurnaceFuelsDataMapProvider::new);
    pack.addProvider(ModCompostablesDataMapProvider::new);
    pack.addProvider(ModForgeBiomeModifierProvider::new);
    pack.addProvider((output, registriesFuture) -> new ModForgeBiomeModifierProvider(output, registriesFuture).setForgedModLoader(ForgedModLoaders.NEOFORGE));
  }
}
