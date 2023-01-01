package dev.tauri.jsgcore.utils;

import dev.tauri.jsgcore.config.AbstractConfigFile;
import dev.tauri.jsgcore.config.files.JSGClientConfig;
import dev.tauri.jsgcore.config.files.JSGServerConfig;
import dev.tauri.jsgcore.screen.ScreenTypes;
import dev.tauri.jsgcore.screen.stargate.StargateScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import org.lwjgl.glfw.GLFWWindowFocusCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL32;

import java.util.List;

import static dev.tauri.jsgcore.JSGCore.*;
import static dev.tauri.jsgcore.config.JSGConfigStorage.configFiles;



public class ModLoading {

    static{
        configFiles.add(new JSGClientConfig());
        configFiles.add(new JSGServerConfig());
    }

    public static void loadMod(IEventBus eb){
        setUpAddonsCount();

        ScreenTypes.load();

        // registry
        SCREEN_REGISTRY.register(eb);

        eb.addListener(ModLoading::commonSetup);
        eb.addListener(ModLoading::clientSetup);
    }

    private static void commonSetup(final FMLCommonSetupEvent event) {
        for(AbstractConfigFile c : configFiles){
            c.init();
            ModLoadingContext.get().registerConfig(c.type, c.config);
            c.load();
        }
        System.out.println("Registered!");
    }

    private static void clientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(ScreenTypes.STARGATE_MENU.get(), StargateScreen::new);
    }

    private static void setUpAddonsCount(){
        List<ModInfo> list = FMLLoader.getLoadingModList().getMods();
        for(ModInfo modInfo : list){
            String id = modInfo.getModId();
            if(id.startsWith(MOD_BASE_ID) && !(id.equals(MOD_ID))){
                INSTALLED_ADDONS++;
            }
        }
    }
}
