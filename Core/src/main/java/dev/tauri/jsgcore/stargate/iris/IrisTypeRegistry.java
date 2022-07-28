package dev.tauri.jsgcore.stargate.iris;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;

public class IrisTypeRegistry {
    public static final ArrayList<AbstractIrisType> REGISTRY = new ArrayList<>();
    public static void registerIrisType(AbstractIrisType type){
        REGISTRY.add(type);
    }

    @Nullable
    public static AbstractIrisType getIrisTypeById(int id){
        for(AbstractIrisType type : REGISTRY){
            if(type.id == id) return type;
        }
        if(id != -1)
            return getIrisTypeById(-1);
        return null;
    }

    @Nullable
    public static AbstractIrisType getIrisTypeByItem(Item item){
        for(AbstractIrisType type : REGISTRY){
            if(type.item == item) return type;
        }
        if(item != Items.AIR)
            return getIrisTypeById(-1);
        return null;
    }

    static{
        registerIrisType(new NullIris());
    }

    private static class NullIris extends AbstractIrisType {

        public NullIris() {
            super(-1, Items.AIR);
        }
    }

}
