package dev.tauri.jsgcore.stargate.network;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class StargatePos {

    public String dim;
    public BlockPos pos;

    public StargatePos(ResourceKey<Level> level, BlockPos pos){
        this.dim = level.location().getNamespace() + ":" + level.location().getPath();
        this.pos = pos;
    }

    public StargatePos(String data){
        String[] d = data.split(";");
        dim = d[0];
        pos = BlockPos.of(Long.parseLong(d[1]));
    }

    public String toString(){
        return dim + ";" + pos.asLong();
    }
}
