package dev.tauri.jsgcore.stargate.network;

import dev.tauri.jsgcore.utils.Logging;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.LevelResource;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.*;
import java.nio.file.Path;

import static dev.tauri.jsgcore.JSGCore.MOD_BASE_ID;

public class StargateNetworkFile{

    public static final String FILE_NAME = MOD_BASE_ID + "_StargateNetwork";
    public static final String FILE_END = "sg";

    public static StargateNetworkData NETWORK = new StargateNetworkData();

    public static File getNetwork(ServerLevel level) throws IOException {
        Path worldDir = level.getServer().getWorldPath(LevelResource.ROOT);

        File jsgDir = new File(worldDir.toFile(), MOD_BASE_ID);
        File network = new File(jsgDir, FILE_NAME + "." + FILE_END);
        NETWORK.setFile(network);
        if(!jsgDir.mkdirs()){
            Logging.info("Network: Dirs exist!");
        }
        if (!network.createNewFile()) {
            Logging.info("Network: File " + network.getPath() + " already exists!");
        }
        return network;
    }

    @Nullable
    public static StargateNetworkData load(ServerLevel level) {
        try {
            File network = getNetwork(level);
            return NETWORK.setNetwork(network);
        }
        catch (Exception ignored){}
        return null;
    }

    public static void save(ServerLevel level, StargateNetworkData data) {
        try {
            File network = getNetwork(level);
            data.save(network);
        }
        catch (Exception ignored){}
    }
}
