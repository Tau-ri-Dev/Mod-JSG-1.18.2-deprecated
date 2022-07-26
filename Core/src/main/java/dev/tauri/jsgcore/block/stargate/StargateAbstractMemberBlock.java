package dev.tauri.jsgcore.block.stargate;

import dev.tauri.jsgcore.block.RotatableBlock;
import dev.tauri.jsgcore.tileentity.StargateAbstractBaseTile;
import dev.tauri.jsgcore.tileentity.StargateAbstractMemberTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public abstract class StargateAbstractMemberBlock extends RotatableBlock implements EntityBlock {
    public static final BooleanProperty MERGED = BooleanProperty.create("merged");

    public StargateAbstractMemberBlock(Properties properties) {
        super(properties.explosionResistance(60f).requiresCorrectToolForDrops());
        registerDefaultState(this.stateDefinition.any().setValue(MERGED, Boolean.FALSE).setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(MERGED);
        super.createBlockStateDefinition(pBuilder);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        if(pState.getValue(MERGED))
            return RenderShape.INVISIBLE;
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pNewState, boolean pIsMoving) {
        if(!pLevel.isClientSide()) {
            if (pState.getBlock() != pNewState.getBlock()) {
                BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
                if (blockEntity instanceof StargateAbstractMemberTile) {
                    ((StargateAbstractMemberTile) blockEntity).onBreak();

                }
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
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

    /*@Override
    public boolean skipRendering(@NotNull BlockState blockState, @NotNull BlockState blockState1, @NotNull Direction direction) {
        return blockState.getValue(MERGED);
    }*/
}
