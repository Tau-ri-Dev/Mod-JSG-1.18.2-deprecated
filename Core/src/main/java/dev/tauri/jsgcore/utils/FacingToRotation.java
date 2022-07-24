package dev.tauri.jsgcore.utils;

import com.mojang.math.Quaternion;
import net.minecraft.core.Direction;

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
}
