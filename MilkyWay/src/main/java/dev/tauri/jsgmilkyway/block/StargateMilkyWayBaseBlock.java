package dev.tauri.jsgmilkyway.block;

import dev.tauri.jsgcore.block.stargate.base.StargateClassicBaseBlock;
import dev.tauri.jsgcore.stargate.merging.StargateAbstractMergeHelper;
import dev.tauri.jsgmilkyway.stargate.merging.StargateMilkyWayMergeHelper;
import dev.tauri.jsgmilkyway.tileentity.StargateMilkyWayBaseTile;
import dev.tauri.jsgmilkyway.tileentity.TileEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StargateMilkyWayBaseBlock extends StargateClassicBaseBlock {

    public StargateMilkyWayBaseBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL).strength(6f));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new StargateMilkyWayBaseTile(blockPos, blockState);
    }

    @Override
    public StargateAbstractMergeHelper getMergeHelper(){
        return new StargateMilkyWayMergeHelper();
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> entityType) {
        return createTickerHelper(entityType, TileEntityRegistry.SG_MILKYWAY_BASE_TILE.get(),
                StargateMilkyWayBaseTile::tick);
    }
    @javax.annotation.Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
        return p_152134_ == p_152133_ ? ((BlockEntityTicker<A>)p_152135_) : null;
    }
}
