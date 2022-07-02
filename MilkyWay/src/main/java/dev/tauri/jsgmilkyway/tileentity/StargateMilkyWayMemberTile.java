package dev.tauri.jsgmilkyway.tileentity;

import dev.tauri.jsgcore.tileentity.StargateClassicBaseTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class StargateMilkyWayMemberTile extends StargateClassicBaseTile {
    public StargateMilkyWayMemberTile(BlockPos blockPos, BlockState blockState) {
        super(TileEntityRegistry.SG_MILKYWAY_MEMBER_TILE.get(), blockPos, blockState);
    }
}
