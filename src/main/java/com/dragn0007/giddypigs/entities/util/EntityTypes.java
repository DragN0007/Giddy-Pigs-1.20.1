package com.dragn0007.giddypigs.entities.util;

import com.dragn0007.giddypigs.entities.GuineaPig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.dragn0007.giddypigs.GiddyGuineaPigs.MODID;

public class EntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    public static final RegistryObject<EntityType<GuineaPig>> GUINEA_PIG_ENTITY = ENTITY_TYPES.register("guinea_pig_entity",
            () -> EntityType.Builder.of(GuineaPig::new,
                    MobCategory.CREATURE)
                    .sized(0.5f,0.5f)
                    .build(new ResourceLocation(MODID,"guinea_pig").toString()));
}