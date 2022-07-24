package dev.tauri.jsgcore.stargate.network;

import dev.tauri.jsgcore.stargate.address.StargateAddress;
import dev.tauri.jsgcore.utils.Logging;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StargateNetworkData {
    private final Map<StargatePos, StargateAddress> map = new HashMap<>();

    private File networkFile = null;

    public StargateNetworkData(){}

    public StargateNetworkData setNetwork(File network) throws FileNotFoundException{
        if(!network.exists()) return this;
        Scanner reader = new Scanner(network);
        map.clear();
        while(reader.hasNextLine()){
            String line = reader.nextLine();
            Logging.debug("Loading: " + line);
            String[] data = line.split("::");
            StargatePos pos = new StargatePos(data[1]);
            StargateAddress address = new StargateAddress(data[2]);
            map.put(pos, address);
        }
        return this;
    }

    public void setFile(File network){
        networkFile = network;
    }

    public void addStargateToNetwork(StargatePos pos, StargateAddress address){
        map.put(pos, address);
        if(networkFile != null){
            try {
                save(networkFile);
            }catch (Exception ignored){}
        }
    }

    public void save(File network) throws IOException {
        int i = 1;

        // clear file
        if(!network.delete()) Logging.debug("Network: File " + network.getPath() + " can not be deleted!");
        if(!network.createNewFile()) Logging.debug("Network: File " + network.getPath() + " can not be created!");

        FileWriter writer = new FileWriter(network);
        for(StargatePos pos : map.keySet()){
            StringBuilder output = new StringBuilder();

            // id
            output.append(i);
            output.append("::");

            // Stargate Pos
            output.append(pos.toString());
            output.append("::");

            // Stargate Address
            StargateAddress address = map.get(pos);
            output.append(address.toString());

            // write to the file
            output.append("\n");
            writer.write(output.toString());
            i++;

            Logging.debug("Saving: " + output);
        }
        writer.close();
    }
}
