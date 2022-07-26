package dev.tauri.jsgcore.tileentity;

import dev.tauri.jsgcore.block.stargate.StargateAbstractMemberBlock;
import dev.tauri.jsgcore.block.stargate.chevron.StargateAbstractChevronBlock;
import dev.tauri.jsgcore.block.stargate.ring.StargateAbstractRingBlock;
import dev.tauri.jsgcore.stargate.merging.StargateAbstractMergeHelper;
import dev.tauri.jsgcore.utils.FacingToRotation;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static dev.tauri.jsgcore.block.RotatableBlock.FACING;
import static dev.tauri.jsgcore.block.stargate.StargateAbstractMemberBlock.MERGED;

public abstract class StargateAbstractMemberTile extends BlockEntity {
    public boolean isMerged = false;

    public BlockPos basePos = null;

    public final StargateAbstractMemberBlock memberBlockType;

    public StargateAbstractMemberTile(BlockPos blockPos, BlockState blockState) {
        super(((StargateAbstractMemberBlock) blockState.getBlock()).getRegisteredTile(), blockPos, blockState);
        memberBlockType = (StargateAbstractMemberBlock) blockState.getBlock();
        setChanged();
        getBaseTile();
    }

    @Nullable
    public StargateAbstractBaseTile getBaseTile(){
        if(level == null) return null;
        if(level.getBlockState(worldPosition).isAir()) return null;
        StargateAbstractBaseTile baseTile = getMergeHelper().findBaseTile(level, worldPosition, level.getBlockState(worldPosition).getValue(FACING));
        if(baseTile == null) return null;
        // just in case that basePos was null
        setBasePos(baseTile.getPos());
        return baseTile;
    }

    public abstract StargateAbstractChevronBlock getChevronBlock();
    public abstract StargateAbstractRingBlock getRingBlock();
    public abstract StargateAbstractMergeHelper getMergeHelper();


    @Nullable
    public BlockPos getBasePos(){
        return basePos;
    }

    public void setBasePos(BlockPos basePos){
        this.basePos = basePos;
        setChanged();
    }

    public void onBreak(){
        setMerged(false);
        StargateAbstractBaseTile baseTile = getBaseTile();
        if(baseTile != null)
            baseTile.onBreak();
    }

    public void setMerged(boolean merged){
        this.isMerged = merged;
        if(level != null && !level.getBlockState(worldPosition).isAir()) {
            level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(MERGED, merged), 2);
        }
        setChanged();
    }


    // ----------------------------------
    // NBT TAGS

    // load nbt
    @Override
    public void load(@NotNull CompoundTag compound){
        super.load(compound);
        isMerged = compound.getBoolean("merged");
        getBaseTile();
    }

    // save nbt
    @Override
    protected void saveAdditional(@NotNull CompoundTag compound) {
        compound.putBoolean("merged", isMerged);
        super.saveAdditional(compound);
    }
}
