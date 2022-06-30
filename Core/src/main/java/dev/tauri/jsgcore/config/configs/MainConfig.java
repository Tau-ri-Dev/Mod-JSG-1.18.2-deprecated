package dev.tauri.jsgcore.config.configs;

import dev.tauri.jsgcore.config.AbstractConfig;
import net.minecraftforge.common.ForgeConfigSpec;

public class MainConfig extends AbstractConfig {
    public static ForgeConfigSpec.IntValue test_int_value; // used by MainConfig.test_int_value.get();

    public void init(ForgeConfigSpec.Builder builder) {
        builder.comment("JSG: Core Config file");

        test_int_value = builder
                .comment("Test int value")
                .defineInRange("core.test_int", 1, 0, 10);
    }
}
