package dev.tauri.jsgmilkyway.config;

import dev.tauri.jsgcore.config.AbstractConfig;
import net.minecraftforge.common.ForgeConfigSpec;

public class MainConfig extends AbstractConfig {
    public static ForgeConfigSpec.IntValue test_int_value; // used by MainConfig.test_int_value.get();

    public void init(ForgeConfigSpec.Builder builder) {
        builder.comment("JSG: MilkyWay Config file");

        test_int_value = builder
                .comment("Test int value")
                .defineInRange("mw.test_int", 1, 0, 10);
    }
}
