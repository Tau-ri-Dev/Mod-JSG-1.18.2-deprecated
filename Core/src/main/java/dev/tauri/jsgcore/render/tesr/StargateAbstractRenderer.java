package dev.tauri.jsgcore.render.tesr;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import dev.tauri.jsgcore.render.state.StargateAbstractRendererState;
import dev.tauri.jsgcore.tileentity.StargateAbstractBaseTile;
import dev.tauri.jsgcore.utils.FacingToRotation;
import dev.tauri.jsgcore.utils.Logging;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public abstract class StargateAbstractRenderer<S extends StargateAbstractRendererState> implements BlockEntityRenderer<StargateAbstractBaseTile> {
    private static final float GATE_DIAMETER = 10.1815f;
    @Override
    public void render(@NotNull StargateAbstractBaseTile baseTile, float renderTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        @SuppressWarnings("unchecked") S rendererState = (S) baseTile.getRendererStateClient();
        if(rendererState != null){
            if(baseTile.isMerged) {

                stack.pushPose();
                //stack.translate(baseTile.getPos().getX(), baseTile.getPos().getY(), baseTile.getPos().getZ());
                stack.translate(0.50, GATE_DIAMETER / 2 - 0.62, 0.50);
                stack.scale(0.83f, 0.83f, 0.83f);

                stack.mulPose(new Quaternion(0, FacingToRotation.getHorizontal(baseTile.facing), 0, true));
                stack.mulPose(new Quaternion(FacingToRotation.getVertical(baseTile.facing), 0, 0, true));

                renderGate(baseTile, renderTicks, stack, bufferSource);
                renderRing(baseTile, renderTicks, stack, bufferSource);
                renderChevrons(baseTile, renderTicks, stack, bufferSource);

                stack.popPose();
            }
            else{
                Logging.info("not merged");
            }
        }
        else{
            Logging.info("null");
        }
    }

    public abstract void renderGate(StargateAbstractBaseTile e, float renderTicks, PoseStack stack, MultiBufferSource bufferSource);
    public abstract void renderChevrons(StargateAbstractBaseTile e, float renderTicks, PoseStack stack, MultiBufferSource bufferSource);
    public abstract void renderRing(StargateAbstractBaseTile e, float renderTicks, PoseStack stack, MultiBufferSource bufferSource);

    @Override
    public boolean shouldRenderOffScreen(@NotNull StargateAbstractBaseTile baseTile) {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 65536;
    }

    @Override
    public boolean shouldRender(@NotNull StargateAbstractBaseTile baseTile, @NotNull Vec3 vec3) {
        return true;
    }
}
