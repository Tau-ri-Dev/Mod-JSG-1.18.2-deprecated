package dev.tauri.jsgcore.screen.stargate;

import dev.tauri.jsgcore.block.JSGEnergyBlock;
import dev.tauri.jsgcore.item.EnergyItem;
import dev.tauri.jsgcore.item.IrisItem;
import dev.tauri.jsgcore.item.notebook.page.NoteBookPageItem;
import dev.tauri.jsgcore.screen.JSGAbstractMenu;
import dev.tauri.jsgcore.screen.ScreenTypes;
import dev.tauri.jsgcore.tileentity.StargateAbstractBaseTile;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class StargateMenu extends JSGAbstractMenu {

    public final StargateAbstractBaseTile blockEntity;
    private final Level level;

    public StargateMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()));
    }

    public StargateMenu(int id, Inventory inv, BlockEntity entity) {
        super(ScreenTypes.STARGATE_MENU.get(), id, StargateAbstractBaseTile.STARGATE_CONTAINER_SIZE);
        checkContainerSize(inv, StargateAbstractBaseTile.STARGATE_CONTAINER_SIZE);
        blockEntity = ((StargateAbstractBaseTile) entity);
        this.level = inv.player.level;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            // Upgrades 2x2 (index 0-3)
            for (int row = 0; row < 2; row++) {
                for (int col = 0; col < 2; col++) {
                    addSlot(new SlotItemHandler(handler, row * 2 + col, 9 + 18 * col, 18 + 18 * row));
                }
            }

            // Capacitors 1x3 (index 4-6)
            for (int col = 0; col < 3; col++) {
                final int capacitorIndex = col;

                addSlot(new SlotItemHandler(handler, col + 4, 115 + 18 * col, 40) {
                    @Override
                    public boolean mayPlace(@Nonnull ItemStack stack) {
                        if(!(stack.getItem() instanceof EnergyItem))
                            return false;
                        if(capacitorIndex + 1 <= blockEntity.getSupportedCapacitors())
                            return super.mayPlace(stack);
                        return false;
                    }
                });
            }

            // Page slots (index 7-9)
            for (int i = 0; i < 3; i++) {
                addSlot(new SlotItemHandler(handler, i + 7, -22, 89 + 22 * i){
                    @Override
                    public boolean mayPlace(@Nonnull ItemStack stack) {
                        if(!(stack.getItem() instanceof NoteBookPageItem))
                            return false;
                        return super.mayPlace(stack);
                    }
                });
            }

            // Biome overlay slot (index 10)
            addSlot(new SlotItemHandler(handler, 10, 0, 0){
                @Override
                public boolean mayPlace(@Nonnull ItemStack stack) {
                    if(!(blockEntity.canUseAsOverlay(stack)))
                        return false;
                    return super.mayPlace(stack);
                }
            });

            // Shield/Iris Upgrade (index 11)
            addSlot(new SlotItemHandler(handler, 11, 17 + 18 * 3, 27){
                @Override
                public boolean mayPlace(@Nonnull ItemStack stack) {
                    if(!(stack.getItem() instanceof IrisItem))
                        return false;
                    return super.mayPlace(stack);
                }
            });
        });
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, blockEntity.getBlockState().getBlock().defaultBlockState().getBlock());
    }
}
