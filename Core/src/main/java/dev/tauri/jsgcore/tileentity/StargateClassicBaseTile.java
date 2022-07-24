package dev.tauri.jsgcore.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class StargateClassicBaseTile extends StargateAbstractBaseTile {
    public StargateClassicBaseTile(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Override
    public int getSupportedCapacitors(){
        return 3;
    }
}
