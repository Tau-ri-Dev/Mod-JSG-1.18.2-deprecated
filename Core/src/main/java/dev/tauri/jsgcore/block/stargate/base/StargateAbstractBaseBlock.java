package dev.tauri.jsgcore.block.stargate.base;

import dev.tauri.jsgcore.block.RotatableBlock;
import dev.tauri.jsgcore.stargate.merging.StargateAbstractMergeHelper;
import dev.tauri.jsgcore.tileentity.StargateAbstractBaseTile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public abstract class StargateAbstractBaseBlock extends RotatableBlock implements EntityBlock {
    public StargateAbstractBaseBlock(Properties properties) {
        super(properties.explosionResistance(60f).requiresCorrectToolForDrops().noOcclusion(), true);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return null;
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level pLevel, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit){
        if(!pLevel.isClientSide()){
            BlockEntity entity = pLevel.getBlockEntity(pos);
            if(entity instanceof StargateAbstractBaseTile){
                NetworkHooks.openGui(((ServerPlayer) player), ((StargateAbstractBaseTile) entity), pos);
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public void onRemove(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pNewState, boolean pIsMoving) {
        if(!pLevel.isClientSide()) {
            if (pState.getBlock() != pNewState.getBlock()) {
                BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
                if (blockEntity instanceof StargateAbstractBaseTile) {
                    ((StargateAbstractBaseTile) blockEntity).drops();
                }
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public void setPlacedBy(@Nonnull Level level, @Nonnull BlockPos blockPos, @Nonnull BlockState blockState, @Nullable LivingEntity placedBy, @Nonnull ItemStack itemStack) {
        if(!level.isClientSide()){
            getMergeHelper().tryMerge(level, blockPos, blockState);
        }
        super.setPlacedBy(level, blockPos, blockState, placedBy, itemStack);
    }

    public abstract StargateAbstractMergeHelper getMergeHelper();


    // copy from mc class
    public boolean triggerEvent(@NotNull BlockState p_49226_, @NotNull Level p_49227_, @NotNull BlockPos p_49228_, int p_49229_, int p_49230_) {
        super.triggerEvent(p_49226_, p_49227_, p_49228_, p_49229_, p_49230_);
        BlockEntity blockentity = p_49227_.getBlockEntity(p_49228_);
        return blockentity != null && blockentity.triggerEvent(p_49229_, p_49230_);
    }
    @javax.annotation.Nullable
    public MenuProvider getMenuProvider(@NotNull BlockState p_49234_, Level p_49235_, @NotNull BlockPos p_49236_) {
        BlockEntity blockentity = p_49235_.getBlockEntity(p_49236_);
        return blockentity instanceof MenuProvider ? (MenuProvider)blockentity : null;
    }


}
