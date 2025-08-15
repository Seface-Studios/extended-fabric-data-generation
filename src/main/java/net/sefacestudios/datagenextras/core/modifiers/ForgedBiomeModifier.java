package net.sefacestudios.datagenextras.core.modifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.sefacestudios.datagenextras.core.utils.ForgedModLoaders;

import java.util.Map;
import java.util.function.Consumer;

public record ForgedBiomeModifier(
  ForgedBiomeModifierType type,
  HolderSet<Biome> biomes,
  ResourceLocation feature,
  GenerationStep.Decoration step) {

  public static Codec<ForgedBiomeModifier> CODEC = RecordCodecBuilder.create((instance) -> {
    return instance.group(
      ForgedBiomeModifierType.CODEC.fieldOf("type").forGetter(ForgedBiomeModifier::type),
      Biome.LIST_CODEC.fieldOf("biomes").forGetter(ForgedBiomeModifier::biomes),
      ResourceLocation.CODEC.fieldOf("feature").forGetter(ForgedBiomeModifier::feature),
      GenerationStep.Decoration.CODEC.fieldOf("step").forGetter(ForgedBiomeModifier::step)
    ).apply(instance, ForgedBiomeModifier::new);
  });

  public static class Builder {
    private ForgedModLoaders loader;

    private ForgedBiomeModifierType type;
    private HolderSet<Biome> biomes;
    private ResourceLocation feature;
    private GenerationStep.Decoration step;

    public Builder(ForgedModLoaders loader) {
      this.loader = loader;
      this.type = ForgedBiomeModifierTypes.ADD_FEATURES.appendModLoaderPrefix(this.loader);
      this.biomes = HolderSet.empty();
      this.step = GenerationStep.Decoration.VEGETAL_DECORATION;
    }

    public static Builder biomeModifier(ForgedModLoaders loader) {
      return new Builder(loader);
    }

    public Builder type(ForgedBiomeModifierType value) {
      this.type = value.appendModLoaderPrefix(this.loader);
      return this;
    }

    public Builder biomes(HolderSet<Biome> value) {
      this.biomes = value;
      return this;
    }

    public Builder feature(ResourceKey<ConfiguredFeature<?, ?>> value) {
      this.feature = value.location();
      return this;
    }

    public Builder step(GenerationStep.Decoration value) {
      this.step = value;
      return this;
    }

    public ForgedBiomeModifierHolder build() {
      return new ForgedBiomeModifierHolder(this.feature, new ForgedBiomeModifier(this.type, this.biomes, this.feature, this.step));
    }

    public ForgedBiomeModifierHolder save(Consumer<ForgedBiomeModifierHolder> consumer) {
      ForgedBiomeModifierHolder holder = this.build();
      consumer.accept(holder);
      return holder;
    }
  }
}
