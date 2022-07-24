package dev.tauri.jsgmilkyway.block;

import dev.tauri.jsgcore.block.stargate.chevron.StargateClassicChevronBlock;
import dev.tauri.jsgmilkyway.tileentity.StargateMilkyWayMemberTile;
import dev.tauri.jsgmilkyway.tileentity.TileEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StargateMilkyWayChevronBlock extends StargateClassicChevronBlock {

    public StargateMilkyWayChevronBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL).strength(6f));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new StargateMilkyWayMemberTile(blockPos, blockState);
    }


    public BlockEntityType getRegisteredTile(){
        return TileEntityRegistry.SG_MILKYWAY_CHEVRON_TILE.get();
    }
}
