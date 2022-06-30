package dev.tauri.jsgmilkyway.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MilkyWayConfig {
    public static ForgeConfigSpec.IntValue test_int_value; // used by MilkyWayConfig.test_int_value.get();

    public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client) {
        server.comment("JSG: MilkyWay Config file");

        test_int_value = server
                .comment("Test int value")
                .defineInRange("milkyway.test_int", 1, 0, 10);
    }
}
