package dev.tauri.jsgcore.block.stargate;

import dev.tauri.jsgcore.block.RotatableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import static dev.tauri.jsgcore.block.stargate.base.StargateAbstractBaseBlock.MERGED;

public abstract class StargateAbstractMemberBlock extends RotatableBlock implements EntityBlock {
    public StargateAbstractMemberBlock(Properties properties) {
        super(properties.explosionResistance(60f).requiresCorrectToolForDrops());
    }

    @Override
    public BlockState onDefaultStateRegister(BlockState state){
        state.setValue(MERGED, false);
        return super.onDefaultStateRegister(state);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return null;
    }

    public abstract BlockEntityType getRegisteredTile();
}
