package com.dragn0007.giddypigs.blocks.pixel_placement;

import com.dragn0007.giddypigs.blocks.pixel_placement.util.PixelPlacer;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WaterBottle extends PixelPlacer {
    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(-2, 0, -2, 2, 10, 2)
    );

    public WaterBottle() {
        super(Properties.copy(Blocks.GLASS).noOcclusion());
    }

    @Override
    public VoxelShape getVoxelShape(Direction direction) {
        return SHAPE;
    }
}
