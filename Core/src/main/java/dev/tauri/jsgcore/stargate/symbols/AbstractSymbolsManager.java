package dev.tauri.jsgcore.stargate.symbols;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.Random;

public abstract class AbstractSymbolsManager {

    @NonNull
    public abstract ArrayList<SymbolInterface> getOrigins();
    @Nullable
    public abstract SymbolInterface byName(String name);
    @Nullable
    public abstract SymbolInterface byId(int id);
    @NonNull
    public abstract SymbolInterface getRandomSymbol(Random random);
    @NonNull
    public abstract AbstractSymbolType getSymbolType();
    public abstract boolean isAddressValid();
}
