package dev.tauri.jsgcore.config.files;

import dev.tauri.jsgcore.config.AbstractConfig;
import dev.tauri.jsgcore.config.AbstractConfigFile;

import java.util.ArrayList;
import java.util.List;

import static dev.tauri.jsgcore.JSGCore.MOD_ID;
import static net.minecraftforge.fml.config.ModConfig.Type.CLIENT;

public class JSGClientConfig extends AbstractConfigFile {
    public static List<AbstractConfig> configs = new ArrayList<>();
    static{
        //configs.add(new MainConfig());
    }
    @Override
    public void init() {
        type = CLIENT;
        for (AbstractConfig c : configs) {
            c.init(BUILDER);
        }
        super.init();
    }

    @Override
    public String getName(){ return MOD_ID + "-client"; }
}
