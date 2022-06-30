package dev.tauri.jsgmilkyway.utils;

import dev.tauri.jsgmilkyway.JSGMilkyWay;

public class Logging {
    public static void info(String s) {
        JSGMilkyWay.LOGGER.info(s);
    }

    public static void error(String s) {
        JSGMilkyWay.LOGGER.error(s);
    }

    public static void warn(String s) {
        JSGMilkyWay.LOGGER.warn(s);
    }

    public static void debug(String s) {
        JSGMilkyWay.LOGGER.debug(s);
    }
}
