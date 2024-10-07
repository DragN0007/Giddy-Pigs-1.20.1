package com.dragn0007.giddypigs.datagen.biglooter;

import com.dragn0007.giddypigs.blocks.GGPBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class GGPBlockLootTables extends BlockLootSubProvider {

    public GGPBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }
    @Override
    protected void generate() {
        this.dropSelf(GGPBlocks.GUINEA_PIG_HOUSE.get());
        this.dropSelf(GGPBlocks.ENCLOSURE_PANE.get());
        this.dropSelf(GGPBlocks.ENCLOSURE_PANE_CORNER.get());
        this.dropSelf(GGPBlocks.FOOD_DISH_BLACK.get());
        this.dropSelf(GGPBlocks.FOOD_DISH_BROWN.get());
        this.dropSelf(GGPBlocks.FOOD_DISH_WHITE.get());
        this.dropSelf(GGPBlocks.WATER_BOTTLE.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return GGPBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
