package dev.tauri.jsgcore.stargate.symbols;

import net.minecraft.resources.ResourceLocation;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface SymbolInterface {
    @NonNull
    String getName();
    @NonNull
    int getId();

    boolean isBrb();
    boolean isOrigin();

    float getAngle();
    int getAngleIndex();

    String getEnglishName();
    ResourceLocation getIconResource();
    String localize();
    AbstractSymbolType getSymbolType();
}
