package dev.tauri.jsgcore.utils;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public class Logging {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static void info(String s) {
        LOGGER.info("JSG: " + s);
    }

    public static void error(String s) {
        LOGGER.error("JSG: " + s);
    }

    public static void warn(String s) {
        LOGGER.warn("JSG: " + s);
    }

    public static void debug(String s) {
        LOGGER.debug("JSG: " + s);
    }
}
