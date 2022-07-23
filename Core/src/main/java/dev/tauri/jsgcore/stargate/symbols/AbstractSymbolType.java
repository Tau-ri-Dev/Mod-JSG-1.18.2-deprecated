package dev.tauri.jsgcore.stargate.symbols;

public abstract class AbstractSymbolType {

    private final String name;
    private final String idName;

    public AbstractSymbolType(String name, String idName){
        this.name = name;
        this.idName = idName;
    }
    public String getName(){
        return name;
    }
    public String getId(){
        return idName;
    }
}
