package com.dragn0007.giddypigs.datagen;

import com.dragn0007.giddypigs.GiddyGuineaPigs;
import com.dragn0007.giddypigs.items.GGPItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class GGPItemModelProvider extends ItemModelProvider {
    public GGPItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, GiddyGuineaPigs.MODID, existingFileHelper);
    }

    @Override
    public void registerModels() {
        simpleItem(GGPItems.COOKED_GUINEA_PIG);
        simpleItem(GGPItems.GUINEA_PIG);
        simpleItem(GGPItems.GUINEA_PIG_HIDE);
    }

    public ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(GiddyGuineaPigs.MODID,"item/" + item.getId().getPath()));
    }
}