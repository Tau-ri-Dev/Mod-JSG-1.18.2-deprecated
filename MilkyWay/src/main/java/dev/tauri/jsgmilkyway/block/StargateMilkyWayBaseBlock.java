package dev.tauri.jsgmilkyway.block;

import dev.tauri.jsgcore.block.StargateClassicBaseBlock;
import dev.tauri.jsgmilkyway.tileentity.StargateMilkyWayBaseTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StargateMilkyWayBaseBlock extends StargateClassicBaseBlock {

    public StargateMilkyWayBaseBlock() {
        super(Properties.of(Material.METAL).strength(6f));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new StargateMilkyWayBaseTile(blockPos, blockState);
    }



}
