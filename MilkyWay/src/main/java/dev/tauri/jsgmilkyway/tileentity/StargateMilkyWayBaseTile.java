package dev.tauri.jsgmilkyway.tileentity;

import dev.tauri.jsgcore.tileentity.StargateClassicBaseTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class StargateMilkyWayBaseTile extends StargateClassicBaseTile {
    public StargateMilkyWayBaseTile(BlockPos blockPos, BlockState blockState) {
        super(TileEntityRegistry.SG_MILKYWAY_BASE_TILE.get(), blockPos, blockState);
    }
}
