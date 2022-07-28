package dev.tauri.jsgmilkyway.stargate.symbols;

import dev.tauri.jsgcore.stargate.symbols.AbstractSymbolType;
import dev.tauri.jsgcore.stargate.symbols.AbstractSymbolsManager;
import dev.tauri.jsgcore.stargate.symbols.SymbolInterface;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class MilkyWaySymbolsManager extends AbstractSymbolsManager {
    @Override
    public @NonNull ArrayList<SymbolInterface> getOrigins() {
        ArrayList<SymbolInterface> list = new ArrayList<>();
        list.add(SymbolMilkyWayEnum.TAURI_ORIGIN);
        list.add(SymbolMilkyWayEnum.ANOTHER_ORIGIN);
        return list;
    }

    @Override
    public @Nullable SymbolInterface byName(String s) {
        for(SymbolMilkyWayEnum symbol : SymbolMilkyWayEnum.values()){
            if(symbol.name.equalsIgnoreCase(s)) return symbol;
        }
        return null;
    }


    @Override
    public @Nullable SymbolInterface byId(int id) {
        return SymbolMilkyWayEnum.byId(id);
    }

    @Override
    public @NotNull SymbolInterface getRandomSymbol(Random random) {
        int id;
        SymbolInterface symbol;
        int loop = 0;
        do{
            loop++;
            id = random.nextInt(38);
            symbol = SymbolMilkyWayEnum.byId(id);
        }
        while((id == 4 || symbol == null) && loop < 100);
        if(symbol == null) symbol = SymbolMilkyWayEnum.ANDROMEDA;
        return symbol;
    }

    @Override
    public @NonNull AbstractSymbolType getSymbolType() {
        return new MilkyWaySymbolType();
    }

    @Override
    public boolean isAddressValid() {
        return false;
    }
}
