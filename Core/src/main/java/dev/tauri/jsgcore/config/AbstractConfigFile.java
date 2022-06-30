package dev.tauri.jsgcore.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import dev.tauri.jsgcore.utils.Logging;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.loading.FileUtils;

import java.io.File;
import java.nio.file.Path;

import static dev.tauri.jsgcore.JSGCore.BASE_ID;

public abstract class AbstractConfigFile {

    public final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public ForgeConfigSpec config;
    public ModConfig.Type type;

    public void init(){
        config = BUILDER.build();
    }

    public abstract String getName();

    public void load() {
        Path path = FMLPaths.CONFIGDIR.get().resolve(BASE_ID);
        FileUtils.getOrCreateDirectory(path, BASE_ID);
        path = path.resolve(getName() + ".toml");
        Logging.debug(path.toString());
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path.toString())).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }
}
