package dev.tauri.jsgcore.render;

import java.util.HashMap;
import java.util.Map;

public enum EnumVortexState {
    FORMING(0),
    FULL(1),
    DECREASING(2),
    STILL(3),
    CLOSING(4),
    SHRINKING(5);

    private static final Map<Integer, EnumVortexState> map = new HashMap<>();

    static {
        for (EnumVortexState packet : EnumVortexState.values()) {
            map.put(packet.index, packet);
        }
    }

    public final int index;

    EnumVortexState(int index) {
        this.index = index;
    }

    public static EnumVortexState valueOf(int index) {
        return map.get(index);
    }

    public boolean equals(EnumVortexState state) {
        return this.index == state.index;
    }
}
