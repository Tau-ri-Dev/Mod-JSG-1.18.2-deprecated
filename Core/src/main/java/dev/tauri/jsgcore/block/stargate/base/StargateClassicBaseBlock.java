package dev.tauri.jsgcore.block.stargate.base;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public abstract class StargateClassicBaseBlock extends StargateAbstractBaseBlock{
    public StargateClassicBaseBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected BlockState createMemberState(BlockState memberState, Direction facing) {
        return memberState.setValue(FACING, facing);
    }
}
