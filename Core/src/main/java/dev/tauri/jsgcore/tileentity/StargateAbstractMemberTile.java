package dev.tauri.jsgcore.tileentity;

import dev.tauri.jsgcore.block.stargate.StargateAbstractMemberBlock;
import dev.tauri.jsgcore.block.stargate.chevron.StargateAbstractChevronBlock;
import dev.tauri.jsgcore.block.stargate.ring.StargateAbstractRingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class StargateAbstractMemberTile extends BlockEntity {
    public final StargateAbstractMemberBlock memberBlockType;

    public StargateAbstractMemberTile(BlockPos blockPos, BlockState blockState) {
        super(((StargateAbstractMemberBlock) blockState.getBlock()).getRegisteredTile(), blockPos, blockState);
        memberBlockType = (StargateAbstractMemberBlock) blockState.getBlock();
    }

    @Nullable
    public StargateAbstractBaseTile getBaseTile(){
        return null;
    }

    public abstract StargateAbstractChevronBlock getChevronBlock();
    public abstract StargateAbstractRingBlock getRingBlock();
}
