package com.dragn0007.giddypigs.items;

import com.dragn0007.giddypigs.GiddyGuineaPigs;
import com.dragn0007.giddypigs.blocks.GGPBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.dragn0007.giddypigs.items.GGPItemGroup.GPPItemGroup.CREATIVE_MODE_TABS;

public class GGPItemGroup {

    public class GPPItemGroup {

        public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
                DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GiddyGuineaPigs.MODID);

        public static final RegistryObject<CreativeModeTab> GIDDY_PIGS = CREATIVE_MODE_TABS.register("giddy_pigs",
                () -> CreativeModeTab.builder().icon(() -> new ItemStack(GGPItems.GIDDY_PIGS.get())).title(Component.translatable("itemGroup.giddy_pigs"))
                        .displayItems((displayParameters, output) -> {

                            output.accept(GGPItems.GUINEA_PIG_SPAWN_EGG.get());
                            output.accept(GGPBlocks.ENCLOSURE_PANE.get());
                            output.accept(GGPBlocks.ENCLOSURE_PANE_CORNER.get());
                            output.accept(GGPBlocks.GUINEA_PIG_HOUSE.get());
                            output.accept(GGPBlocks.WATER_BOTTLE.get());
                            output.accept(GGPBlocks.FOOD_DISH_BLACK.get());
                            output.accept(GGPBlocks.FOOD_DISH_BROWN.get());
                            output.accept(GGPBlocks.FOOD_DISH_WHITE.get());

                        }).build());
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
