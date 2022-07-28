package dev.tauri.jsgcore.stargate.spinning;

import dev.tauri.jsgcore.stargate.symbols.SymbolInterface;
import io.netty.buffer.ByteBuf;

public interface ISpinHelper {
    boolean getIsSpinning();

    void setIsSpinning(boolean value);

    SymbolInterface getCurrentSymbol();

    void setCurrentSymbol(SymbolInterface symbol);

    SymbolInterface getTargetSymbol();

    void initRotation(long totalWorldTime, SymbolInterface targetSymbol, EnumSpinDirection direction, float startOffset, int plusRounds);

    float apply(double tick);

    void toBytes(ByteBuf buf);

    void fromBytes(ByteBuf buf);
}
