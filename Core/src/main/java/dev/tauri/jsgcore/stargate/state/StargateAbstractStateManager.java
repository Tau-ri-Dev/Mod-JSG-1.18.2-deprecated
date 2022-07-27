package dev.tauri.jsgcore.stargate.state;

import dev.tauri.jsgcore.stargate.address.StargateAddressDynamic;
import dev.tauri.jsgcore.tileentity.StargateAbstractBaseTile;
import net.minecraft.nbt.CompoundTag;

public abstract class StargateAbstractStateManager {
    private final StargateAbstractBaseTile gateTile;
    private EnumStargateState stargateState = EnumStargateState.IDLE;

    public StargateAbstractStateManager(StargateAbstractBaseTile gateTile){
        this.gateTile = gateTile;
    }

    public void fromNBT(CompoundTag compound){
        stargateState = EnumStargateState.byId(compound.getInt("state"));
    }

    public CompoundTag toNBT(){
        CompoundTag compound = new CompoundTag();
        compound.putInt("state", stargateState.id);
        return compound;
    }

    private void markDirty(){
        gateTile.setChanged();
    }

    public void onMerge(){
        stargateState = EnumStargateState.IDLE;
        markDirty();
    }
    public void onUnmerge(){
        stargateState = EnumStargateState.IDLE;
        markDirty();
    }

    public void openGate(){
        markDirty();
    }
    public void closeGate(){
        markDirty();
    }
    public boolean isAddressValid(StargateAddressDynamic address){
        return false;
    }

    public void setStargateState(EnumStargateState state){
        stargateState = state;
        markDirty();
    }
    public EnumStargateState getStargateState(){
        return stargateState;
    }


    public void tick(){
        markDirty();
    }
}
