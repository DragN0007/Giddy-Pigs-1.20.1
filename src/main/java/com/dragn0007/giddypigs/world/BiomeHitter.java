package com.dragn0007.giddypigs.world;

import com.dragn0007.giddypigs.GiddyGuineaPigs;
import com.dragn0007.giddypigs.entities.util.EntityTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class BiomeHitter {
    public static final ResourceKey<BiomeModifier> SPAWN_PIG_HOT = registerKey("spawn_pig_hot");
    public static final ResourceKey<BiomeModifier> SPAWN_PIG_PLAINS = registerKey("spawn_pig_plains");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);


        context.register(SPAWN_PIG_PLAINS, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_PLAINS),
                List.of(new MobSpawnSettings.SpawnerData(EntityTypes.GUINEA_PIG_ENTITY.get(),
                        4,
                        1,
                        3
                ))));

        context.register(SPAWN_PIG_HOT, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_HOT_OVERWORLD),
                List.of(new MobSpawnSettings.SpawnerData(EntityTypes.GUINEA_PIG_ENTITY.get(),
                        4,
                        1,
                        3
                ))));

    }

    public static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(GiddyGuineaPigs.MODID, name));
    }
}