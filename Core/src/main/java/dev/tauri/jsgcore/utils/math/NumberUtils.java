package dev.tauri.jsgcore.utils.math;

public class NumberUtils {
  public static double round(double number, int places) {
    double p = Math.pow(10, places);
    return Math.round(number * p) / p;
  }
}
