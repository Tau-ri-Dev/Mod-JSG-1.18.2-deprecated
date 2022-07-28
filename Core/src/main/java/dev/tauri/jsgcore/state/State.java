package dev.tauri.jsgcore.state;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class State implements INBTSerializable<CompoundTag> {
    public abstract void toBytes(ByteBuf buf);

    public abstract void fromBytes(ByteBuf buf);

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compound = new CompoundTag();

        ByteBuf buf = Unpooled.buffer();
        toBytes(buf);

        byte[] dst = new byte[buf.readableBytes()];
        buf.readBytes(dst);

        compound.putByteArray("byteArray", dst);

        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag compound) {
        if (compound == null)
            return;

        byte[] dst = compound.getByteArray("byteArray");

        if (dst.length > 0) {
            ByteBuf buf = Unpooled.copiedBuffer(dst);
            fromBytes(buf);
        }
    }
}
