package dev.tauri.jsgcore.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BarrierBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class JSGInvisibleBlock extends BarrierBlock {
    public JSGInvisibleBlock(Properties properties) {
        super(properties);
    }

    public boolean propagatesSkylightDown(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
        return false;
    }

    public @NotNull RenderShape getRenderShape(@NotNull BlockState blockState) {
        return RenderShape.INVISIBLE;
    }

    public float getShadeBrightness(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
        return 0.7F;
    }
}
