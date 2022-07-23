package dev.tauri.jsgcore.stargate.symbols;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface SymbolInterface {
    @NonNull
    String getName();

    boolean isBrb();
    boolean isOrigin();
}
