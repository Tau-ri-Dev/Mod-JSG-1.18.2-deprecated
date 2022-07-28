package dev.tauri.jsgcore.utils;

import com.mojang.math.Quaternion;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashMap;
import java.util.Map;

public class FacingToRotation {
	private static final Map<Direction, Quaternion> ROTATION_MAP = new HashMap<>();

	static {
		for (Direction facing : Direction.values()) {
			ROTATION_MAP.put(facing, facing.getRotation());
		}
	}
	
	public static Quaternion get(Direction facing) {
		return ROTATION_MAP.get(facing);
	}

	public static BlockPos rotatePos(BlockPos pos, Direction rotation) {
		switch(rotation) {
			case NORTH:
			default:
				return pos;
			case EAST:
				return new BlockPos(-pos.getZ(), pos.getY(), pos.getX());
			case SOUTH:
				return new BlockPos(-pos.getX(), pos.getY(), -pos.getZ());
			case WEST:
				return new BlockPos(pos.getZ(), pos.getY(), -pos.getX());
			case UP:
				return new BlockPos(pos.getX(), pos.getZ(), pos.getY());
			case DOWN:
				return new BlockPos(pos.getX(), pos.getZ(), -pos.getY());
		}
	}

	public static float getHorizontal(Direction facing){
		return facing.toYRot();
	}

	public static float getVertical(Direction facing){
		if(facing == Direction.UP) return 90.0f;
		if(facing == Direction.DOWN) return -90.0f;
		return 0;
	}
}
