package dev.tauri.jsgcore.stargate.network;

import dev.tauri.jsgcore.stargate.network.address.StargateAddress;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static dev.tauri.jsgcore.stargate.network.StargateNetwork.FILE_NAME;

public class StargateNetworkFile extends SavedData {

    // Get network
    public static StargateNetworkFile get(Level level){
        if(!level.isClientSide){
            DimensionDataStorage storage = ((ServerLevel) level).getDataStorage();
            return storage.computeIfAbsent(StargateNetworkFile::new, StargateNetworkFile::new, FILE_NAME);
        }
        return null;
    }

    // Create new data storage
    public StargateNetworkFile(){

    }

    // Use generated data storage
    public StargateNetworkFile(CompoundTag tag){

    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag compoundTag) {
        ListTag list = new ListTag();
        ArrayList<StargateAddress> gates = StargateNetwork.getStargates();
        for(int i = 0; i < gates.size(); i++){
            CompoundTag tag = new CompoundTag();
            //tag.put("address", gates.get(i).serializeNBT());
        }
        compoundTag.put("sg_network", list);
        return compoundTag;
    }
}
