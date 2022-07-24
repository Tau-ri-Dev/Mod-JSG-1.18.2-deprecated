package dev.tauri.jsgcore.stargate.merging;

import dev.tauri.jsgcore.JSGCore;
import dev.tauri.jsgcore.block.stargate.StargateAbstractMemberBlock;
import dev.tauri.jsgcore.tileentity.StargateAbstractBaseTile;
import dev.tauri.jsgcore.tileentity.StargateAbstractMemberTile;
import dev.tauri.jsgcore.utils.BlockHelpers;
import dev.tauri.jsgcore.utils.FacingToRotation;
import dev.tauri.jsgcore.utils.JSGAxisBox;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public abstract class StargateAbstractMergeHelper {
    @Nonnull
    public abstract List<BlockPos> getRingBlocks();
    @Nonnull
    public abstract List<BlockPos> getChevronBlocks();

    private BlockPos topBlock = null;

    public BlockPos getTopBlock() {
        // null - not initialized
        if (topBlock == null) topBlock = BlockHelpers.getHighest(getChevronBlocks());

        // Still null - chevron list empty (Orlin's gate)
        if (topBlock == null) topBlock = BlockHelpers.getHighest(getRingBlocks());

        return topBlock;
    }

    @Nonnull
    public List<BlockPos> getAbsentBlockPositions(Level world, BlockPos basePos, Direction facing, boolean chevron) {
        List<BlockPos> blocks = getRingBlocks();

        if(chevron)
            blocks = getChevronBlocks();

        return blocks.stream().map(pos -> pos.rotate(FacingToRotation.get(facing)).add(basePos)).filter(pos -> !matchMember(world.getBlockState(pos))).collect(Collectors.toList());
    }

    public abstract JSGAxisBox getBaseSearchBox();
    public abstract boolean matchBase(BlockState state);
    public abstract boolean matchMember(BlockState state);
    public abstract StargateAbstractMemberBlock getMemberBlock();

    @Nullable
    public StargateAbstractBaseTile findBaseTile(Level blockAccess, BlockPos memberPos, Direction facing) {
        JSGAxisBox globalBox = getBaseSearchBox().rotate(facing).offset(memberPos);

        for (BlockPos.MutableBlockPos pos : BlockPos.MutableBlockPos.getA (globalBox.getMinBlockPos(), globalBox.getMaxBlockPos())) {
            if (matchBase(blockAccess.getBlockState(pos))) {
                return (StargateAbstractBaseTile) blockAccess.getTileEntity(pos.toImmutable());
            }
        }

        return null;
    }
    protected abstract boolean checkMemberBlock(Level blockAccess, BlockPos pos, Direction facing, boolean chevron);

    public boolean checkBlocks(Level blockAccess, BlockPos basePos, Direction baseFacing) {
        for (BlockPos pos : getRingBlocks()) {
            if (!checkMemberBlock(blockAccess, pos.rotate(FacingToRotation.get(baseFacing)).add(basePos), baseFacing, EnumMemberVariant.RING))
                return false;
        }

        for (BlockPos pos : getChevronBlocks()) {
            if (!checkMemberBlock(blockAccess, pos.rotate(FacingToRotation.get(baseFacing)).add(basePos), baseFacing, EnumMemberVariant.CHEVRON))
                return false;
        }

        return true;
    }

    protected abstract void updateMemberMergeStatus(Level world, BlockPos checkPos, BlockPos basePos, Direction baseFacing, boolean shouldBeMerged);

    public void updateMembersMergeStatus(Level world, BlockPos basePos, Direction baseFacing, boolean shouldBeMerged) {
        for (BlockPos pos : getRingBlocks())
            updateMemberMergeStatus(world, pos, basePos, baseFacing, shouldBeMerged);

        for (BlockPos pos : getChevronBlocks())
            updateMemberMergeStatus(world, pos, basePos, baseFacing, shouldBeMerged);
    }

    public void updateMembersBasePos(Level blockAccess, BlockPos basePos, Direction baseFacing) {
    }

    public void tryMerge(Level level, BlockPos pos, BlockState state){
        if(!level.isClientSide()){
            BlockEntity tile = level.getBlockEntity(pos);
            if(tile instanceof StargateAbstractBaseTile){
                StargateAbstractBaseTile baseTile = (StargateAbstractBaseTile) tile;
                // check gate structure
            }
            if(tile instanceof StargateAbstractMemberTile){
                StargateAbstractMemberTile memberTile = (StargateAbstractMemberTile) tile;
                StargateAbstractBaseTile baseTile = memberTile.getBaseTile();
                // check gate structure

            }
        }
    }
}
