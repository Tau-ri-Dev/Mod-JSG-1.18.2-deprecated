package dev.tauri.jsgcore.utils;

import dev.tauri.jsgcore.config.AbstractConfigFile;
import dev.tauri.jsgcore.config.files.JSGClientConfig;
import dev.tauri.jsgcore.config.files.JSGServerConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;

import java.util.List;

import static dev.tauri.jsgcore.JSGCore.*;
import static dev.tauri.jsgcore.config.JSGConfigStorage.configFiles;



public class ModLoading {

    static {
        configFiles.add(new JSGClientConfig());
        configFiles.add(new JSGServerConfig());
    }

    public static void loadMod(IEventBus eb){
        setUpAddonsCount();
        eb.addListener(ModLoading::commonSetup);
    }

    private static void commonSetup(final FMLCommonSetupEvent event) {
        for(AbstractConfigFile c : configFiles){
            c.init();
            ModLoadingContext.get().registerConfig(c.type, c.config);
            c.load();
        }
        System.out.println("Registered!");
    }

    private static void setUpAddonsCount(){
        List<ModInfo> list = FMLLoader.getLoadingModList().getMods();
        for(ModInfo modInfo : list){
            String id = modInfo.getModId();
            if(id.startsWith(BASE_ID) && !(id.equals(MOD_ID))){
                INSTALLED_ADDONS++;
            }
        }
    }
}
