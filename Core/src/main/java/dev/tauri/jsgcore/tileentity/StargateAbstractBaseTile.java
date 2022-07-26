package dev.tauri.jsgcore.tileentity;

import dev.tauri.jsgcore.screen.stargate.StargateMenu;
import dev.tauri.jsgcore.stargate.merging.StargateAbstractMergeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

import static dev.tauri.jsgcore.block.stargate.base.StargateAbstractBaseBlock.MERGED;

public abstract class StargateAbstractBaseTile extends BlockEntity implements MenuProvider {

    public static final int STARGATE_CONTAINER_SIZE = 12;

    public boolean isMerged = false;
    private final ItemStackHandler itemStackHandler = new ItemStackHandler(STARGATE_CONTAINER_SIZE){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public int getSlotLimit(int slot){
            return 1;
        }
    };
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public StargateAbstractBaseTile(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public void onMerge(){
        isMerged = true;
    }

    public void onUnmerged(){
        isMerged = false;
    }

    public void onBreak(){
        isMerged = false;
    }

    @Override
    public void onLoad(){
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemStackHandler);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @org.checkerframework.checker.nullness.qual.Nullable Direction side){
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return lazyItemHandler.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps(){
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public static <E extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, E e) {

    }


    // ----------------------------------
    // NBT TAGS

    // load nbt
    @Override
    public void load(@NotNull CompoundTag compound){
        super.load(compound);
        itemStackHandler.deserializeNBT(compound.getCompound("inventory"));
        isMerged = compound.getBoolean("merged");
    }

    // save nbt
    @Override
    protected void saveAdditional(@NotNull CompoundTag compound) {
        compound.put("inventory", itemStackHandler.serializeNBT());
        compound.putBoolean("merged", isMerged);
        super.saveAdditional(compound);
    }


    // ----------------------------------
    // Container

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory pInventory, @NotNull Player player) {
        return new StargateMenu(id, pInventory, this);
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for(int i = 0; i < itemStackHandler.getSlots(); i++){
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }
        if(level == null) return;
        Containers.dropContents(level, worldPosition, inventory);
    }

    public abstract ResourceLocation getMenuBackgroundTexture();

    // ----------------------------------
    // finally STARGATE!

    public abstract int getSupportedCapacitors();
    public abstract boolean canUseAsOverlay(ItemStack stack);
    public abstract StargateAbstractMergeHelper getMergeHelper();

    public final void updateMergeState(boolean shouldBeMerged, Direction facing) {
        if (!shouldBeMerged) {
            if (isMerged) onBreak();

            /*if (stargateState.engaged()) {
                targetGatePos.getTileEntity().closeGate(StargateClosedReasonEnum.CONNECTION_LOST);
            }*/
        } else {
            onMerge();
        }

        if (this.isMerged == shouldBeMerged) {
            if (shouldBeMerged) {
                getMergeHelper().updateMembersBasePos(level, worldPosition, facing);
            }
            return;
        }

        this.isMerged = shouldBeMerged;
        if(level == null) return;
        BlockState actualState = level.getBlockState(worldPosition);

        // When the block is destroyed, there will be air in this place and we cannot set its block state
        if (getMergeHelper().matchBase(actualState)) {
            level.setBlock(worldPosition, actualState.setValue(MERGED, !shouldBeMerged), 2);
        }

        getMergeHelper().updateMembersMergeStatus(level, worldPosition, facing, shouldBeMerged);

        setChanged();
    }
}
