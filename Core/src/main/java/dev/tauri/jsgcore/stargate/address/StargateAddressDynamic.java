package dev.tauri.jsgcore.stargate.address;

import dev.tauri.jsgcore.stargate.symbols.SymbolInterface;
import dev.tauri.jsgcore.stargate.symbols.SymbolsRegistry;

import java.util.ArrayList;

public class StargateAddressDynamic {
    public ArrayList<SymbolInterface> address = new ArrayList<>();

    public StargateAddressDynamic(ArrayList<SymbolInterface> list){
        address.addAll(list);
    }

    public StargateAddressDynamic(String data){
        String[] symbols = data.split(",");
        for (String symbol : symbols) {
            address.add(SymbolsRegistry.symbolByName(symbol));
        }
    }

    public String toString(){
        StringBuilder output = new StringBuilder();
        int size = address.size();
        int i = 0;
        for(SymbolInterface symbol : address){
            i++;
            output.append(symbol.getName());
            if(i < size) // prevents inserting ";" at the end
                output.append(",");
        }
        return output.toString();
    }
}
