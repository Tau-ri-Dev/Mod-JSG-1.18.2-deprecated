package dev.tauri.jsgcore.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class BlockHelpers {
	
	public static boolean isBlockDirectlyUnderSky(Level world, BlockPos pos) {
		while (pos.getY() < 255) {
			pos = pos.above();
			
			BlockState state = world.getBlockState(pos);
			Block block = state.getBlock();
			
			if (!world.getBlockState(pos).isAir() && !(block instanceof LeavesBlock))
				return false;
		}
		
		return true;
	}
	
	/**
	 * Returns {@link BlockPos} with largest Y-coord value.
	 * 
	 * @param list List of positions.
	 * @return largest Y-coord {@link BlockPos}. {@code null} if list empty.
	 */
	public static BlockPos getHighest(List<BlockPos> list) {
		int maxy = -1;
		BlockPos top = null;
		
		for (BlockPos pos : list) {
			if (pos.getY() > maxy) {
				maxy = pos.getY();
				top = pos;
			}
		}
		
		return top;
	}
}
