package dev.tauri.jsgcore.block.stargate;

import dev.tauri.jsgcore.block.RotatableBlock;
import dev.tauri.jsgcore.tileentity.StargateAbstractMemberTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

public abstract class StargateAbstractMemberBlock extends RotatableBlock implements EntityBlock {
    public StargateAbstractMemberBlock(Properties properties) {
        super(properties.explosionResistance(60f).requiresCorrectToolForDrops(), true);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return null;
    }

    public abstract BlockEntityType getRegisteredTile();
}
