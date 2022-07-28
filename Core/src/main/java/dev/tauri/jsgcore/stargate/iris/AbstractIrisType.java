package dev.tauri.jsgcore.stargate.iris;

import net.minecraft.world.item.Item;

public abstract class AbstractIrisType {
    public int id;
    public Item item;

    public AbstractIrisType(int id, Item item){
        this.id = id;
        this.item = item;
    }
}
