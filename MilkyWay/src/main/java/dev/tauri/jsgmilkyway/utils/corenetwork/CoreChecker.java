package dev.tauri.jsgmilkyway.utils.corenetwork;

import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;

import java.util.List;

import static dev.tauri.jsgmilkyway.JSGMilkyWay.CORE_MOD_ID;

public class CoreChecker {
    public static boolean isCoreLoaded = false;
    public static boolean isCoreInstalled(){
        List<ModInfo> list = FMLLoader.getLoadingModList().getMods();
        for(ModInfo modInfo : list){
            if(modInfo.getModId().equals(CORE_MOD_ID)){
                isCoreLoaded = true;
                return true;
            }
        }
        isCoreLoaded = false;
        return false;
    }
}
