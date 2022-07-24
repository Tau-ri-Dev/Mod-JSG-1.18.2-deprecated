package dev.tauri.jsgmilkyway.block;

import dev.tauri.jsgmilkyway.JSGMilkyWay;
import dev.tauri.jsgmilkyway.creativetab.JSGTabs;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static dev.tauri.jsgmilkyway.item.MilkyWayItems.ITEMS_REGISTRY;

public class MilkyWayBlocks {
    public static final DeferredRegister<Block> BLOCKS_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, JSGMilkyWay.MOD_ID);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> r = BLOCKS_REGISTRY.register(name, block);
        registerBlockItem(name, r, tab);
        return r;
    }

    private static <T extends Block> void registerBlockItem(String name, Supplier<T> block, CreativeModeTab tab) {
        ITEMS_REGISTRY.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eb) {
        BLOCKS_REGISTRY.register(eb);
    }

    public static final RegistryObject<Block> SG_BASE_BLOCK = registerBlock("sg_base_block", StargateMilkyWayBaseBlock::new, JSGTabs.TAB_MILKYWAY);
    public static final RegistryObject<Block> SG_CHEVRON_BLOCK = registerBlock("sg_chevron_block", StargateMilkyWayChevronBlock::new, JSGTabs.TAB_MILKYWAY);
    public static final RegistryObject<Block> SG_RING_BLOCK = registerBlock("sg_ring_block", StargateMilkyWayRingBlock::new, JSGTabs.TAB_MILKYWAY);
}
