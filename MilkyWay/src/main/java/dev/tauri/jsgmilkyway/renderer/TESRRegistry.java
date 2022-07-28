package dev.tauri.jsgmilkyway.renderer;

import dev.tauri.jsgmilkyway.renderer.tesr.StargateMilkyWayRenderer;
import dev.tauri.jsgmilkyway.tileentity.TileEntityRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class TESRRegistry {
    private static <T extends BlockEntity> void registerTESR(BlockEntityType<? extends T> entityType, BlockEntityRenderer<T> renderer){
        BlockEntityRenderers.register(entityType, context -> renderer);
    }

    public static void register(){
        registerTESR(TileEntityRegistry.SG_MILKYWAY_BASE_TILE.get(), new StargateMilkyWayRenderer());
    }
}
