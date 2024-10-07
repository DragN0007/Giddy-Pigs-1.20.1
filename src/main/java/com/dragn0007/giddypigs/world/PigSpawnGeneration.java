package com.dragn0007.giddypigs.world;

import com.dragn0007.giddypigs.GiddyGuineaPigs;
import com.dragn0007.giddypigs.entities.util.EntityTypes;
import com.dragn0007.giddypigs.util.GiddyGuineaPigsCommonConfig;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

import java.util.List;

public record PigSpawnGeneration(HolderSet<Biome> biomes) implements BiomeModifier {

    public static List<MobSpawnSettings.SpawnerData> GUINEAPIG_SPAWNS = List.of(
            new MobSpawnSettings.SpawnerData(EntityTypes.GUINEA_PIG_ENTITY.get(), GiddyGuineaPigsCommonConfig.GUINEA_PIG_WEIGHT.get(), 1, 4)
    );

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if(phase == Phase.ADD && biomes.contains(biome)) {
            List<MobSpawnSettings.SpawnerData> spawner = builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE);

            if(biome.is(Biomes.SPARSE_JUNGLE) || biome.is(Biomes.MEADOW) || biome.is(Biomes.SAVANNA)) {
                spawner.addAll(GUINEAPIG_SPAWNS);
            }
            System.out.println("Spawned a Guinea Pig at: " + EntityTypes.GUINEA_PIG_ENTITY.get().getDescription());
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return GiddyGuineaPigs.SPAWN_CODEC.get();
    }
}