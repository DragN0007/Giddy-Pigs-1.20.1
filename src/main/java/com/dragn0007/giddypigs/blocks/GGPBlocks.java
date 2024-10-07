package com.dragn0007.giddypigs.blocks;

import com.dragn0007.giddypigs.GiddyGuineaPigs;
import com.dragn0007.giddypigs.blocks.custom.EnclosurePane;
import com.dragn0007.giddypigs.blocks.custom.EnclosurePaneCorner;
import com.dragn0007.giddypigs.blocks.custom.House;
import com.dragn0007.giddypigs.blocks.pixel_placement.FoodDish;
import com.dragn0007.giddypigs.blocks.pixel_placement.WaterBottle;
import com.dragn0007.giddypigs.blocks.pixel_placement.util.PixelPlacer;
import com.dragn0007.giddypigs.blocks.pixel_placement.util.PixelPlacerContainer;
import com.dragn0007.giddypigs.blocks.pixel_placement.util.PixelPlacerEntity;
import com.dragn0007.giddypigs.blocks.pixel_placement.util.PixelPlacerItem;
import com.dragn0007.giddypigs.items.GGPItemGroup;
import com.dragn0007.giddypigs.items.GGPItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class GGPBlocks {
    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, GiddyGuineaPigs.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES
            = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, GiddyGuineaPigs.MODID);


    public static final RegistryObject<Block> GUINEA_PIG_HOUSE = registerBlock("guinea_pig_house",
            () -> new House());
    public static final RegistryObject<Block> ENCLOSURE_PANE = registerBlock("enclosure_pane",
            () -> new EnclosurePane());
    public static final RegistryObject<Block> ENCLOSURE_PANE_CORNER = registerBlock("enclosure_pane_corner",
            () -> new EnclosurePaneCorner());

    public static final RegistryObject<FoodDish> FOOD_DISH_BLACK = registerPixelPlacer("food_dish_black", FoodDish::new);
    public static final RegistryObject<FoodDish> FOOD_DISH_BROWN = registerPixelPlacer("food_dish_brown", FoodDish::new);
    public static final RegistryObject<FoodDish> FOOD_DISH_WHITE = registerPixelPlacer("food_dish_white", FoodDish::new);
    public static final RegistryObject<WaterBottle> WATER_BOTTLE = registerPixelPlacer("water_bottle", WaterBottle::new);


    public static final RegistryObject<PixelPlacerContainer> PIXEL_PLACER_CONTAINER = BLOCKS.register("pixel_placer_container", PixelPlacerContainer::new);
    public static final RegistryObject<BlockEntityType<PixelPlacerEntity>> PIXEL_PLACER_ENTITY = BLOCK_ENTITIES.register("pixel_placer_container",
            () -> BlockEntityType.Builder.of(PixelPlacerEntity::new, PIXEL_PLACER_CONTAINER.get()).build(null));

    protected static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends PixelPlacer>RegistryObject<T> registerPixelPlacer(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        GGPItems.ITEMS.register("pixel_placement/" + name, () -> new PixelPlacerItem(toReturn.get(), new Item.Properties()));
        return toReturn;
    }

    protected static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        GGPItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
    }
}