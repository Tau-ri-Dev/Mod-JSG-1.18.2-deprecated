package dev.tauri.jsgcore.stargate.network;

import dev.tauri.jsgcore.stargate.network.address.StargateAddress;
import dev.tauri.jsgcore.stargate.network.address.StargateAddressDynamic;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;

import static dev.tauri.jsgcore.JSGCore.MOD_BASE_ID;

public class StargateNetwork {

    public static final String FILE_NAME = MOD_BASE_ID + "_StargateNetwork";

    private static final ArrayList<StargateAddress> STARGATES = new ArrayList<>();

    public static ArrayList<StargateAddress> getStargates(){
        return STARGATES;
    }

    public static void addStargate(StargateAddress address){
        STARGATES.add(address);
    }


}
