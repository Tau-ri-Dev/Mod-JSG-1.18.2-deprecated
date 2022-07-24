package dev.tauri.jsgcore.screen;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ScreenRegistry {

    public final DeferredRegister<MenuType<?>> MENUS;

    public ScreenRegistry(String modId){
        MENUS = DeferredRegister.create(ForgeRegistries.CONTAINERS, modId);
    }

    public <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public void register(IEventBus eb){
        MENUS.register(eb);
    }
}
