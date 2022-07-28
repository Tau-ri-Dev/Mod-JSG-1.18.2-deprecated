package dev.tauri.jsgcore.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;

public class JSGAxisBox {
    private double x1;
    private double y1;
    private double z1;

    private double x2;
    private double y2;
    private double z2;

    public JSGAxisBox(double x1, double y1, double z1, double x2, double y2, double z2){
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

    public VoxelShape toVoxelShape(){
        return Block.box(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2), Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));
    }

    public ArrayList<BlockPos> getPositions(){
        ArrayList<BlockPos> positions = new ArrayList<>();

        double minX = Math.min(x1, x2);
        double minY = Math.min(y1, y2);
        double minZ = Math.min(z1, z2);

        double maxX = Math.max(x1, x2);
        double maxY = Math.max(y1, y2);
        double maxZ = Math.max(z1, z2);

        for(double x = minX; x <= maxX; x++){
            for(double y = minY; y <= maxY; y++){
                for(double z = minZ; z <= maxZ; z++){
                    positions.add(new BlockPos(x, y ,z));
                }
            }
        }

        return positions;
    }
}
