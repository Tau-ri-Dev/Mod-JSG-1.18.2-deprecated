package dev.tauri.jsgcore.stargate.symbols;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;

public class SymbolsRegistry {
    public static ArrayList<AbstractSymbolsManager> REGISTRY = new ArrayList<>();
    public static void registerManager(AbstractSymbolsManager symbolsManager){
        REGISTRY.add(symbolsManager);
    }

    @Nullable
    public static AbstractSymbolsManager managerBySymbolType(String typeName){
        for(AbstractSymbolsManager manager : REGISTRY){
            if(manager.getSymbolType().getName().equalsIgnoreCase(typeName))
                return manager;
        }
        return null;
    }

    @Nullable
    public static SymbolInterface symbolByName(String symbolName){
        for(AbstractSymbolsManager manager : REGISTRY){
            SymbolInterface symbol = manager.byName(symbolName);
            if(symbol != null) return symbol;
        }
        return null;
    }
}
