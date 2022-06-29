package dev.tauri.jsgcore.utils;

import dev.tauri.jsgcore.JSGCore;

public class Logging {
    public static void info(String s) {
        JSGCore.LOGGER.info(s);
    }

    public static void error(String s) {
        JSGCore.LOGGER.error(s);
    }

    public static void warn(String s) {
        JSGCore.LOGGER.warn(s);
    }

    public static void debug(String s) {
        JSGCore.LOGGER.debug(s);
    }
}
