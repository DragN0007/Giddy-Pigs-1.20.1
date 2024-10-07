package com.dragn0007.giddypigs.items;

import com.dragn0007.giddypigs.GiddyGuineaPigs;
import com.dragn0007.giddypigs.entities.util.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GGPItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, GiddyGuineaPigs.MODID);

   //SPAWN EGGS
    public static final RegistryObject<Item> GUINEA_PIG_SPAWN_EGG = ITEMS.register("guinea_pig_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypes.GUINEA_PIG_ENTITY, 0x644f42, 0x493b31, new Item.Properties().stacksTo(64)));

    //MOD ITEM TABS (UNOBTAINABLE)
    public static final RegistryObject<Item> GIDDY_PIGS = ITEMS.register("giddy_pigs",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}