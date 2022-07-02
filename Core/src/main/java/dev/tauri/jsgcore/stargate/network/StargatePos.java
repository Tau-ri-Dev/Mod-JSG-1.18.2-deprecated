package dev.tauri.jsgcore.stargate.network;

import dev.tauri.jsgcore.stargate.network.address.StargateAddress;
import dev.tauri.jsgcore.stargate.network.symbols.ISymbolType;
import dev.tauri.jsgcore.stargate.network.symbols.SymbolInterface;
import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class StargatePos {
    public int dimensionID;
    public BlockPos gatePos;
    public ISymbolType symbolType;
    public List<SymbolInterface> additionalSymbols;

    public StargatePos(int dimensionID, BlockPos gatePos, StargateAddress stargateAddress) {
        this.dimensionID = dimensionID;
        this.gatePos = gatePos;

        this.symbolType = stargateAddress.symbolType;
        this.additionalSymbols = new ArrayList<>(2);
        //this.additionalSymbols.addAll(stargateAddress.getAdditional());
    }
}
