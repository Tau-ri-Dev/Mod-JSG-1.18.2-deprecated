package dev.tauri.jsgcore.stargate.address;


import dev.tauri.jsgcore.stargate.symbols.AbstractSymbolType;
import dev.tauri.jsgcore.stargate.symbols.AbstractSymbolsManager;
import dev.tauri.jsgcore.stargate.symbols.SymbolInterface;
import dev.tauri.jsgcore.stargate.symbols.SymbolsRegistry;
import dev.tauri.jsgcore.utils.Logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class StargateAddress {
    public Map<AbstractSymbolType, StargateAddressDynamic> addresses = new HashMap<>();

    public StargateAddress(Random random){
        generateAddresses(random);
    }

    public void generateAddresses(Random random){
        for(AbstractSymbolsManager symbolManager : SymbolsRegistry.REGISTRY){
            ArrayList<SymbolInterface> symbols = new ArrayList<>();
            for(int i = 0; i < 9; i ++){
                symbols.add(symbolManager.getRandomSymbol(random));
                Logging.info("L1");
            }
            addresses.put(symbolManager.getSymbolType(), new StargateAddressDynamic(symbols));
        }
    }

    public StargateAddress(String data){
        String[] addresses = data.split(";");
        for (String s : addresses) {
            String[] d = s.split(":");
            String sType = d[0];
            String sAddress = d[1];
            AbstractSymbolsManager manager = SymbolsRegistry.managerBySymbolType(sType);
            if (manager == null) continue;
            AbstractSymbolType type = manager.getSymbolType();
            StargateAddressDynamic address = new StargateAddressDynamic(sAddress);
            this.addresses.put(type, address);
        }
    }

    public String toString(){
        StringBuilder output = new StringBuilder();
        int size = addresses.size();
        int i = 0;
        for(AbstractSymbolType type : addresses.keySet()){
            i++;
            output.append(type.getName());
            output.append(":");
            output.append(addresses.get(type).toString());

            if(i < size) // prevents inserting ";" at the end
                output.append(";");
        }
        return output.toString();
    }
}
