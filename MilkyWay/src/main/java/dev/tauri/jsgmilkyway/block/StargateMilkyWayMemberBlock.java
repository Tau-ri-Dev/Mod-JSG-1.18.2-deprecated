package dev.tauri.jsgmilkyway.block;

import dev.tauri.jsgcore.block.StargateClassicMemberBlock;
import dev.tauri.jsgmilkyway.tileentity.StargateMilkyWayMemberTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StargateMilkyWayMemberBlock extends StargateClassicMemberBlock {

    public StargateMilkyWayMemberBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL).strength(6f));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new StargateMilkyWayMemberTile(blockPos, blockState);
    }
}
