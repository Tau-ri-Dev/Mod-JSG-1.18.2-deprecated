package dev.tauri.jsgcore.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MainConfig {
    public static ForgeConfigSpec.IntValue test_int_value; // used by MainConfig.test_int_value.get();

    public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client) {
        server.comment("JSG: Core Config file");

        test_int_value = server
                .comment("Test int value")
                .defineInRange("core.test_int", 1, 0, 10);
    }
}
