package com.dragn0007.giddypigs.util;

import com.dragn0007.giddypigs.GiddyGuineaPigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class GGPTags {

    public static class Items {

        public static final TagKey<Item> GUINEA_PIG_FOOD = tag("guinea_pig_food");

        private static TagKey<Item> tag (String name) {
            return ItemTags.create(new ResourceLocation(GiddyGuineaPigs.MODID, name));
        }
        private static TagKey<Item> forgeTag (String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }

}
