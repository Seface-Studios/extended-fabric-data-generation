package net.sefacestudios.datagen_extras.modifiers;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.sefacestudios.datagen_extras.utils.ForgedModLoaders;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public record ForgedBiomeModifier(
  ForgedBiomeModifierType type,
  Either<TagKey<Biome>, Either<ResourceKey<Biome>, List<ResourceKey<Biome>>>> biomes,
  ResourceLocation features,
  GenerationStep.Decoration step
) {

  public static final Codec<Either<TagKey<Biome>, Either<ResourceKey<Biome>, List<ResourceKey<Biome>>>>> BIOMES_CODEC =
    Codec.either(Codec.STRING.xmap(
        (str) -> {
          if (str.startsWith("#")) {
            ResourceLocation location = ResourceLocation.tryParse(str.substring(1));
            if (location != null) {
              return TagKey.create(Registries.BIOME, location);
            }
          }
          throw new IllegalArgumentException("Invalid tag format: " + str);
        },
        tagKey -> "#" + tagKey.location()
      ),
      Codec.either(ResourceKey.codec(Registries.BIOME), ResourceKey.codec(Registries.BIOME).listOf())
    );

  public static Codec<ForgedBiomeModifier> CODEC = RecordCodecBuilder.create((instance) -> {
    return instance.group(
      ForgedBiomeModifierType.CODEC.fieldOf("type").forGetter(ForgedBiomeModifier::type),
      ForgedBiomeModifier.BIOMES_CODEC.fieldOf("biomes").forGetter(ForgedBiomeModifier::biomes),
      ResourceLocation.CODEC.fieldOf("features").forGetter(ForgedBiomeModifier::features),
      GenerationStep.Decoration.CODEC.fieldOf("step").forGetter(ForgedBiomeModifier::step)
    ).apply(instance, ForgedBiomeModifier::new);
  });

  public ResourceLocation getId() {
    return this.features;
  }

  public static class Builder {
    private final ForgedModLoaders loader;

    private ForgedBiomeModifierType type;
    private Either<TagKey<Biome>, Either<ResourceKey<Biome>, List<ResourceKey<Biome>>>> biomes;
    private ResourceLocation features;
    private GenerationStep.Decoration step;

    public Builder(ForgedModLoaders loader) {
      this.loader = loader;
      this.type = ForgedBiomeModifierTypes.ADD_FEATURES.appendModLoaderPrefix(this.loader);
      this.step = GenerationStep.Decoration.VEGETAL_DECORATION;
    }

    public static Builder biomeModifier(ForgedModLoaders loader) {
      return new Builder(loader);
    }

    public Builder type(ForgedBiomeModifierType value) {
      this.type = value.appendModLoaderPrefix(this.loader);
      return this;
    }

    public Builder biomes(ResourceKey<Biome> value) {
      this.biomes = Either.right(Either.left(value));
      return this;
    }

    @SafeVarargs
    public final Builder biomes(ResourceKey<Biome>... values) {
      List<ResourceKey<Biome>> biomeList = Arrays.asList(values);
      this.biomes = Either.right(Either.right(biomeList));
      return this;
    }

    public Builder biomes(TagKey<Biome> value) {
      this.biomes = Either.left(value);
      return this;
    }

    public Builder features(ResourceKey<ConfiguredFeature<?, ?>> value) {
      this.features = value.location();
      return this;
    }

    public Builder step(GenerationStep.Decoration value) {
      this.step = value;
      return this;
    }

    public ForgedBiomeModifier build() {
      return new ForgedBiomeModifier(this.type, this.biomes, this.features, this.step);
    }

    public void save(Consumer<ForgedBiomeModifier> consumer) {
      ForgedBiomeModifier modifier = this.build();
      consumer.accept(modifier);
    }
  }
}
