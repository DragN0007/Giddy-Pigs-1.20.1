package com.dragn0007.giddypigs;

import com.dragn0007.giddypigs.blocks.GGPBlocks;
import com.dragn0007.giddypigs.entities.util.EntityTypes;
import com.dragn0007.giddypigs.items.GGPItemGroup;
import com.dragn0007.giddypigs.items.GGPItems;
import com.dragn0007.giddypigs.util.GiddyGuineaPigsCommonConfig;
import com.dragn0007.giddypigs.world.PigSpawnGeneration;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

@Mod(GiddyGuineaPigs.MODID)
public class GiddyGuineaPigs
{
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "giddypigs";

    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, MODID);

    public static final RegistryObject<Codec<PigSpawnGeneration>> SPAWN_CODEC = BIOME_MODIFIER_SERIALIZERS.register("spawn_biome_modifier",
            () -> RecordCodecBuilder.create(builder ->
                    builder.group(Biome.LIST_CODEC.fieldOf("biomes").forGetter(PigSpawnGeneration::biomes)).apply(builder, PigSpawnGeneration::new)));

    public GiddyGuineaPigs()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        GGPItems.register(eventBus);
        GGPBlocks.register(eventBus);
        GGPItemGroup.register(eventBus);
        EntityTypes.ENTITY_TYPES.register(eventBus);

        GeckoLib.initialize();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, GiddyGuineaPigsCommonConfig.SPEC, "guinea_pigs-common.toml");

        MinecraftForge.EVENT_BUS.register(this);
    }
}