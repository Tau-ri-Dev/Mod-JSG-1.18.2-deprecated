package dev.tauri.jsgcore.config.files;


import dev.tauri.jsgcore.config.AbstractConfig;
import dev.tauri.jsgcore.config.AbstractConfigFile;
import dev.tauri.jsgcore.config.configs.MainConfig;

import java.util.ArrayList;
import java.util.List;

import static dev.tauri.jsgcore.JSGCore.MOD_BASE_ID;
import static net.minecraftforge.fml.config.ModConfig.Type.SERVER;

public class JSGServerConfig extends AbstractConfigFile {
    public static List<AbstractConfig> configs = new ArrayList<>();
    static{
        configs.add(new MainConfig());
    }
    @Override
    public void init() {
        type = SERVER;
        for (AbstractConfig c : configs) {
            c.init(BUILDER);
        }
        super.init();
    }

    @Override
    public String getName(){ return MOD_BASE_ID + "-server"; }
}
