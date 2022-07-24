package dev.tauri.jsgmilkyway.tileentity;

import dev.tauri.jsgcore.block.stargate.chevron.StargateAbstractChevronBlock;
import dev.tauri.jsgcore.block.stargate.ring.StargateAbstractRingBlock;
import dev.tauri.jsgcore.tileentity.StargateClassicMemberTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class StargateMilkyWayMemberTile extends StargateClassicMemberTile {
    public StargateMilkyWayMemberTile(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }

    @Override
    public StargateAbstractChevronBlock getChevronBlock() {
        return null;
    }

    @Override
    public StargateAbstractRingBlock getRingBlock() {
        return null;
    }
}
