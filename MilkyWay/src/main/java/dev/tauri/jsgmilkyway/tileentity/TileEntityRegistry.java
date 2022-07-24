package dev.tauri.jsgmilkyway.tileentity;

import dev.tauri.jsgmilkyway.JSGMilkyWay;
import dev.tauri.jsgmilkyway.block.MilkyWayBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TileEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, JSGMilkyWay.MOD_ID);

    public static void register(IEventBus eb){
        TILE_ENTITY_TYPES.register(eb);
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> registerTile(String name, Block block, BlockEntityType.BlockEntitySupplier<T> supplier){
        return TILE_ENTITY_TYPES.register(name, () -> BlockEntityType.Builder.of(supplier, block).build(null));
    }

    public static final RegistryObject<BlockEntityType<StargateMilkyWayBaseTile>> SG_MILKYWAY_BASE_TILE = TILE_ENTITY_TYPES.register("sg_milkyway_base", () -> BlockEntityType.Builder.of(StargateMilkyWayBaseTile::new, MilkyWayBlocks.SG_BASE_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<StargateMilkyWayMemberTile>> SG_MILKYWAY_CHEVRON_TILE = TILE_ENTITY_TYPES.register("sg_milkyway_chevron", () -> BlockEntityType.Builder.of(StargateMilkyWayMemberTile::new, MilkyWayBlocks.SG_CHEVRON_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<StargateMilkyWayMemberTile>> SG_MILKYWAY_RING_TILE = TILE_ENTITY_TYPES.register("sg_milkyway_ring", () -> BlockEntityType.Builder.of(StargateMilkyWayMemberTile::new, MilkyWayBlocks.SG_RING_BLOCK.get()).build(null));
}
