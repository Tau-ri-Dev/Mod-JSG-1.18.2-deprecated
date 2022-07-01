package dev.tauri.jsgmilkyway.creativetab;

import dev.tauri.jsgmilkyway.block.MilkyWayBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static dev.tauri.jsgmilkyway.JSGMilkyWay.MOD_ID;

public class JSGTabs {
    public static final CreativeModeTab TAB_MILKYWAY = new CreativeModeTab(MOD_ID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(MilkyWayBlocks.SG_BASE_BLOCK.get());
        }
    };
}
