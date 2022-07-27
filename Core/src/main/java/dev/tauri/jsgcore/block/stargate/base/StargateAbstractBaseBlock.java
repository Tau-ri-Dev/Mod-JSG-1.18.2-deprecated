package dev.tauri.jsgcore.block.stargate.base;

import dev.tauri.jsgcore.block.RotatableBlock;
import dev.tauri.jsgcore.stargate.merging.StargateAbstractMergeHelper;
import dev.tauri.jsgcore.tileentity.StargateAbstractBaseTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;

import static dev.tauri.jsgcore.block.stargate.StargateAbstractMemberBlock.MERGED;

public abstract class StargateAbstractBaseBlock extends RotatableBlock implements EntityBlock {
    public StargateAbstractBaseBlock(Properties properties) {
        super(properties.explosionResistance(60f).requiresCorrectToolForDrops().noOcclusion());
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(MERGED, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(MERGED);
        super.createBlockStateDefinition(pBuilder);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return null;
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        if(pState.getValue(MERGED))
            return RenderShape.INVISIBLE;
        return RenderShape.MODEL;
    }

    @Override
    public boolean propagatesSkylightDown(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
        return blockState.getValue(MERGED);
    }

    @Override
    public float getShadeBrightness(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
        return 0.7F;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level pLevel, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit){
        if(!pLevel.isClientSide()){
            BlockEntity entity = pLevel.getBlockEntity(pos);
            if(entity instanceof StargateAbstractBaseTile){
                if (!player.isShiftKeyDown() && !tryAutobuild(player, pLevel, pos, hand) && state.getValue(MERGED)) {
                    NetworkHooks.openGui(((ServerPlayer) player), ((StargateAbstractBaseTile) entity), pos);
                    return InteractionResult.sidedSuccess(true);
                }
            }
        }
        return InteractionResult.sidedSuccess(false);
    }

    @Override
    public void onRemove(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pNewState, boolean pIsMoving) {
        if(!pLevel.isClientSide()) {
            if (pState.getBlock() != pNewState.getBlock()) {
                BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
                if (blockEntity instanceof StargateAbstractBaseTile) {
                    ((StargateAbstractBaseTile) blockEntity).drops();
                    ((StargateAbstractBaseTile) blockEntity).onBreak();

                }
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public void setPlacedBy(@Nonnull Level level, @Nonnull BlockPos blockPos, @Nonnull BlockState blockState, @Nullable LivingEntity placedBy, @Nonnull ItemStack itemStack) {
        if(!level.isClientSide()){
            final StargateAbstractBaseTile gateTile = (StargateAbstractBaseTile) level.getBlockEntity(blockPos);
            final Direction facing = level.getBlockState(blockPos).getValue(FACING);
            if(gateTile != null)
                gateTile.updateMergeState(gateTile.getMergeHelper().checkBlocks(level, blockPos, facing), facing);
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


    protected boolean tryAutobuild(Player player, Level world, BlockPos basePos, InteractionHand hand) {
        final StargateAbstractBaseTile gateTile = (StargateAbstractBaseTile) world.getBlockEntity(basePos);
        final Direction facing = world.getBlockState(basePos).getValue(FACING);

        StargateAbstractMergeHelper mergeHelper = gateTile.getMergeHelper();
        ItemStack stack = player.getItemInHand(hand);

        if (!gateTile.isMerged) {

            // This check ensures that stack represents matching member block.
            int memberVariant = mergeHelper.getMemberVariantFromItemStack(stack);

            if (memberVariant != -1) {
                boolean chevron = (memberVariant == 1);

                List<BlockPos> posList = mergeHelper.getAbsentBlockPositions(world, basePos, facing, chevron);

                if (!posList.isEmpty()) {
                    BlockPos pos = posList.get(0);

                    if (world.getBlockState(pos).isAir()) {
                        BlockState memberState = mergeHelper.getMemberBlock(chevron).defaultBlockState();
                        world.setBlock(pos, createMemberState(memberState, facing), 11);

                        SoundType soundtype = memberState.getBlock().getSoundType(memberState, world, pos, player);
                        world.playSound(null, pos, soundtype.getBreakSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);

                        if (!player.isCreative()) stack.shrink(1);

                        // If it was the last chevron/ring
                        if (posList.size() == 1)
                            gateTile.updateMergeState(gateTile.getMergeHelper().checkBlocks(world, basePos, facing), facing);

                        return true;
                    }
                }
            } // variant == null, wrong block held
        }

        return false;
    }

    protected abstract BlockState createMemberState(BlockState memberState, Direction facing);

    /*@Override
    public boolean skipRendering(@NotNull BlockState blockState, @NotNull BlockState blockState1, @NotNull Direction direction) {
        return blockState.getValue(MERGED);
    }*/


}
