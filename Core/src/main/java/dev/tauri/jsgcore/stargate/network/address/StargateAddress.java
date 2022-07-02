package dev.tauri.jsgcore.stargate.network.address;

import dev.tauri.jsgcore.stargate.network.symbols.ISymbolType;

public class StargateAddress {

    public ISymbolType symbolType;

    public StargateAddress(ISymbolType symbolType) {
        this.symbolType = symbolType;
    }
}
