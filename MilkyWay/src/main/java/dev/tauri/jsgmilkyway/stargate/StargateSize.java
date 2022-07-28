package dev.tauri.jsgmilkyway.stargate;

import dev.tauri.jsgcore.utils.JSGAxisBox;
import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum StargateSize {
    SMALL(0, "Small", 0.75, -0.95,
            new JSGAxisBox(-2.5, 1.5, -0.1, 2.5, 6.6, 0.2),
            new JSGAxisBox(-1, 3, 0, 1, 5.5, 5),
            5,
            Arrays.asList(
                    new JSGAxisBox(-1.5, 2.0, -0.5, 1.5, 7, 0.5),
                    new JSGAxisBox(-2.5, 2.0, -0.5, -1.5, 6, 0.5),
                    new JSGAxisBox(2.5, 2.0, -0.5, 1.5, 6, 0.5)
            )
    ),

    MEDIUM(1, "Medium", 0.83, -0.62,
            new JSGAxisBox(-2.5, 1.5, -0.1, 2.5, 7.5, 0.2),
            new JSGAxisBox(-1, 3, 0, 1, 5.5, 5), 5,
            Arrays.asList(
                    new JSGAxisBox(-1.5, 2.0, -0.5, 1.5, 7, 0.5),
                    new JSGAxisBox(-2.5, 2.0, -0.5, -1.5, 6, 0.5),
                    new JSGAxisBox(2.5, 2.0, -0.5, 1.5, 6, 0.5)
            )
    ),

    LARGE(2, "Large", 1, 0,
            new JSGAxisBox(-3.5, 1.5, -0.1, 3.5, 8.6, 0.2),
            new JSGAxisBox(-1.5, 3.5, 0.5, 1.5, 7, 6.5), 6,
            Arrays.asList(
                    new JSGAxisBox(-2.5, 2.0, -0.5, 2.5, 9, 0.5),
                    new JSGAxisBox(-3.5, 2.0, -0.5, -2.5, 8, 0.5),
                    new JSGAxisBox(3.5, 2.0, -0.5, 2.5, 8, 0.5)
            )
    );

    public final int id;
    public final String name;
    public final double renderScale;
    public final double renderTranslationY;
    public final JSGAxisBox teleportBox;
    public final JSGAxisBox killingBox;
    public final int horizonSegmentCount;
    public final List<JSGAxisBox> gateVaporizingBoxes;



    StargateSize(int id, String name, double renderScale, double renderTranslationY, JSGAxisBox teleportBox, JSGAxisBox killingBox, int horizonSegmentCount, List<JSGAxisBox> gateVaporizingBoxes) {
        this.id = id;
        this.name = name;
        this.renderScale = renderScale;
        this.renderTranslationY = renderTranslationY;
        this.teleportBox = teleportBox;
        this.killingBox = killingBox;
        this.horizonSegmentCount = horizonSegmentCount;
        this.gateVaporizingBoxes = gateVaporizingBoxes;
    }

    StargateSize(int id, String name, double renderScale, double renderTranslationY) {
        this.id = id;
        this.name = name;
        this.renderScale = renderScale;
        this.renderTranslationY = renderTranslationY;
        this.teleportBox = null;
        this.killingBox = null;
        this.horizonSegmentCount = 0;
        this.gateVaporizingBoxes = null;
    }

    @Override
    public String toString() {
        return name;
    }

    public static StargateSize fromId(int id) {
        return StargateSize.values()[id];
    }

    public static BlockPos[] getIrisBLocksPatter(StargateSize size) {
        List<BlockPos> patter = new ArrayList<>();
        return switch (size) {
            case SMALL, MEDIUM -> IRIS_PATTER_SMALL;
            case LARGE -> IRIS_PATTER_LARGE;
        };
    }

    public static void init() {

        // Small
        // Medium
        List<BlockPos> small = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            for (int j = -2; j < 3; j++) {
                if ((i == 0 || i == 5) && (j == -2 || j == 2)) continue;
                small.add(new BlockPos(j, i+1 , 0));
            }
        }

        // Large
        List<BlockPos> large = new ArrayList<>();
        int startX = -2;
        for (int i = 0; i < 8; i++) {

            for (int j = startX; j < 1-startX; j++) {
                large.add(new BlockPos(j, i+1, 0));
            }
            if (i < 3 && i != 1) startX--;
            if (i >= 4 && i != 5) startX++;
        }

        IRIS_PATTER_SMALL = small.toArray(new BlockPos[0]);
        IRIS_PATTER_LARGE = large.toArray(new BlockPos[0]);
    }


    public static BlockPos[] IRIS_PATTER_SMALL;
    public static BlockPos[] IRIS_PATTER_LARGE;

    public static final StargateSize defaultSize = MEDIUM;
}
