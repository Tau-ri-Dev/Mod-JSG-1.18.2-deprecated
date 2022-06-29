package dev.tauri.jsgcore.config;


import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import dev.tauri.jsgcore.JSGCore;
import dev.tauri.jsgcore.utils.Logging;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.loading.FileUtils;

import java.io.File;
import java.nio.file.Path;

import static dev.tauri.jsgcore.JSGCore.BASE_ID;

public class JSGConfig {

    public static final ForgeConfigSpec.Builder CONFIG_SERVER_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec CONFIG_SERVER;

    public static final ForgeConfigSpec.Builder CONFIG_CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec CONFIG_CLIENT;

    static{
        MainConfig.init(CONFIG_SERVER_BUILDER, CONFIG_CLIENT_BUILDER);

        CONFIG_SERVER = CONFIG_SERVER_BUILDER.build();
        CONFIG_CLIENT = CONFIG_CLIENT_BUILDER.build();
    }

    public static void load(ForgeConfigSpec config, String name){
        Path path = FMLPaths.CONFIGDIR.get().resolve(BASE_ID);
        FileUtils.getOrCreateDirectory(path, BASE_ID);
        path = path.resolve(name);
        Logging.debug(path.toString());
        Logging.debug("Loading config...");
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path.toString())).sync().autosave().writingMode(WritingMode.REPLACE).build();
        Logging.debug("Config built!");
        file.load();
        Logging.debug("Config loaded!");
        config.setConfig(file);
    }
}
