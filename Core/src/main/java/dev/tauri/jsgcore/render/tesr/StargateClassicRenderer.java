package dev.tauri.jsgcore.render.tesr;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.tauri.jsgcore.render.state.StargateAbstractRendererState;
import dev.tauri.jsgcore.render.state.StargateClassicRendererState;
import dev.tauri.jsgcore.tileentity.StargateAbstractBaseTile;
import dev.tauri.jsgcore.tileentity.StargateClassicBaseTile;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public abstract class StargateClassicRenderer<S extends StargateClassicRendererState> extends StargateAbstractRenderer<S> {
    @Override
    public void render(@NotNull StargateAbstractBaseTile e, float renderTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        StargateClassicBaseTile baseTile = (StargateClassicBaseTile) e;
        super.render(e, renderTicks, stack, bufferSource, combinedLight, combinedOverlay);
        renderIris(baseTile, renderTicks);
    }

    public void renderChevrons(StargateAbstractBaseTile e, float renderTicks){
        @SuppressWarnings("unchecked") S rendererState = (S) e.getRendererStateClient();
        if(rendererState != null) {
            //rendererState.chevronTextureList.iterate(Objects.requireNonNull(e.getLevel()), renderTicks);
        }
    }

    public abstract void renderIris(StargateAbstractBaseTile e, float renderTicks);
}
