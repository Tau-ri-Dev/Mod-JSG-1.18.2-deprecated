package dev.tauri.jsgcore.block.stargate;

import dev.tauri.jsgcore.block.RotatableBlock;
import dev.tauri.jsgcore.tileentity.StargateAbstractBaseTile;
import dev.tauri.jsgcore.tileentity.StargateAbstractMemberTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public abstract class StargateAbstractMemberBlock extends RotatableBlock implements EntityBlock {
    public StargateAbstractMemberBlock(Properties properties) {
        super(properties.explosionResistance(60f).requiresCorrectToolForDrops());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return null;
    }

    @Override
    public void setPlacedBy(@Nonnull Level level, @Nonnull BlockPos blockPos, @Nonnull BlockState blockState, @Nullable LivingEntity placedBy, @Nonnull ItemStack itemStack) {
        if(!level.isClientSide()){
            final StargateAbstractMemberTile memberTile = (StargateAbstractMemberTile) level.getBlockEntity(blockPos);
            final Direction facing = level.getBlockState(blockPos).getValue(FACING);

            if(memberTile != null) {
                final StargateAbstractBaseTile baseTile = memberTile.getBaseTile();
                if(baseTile != null) {
                    baseTile.updateMergeState(memberTile.getMergeHelper().checkBlocks(level, baseTile.getPos(), facing), level.getBlockState(baseTile.getPos()).getValue(FACING));
                }
            }
        }
        super.setPlacedBy(level, blockPos, blockState, placedBy, itemStack);
    }

    public abstract BlockEntityType getRegisteredTile();
}
