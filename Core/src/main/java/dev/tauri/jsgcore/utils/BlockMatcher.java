package dev.tauri.jsgcore.utils;

import com.google.common.base.Predicate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockMatcher implements Predicate<BlockState>
{
    private final Block block;

    private BlockMatcher(Block blockType)
    {
        this.block = blockType;
    }

    public static BlockMatcher forBlock(Block blockType) {
        return new BlockMatcher(blockType);
    }

    @Override
    public boolean apply(BlockState input) {
        return input != null && input.getBlock() == this.block;
    }
}
