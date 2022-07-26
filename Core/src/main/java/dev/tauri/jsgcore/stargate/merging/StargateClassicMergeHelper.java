package dev.tauri.jsgcore.stargate.merging;

import dev.tauri.jsgcore.block.RotatableBlock;
import dev.tauri.jsgcore.tileentity.StargateClassicMemberTile;
import dev.tauri.jsgcore.utils.FacingToRotation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public abstract class StargateClassicMergeHelper extends StargateAbstractMergeHelper{

    @Override
    protected boolean checkMemberBlock(Level blockAccess, BlockPos pos, Direction facing, boolean chevron) {
        BlockState state = blockAccess.getBlockState(pos);

        return matchMember(state, chevron) && state.getValue(RotatableBlock.FACING) == facing;
    }

    @Override
    protected void updateMemberMergeStatus(Level world, BlockPos checkPos, BlockPos basePos, Direction baseFacing, boolean shouldBeMerged, boolean chevron) {
        checkPos = FacingToRotation.rotatePos(checkPos, baseFacing).offset(basePos);
        BlockState state = world.getBlockState(checkPos);

        if (matchMember(state, chevron)) {
            StargateClassicMemberTile memberTile = (StargateClassicMemberTile) world.getBlockEntity(checkPos);

            if (memberTile != null && ((shouldBeMerged && !memberTile.isMerged) || (memberTile.isMerged && memberTile.getBasePos().equals(basePos)))) {

                /*ItemStack camoStack = memberTile.getCamoItemStack();
                if (camoStack != null) {
                    InventoryHelper.spawnItemStack(world, checkPos.getX(), checkPos.getY(), checkPos.getZ(), camoStack);
                }

                if (memberTile.getCamoState() != null) {
                    memberTile.setCamoState(null);
                }*/

                // This also sets merge status
                memberTile.setBasePos(shouldBeMerged ? basePos : null);
                memberTile.setMerged(!shouldBeMerged);
            }
        }
    }

    private void updateMemberBasePos(Level blockAccess, BlockPos pos, BlockPos basePos, Direction baseFacing, boolean chevron) {
        BlockState state = blockAccess.getBlockState(pos);

        if (matchMember(state, chevron)) {
            StargateClassicMemberTile memberTile = (StargateClassicMemberTile) blockAccess.getBlockEntity(pos);
            if(memberTile != null)
                memberTile.setBasePos(basePos);
        }
    }

    @Override
    public void updateMembersBasePos(Level blockAccess, BlockPos basePos, Direction baseFacing) {
        for (BlockPos pos : getRingBlocks())
            updateMemberBasePos(blockAccess, FacingToRotation.rotatePos(pos, baseFacing).offset(basePos), basePos, baseFacing, false);

        for (BlockPos pos : getChevronBlocks())
            updateMemberBasePos(blockAccess, FacingToRotation.rotatePos(pos, baseFacing).offset(basePos), basePos, baseFacing, true);
    }

}
