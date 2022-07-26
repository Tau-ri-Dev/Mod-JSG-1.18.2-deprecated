package dev.tauri.jsgcore.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;

public class JSGAxisBox {
    private int x1;
    private int y1;
    private int z1;

    private int x2;
    private int y2;
    private int z2;

    public JSGAxisBox(int x1, int y1, int z1, int x2, int y2, int z2){
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
    }

    public JSGAxisBox rotate(Direction facing){
        BlockPos pos1 = FacingToRotation.rotatePos(new BlockPos(x1, y1, z1), facing);
        BlockPos pos2 = FacingToRotation.rotatePos(new BlockPos(x2, y2, z2), facing);

        this.x1 = pos1.getX();
        this.y1 = pos1.getY();
        this.z1 = pos1.getZ();

        this.x2 = pos2.getX();
        this.y2 = pos2.getY();
        this.z2 = pos2.getZ();

        return this;
    }

    public JSGAxisBox offset(BlockPos pos){
        this.x1 += pos.getX();
        this.y1 += pos.getY();
        this.z1 += pos.getZ();

        this.x2 += pos.getX();
        this.y2 += pos.getY();
        this.z2 += pos.getZ();

        return this;
    }

    public ArrayList<BlockPos> getPositions(){
        ArrayList<BlockPos> positions = new ArrayList<>();

        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int minZ = Math.min(z1, z2);

        int maxX = Math.max(x1, x2);
        int maxY = Math.max(y1, y2);
        int maxZ = Math.max(z1, z2);

        for(int x = minX; x <= maxX; x++){
            for(int y = minY; y <= maxY; y++){
                for(int z = minZ; z <= maxZ; z++){
                    positions.add(new BlockPos(x, y ,z));
                }
            }
        }

        return positions;
    }
}
