package dev.tauri.jsgcore.tileentity;

import dev.tauri.jsgcore.block.stargate.StargateAbstractMemberBlock;
import dev.tauri.jsgcore.block.stargate.chevron.StargateAbstractChevronBlock;
import dev.tauri.jsgcore.block.stargate.ring.StargateAbstractRingBlock;
import dev.tauri.jsgcore.stargate.merging.StargateAbstractMergeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import static dev.tauri.jsgcore.block.RotatableBlock.FACING;
import static dev.tauri.jsgcore.block.stargate.StargateAbstractMemberBlock.MERGED;

public abstract class StargateAbstractMemberTile extends BlockEntity {
    public boolean isMerged = false;
    public BlockPos basePos = null;
    public final Direction facing;

    public final StargateAbstractMemberBlock memberBlockType;

    public StargateAbstractMemberTile(BlockPos blockPos, BlockState blockState) {
        super(((StargateAbstractMemberBlock) blockState.getBlock()).getRegisteredTile(), blockPos, blockState);
        memberBlockType = (StargateAbstractMemberBlock) blockState.getBlock();
        facing = blockState.getValue(FACING);
        setChanged();
        getBaseTile();
    }

    @Nullable
    public StargateAbstractBaseTile getBaseTile(){
        if(level == null) return null;
        if(getBasePos() == null) return null;
        BlockEntity e = level.getBlockEntity(getBasePos());
        if(!(e instanceof StargateAbstractBaseTile)) return null;
        return (StargateAbstractBaseTile) e;
    }

    public abstract StargateAbstractChevronBlock getChevronBlock();
    public abstract StargateAbstractRingBlock getRingBlock();
    public abstract StargateAbstractMergeHelper getMergeHelper();


    @Nullable
    public BlockPos getBasePos(){
        if(basePos == null){
            final StargateAbstractBaseTile tile = getMergeHelper().findBaseTile(level, worldPosition, facing);
            if(tile != null)
                basePos = tile.getPos();
        }
        return basePos;
    }

    public void setBasePos(BlockPos basePos){
        this.basePos = basePos;
        setChanged();
    }

    public void onBreak(){
    }

    public void setMerged(boolean merged){
        this.isMerged = merged;
        if(level != null && !level.getBlockState(worldPosition).isAir() && !level.isClientSide()) {
            level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(MERGED, merged), 11);
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
