package dev.tauri.jsgcore.stargate.symbols;

public abstract class AbstractSymbolType {

    private final String name;
    private final String idName;
    private final float anglePerGlyph;

    public AbstractSymbolType(String name, String idName, float anglePerGlyph){
        this.name = name;
        this.idName = idName;
        this.anglePerGlyph = anglePerGlyph;
    }
    public String getName(){
        return name;
    }
    public String getId(){
        return idName;
    }
    public float getAnglePerGlyph() { return anglePerGlyph; }
}
