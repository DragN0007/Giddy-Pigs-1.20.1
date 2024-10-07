package com.dragn0007.giddypigs.blocks.pixel_placement;

import com.dragn0007.giddypigs.blocks.pixel_placement.util.PixelPlacer;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FoodDish extends PixelPlacer {
    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(-2, 0, -2, 2, 1, 2),
            Block.box(-2, 1, -2, 2, 1.5, 2),
            Block.box(-3, 0, -3, 3, 2, -2),
            Block.box(-3, 0, 2, 3, 2, 3),
            Block.box(2, 0, -2, 3, 2, 2),
            Block.box(-3, 0, -2, -2, 2, 2)
    );

    public FoodDish() {
        super(Properties.copy(Blocks.STONE).noOcclusion());
    }

    @Override
    public VoxelShape getVoxelShape(Direction direction) {
        return SHAPE;
    }
}
