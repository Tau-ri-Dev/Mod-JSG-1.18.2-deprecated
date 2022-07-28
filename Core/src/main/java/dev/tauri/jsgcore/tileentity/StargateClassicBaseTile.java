package dev.tauri.jsgcore.tileentity;

import dev.tauri.jsgcore.render.state.StargateClassicRendererState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
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
