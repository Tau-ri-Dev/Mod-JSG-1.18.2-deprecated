package dev.tauri.jsgmilkyway.tileentity;

import dev.tauri.jsgcore.stargate.merging.StargateAbstractMergeHelper;
import dev.tauri.jsgcore.stargate.state.EnumStargateState;
import dev.tauri.jsgcore.stargate.state.StargateAbstractStateManager;
import dev.tauri.jsgcore.stargate.symbols.SymbolsRegistry;
import dev.tauri.jsgcore.tileentity.StargateClassicBaseTile;
import dev.tauri.jsgcore.utils.BiomeOverlayEnum;
import dev.tauri.jsgmilkyway.renderer.state.StargateMilkyWayRendererState;
import dev.tauri.jsgmilkyway.renderer.tesr.StargateMilkyWayRenderer;
import dev.tauri.jsgmilkyway.stargate.merging.StargateMilkyWayMergeHelper;
import dev.tauri.jsgmilkyway.stargate.state.StargateMilkyWayStateManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static dev.tauri.jsgmilkyway.JSGMilkyWay.MOD_ID;

public class StargateMilkyWayBaseTile extends StargateClassicBaseTile {
    public final StargateAbstractMergeHelper MERGE_HELPER = new StargateMilkyWayMergeHelper();

    @OnlyIn(Dist.CLIENT)
    private final StargateMilkyWayRenderer RENDERER = new StargateMilkyWayRenderer();

    public static final BiomeOverlayEnum[] ALLOWED_OVERLAYS = {
            BiomeOverlayEnum.NORMAL,
            BiomeOverlayEnum.AGED,
            BiomeOverlayEnum.SOOTY,
            BiomeOverlayEnum.FROST,
            BiomeOverlayEnum.MOSSY
    };

    public StargateMilkyWayBaseTile(BlockPos blockPos, BlockState blockState) {
        super(TileEntityRegistry.SG_MILKYWAY_BASE_TILE.get(), blockPos, blockState);
        setRendererStateClient(StargateMilkyWayRendererState.builder()
                .setActiveChevrons(9)
                .setFinalActive(true)
                .setSymbolType(Objects.requireNonNull(SymbolsRegistry.managerBySymbolType("milkyway")).getSymbolType())
                .setStargateState(EnumStargateState.IDLE)
                .build());

        ((StargateMilkyWayRendererState) getRendererStateClient()).chevronTextureList.initClient();
    }

    public StargateAbstractStateManager getStateManager(){
        return new StargateMilkyWayStateManager(this);
    }

    @Override
    public ResourceLocation getMenuBackgroundTexture(){
        return new ResourceLocation(MOD_ID, "textures/gui/stargate/base.png");
    }

    @Override
    public boolean canUseAsOverlay(ItemStack stack){
        return true;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return new TranslatableComponent("stargate.gui.title");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        return super.m_7208_(p_39954_, p_39955_, p_39956_);
    }

    public StargateAbstractMergeHelper getMergeHelper(){
        return MERGE_HELPER;
    }
}
