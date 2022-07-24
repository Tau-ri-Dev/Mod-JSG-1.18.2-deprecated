package dev.tauri.jsgcore.screen;

import dev.tauri.jsgcore.JSGCore;
import dev.tauri.jsgcore.screen.stargate.StargateMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.RegistryObject;

public class ScreenTypes {
    public static final RegistryObject<MenuType<StargateMenu>> STARGATE_MENU = JSGCore.SCREEN_REGISTRY.registerMenuType(StargateMenu::new, "stargate_menu");


    public static void load(){}
}
