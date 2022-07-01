package dev.tauri.jsgmilkyway.item;

import dev.tauri.jsgmilkyway.JSGMilkyWay;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MilkyWayItems {
    public static final DeferredRegister<Item> ITEMS_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, JSGMilkyWay.MOD_ID);

    public static void register(IEventBus eb){
        ITEMS_REGISTRY.register(eb);
    }
}
