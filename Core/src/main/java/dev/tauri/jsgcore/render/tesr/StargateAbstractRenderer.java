package dev.tauri.jsgcore.render.tesr;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import dev.tauri.jsgcore.render.state.StargateAbstractRendererState;
import dev.tauri.jsgcore.tileentity.StargateAbstractBaseTile;
import dev.tauri.jsgcore.utils.FacingToRotation;
import dev.tauri.jsgcore.utils.Logging;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

@OnlyIn(Dist.CLIENT)
public abstract class StargateAbstractRenderer<S extends StargateAbstractRendererState>  implements BlockEntityRenderer<StargateAbstractBaseTile> {
    @Override
    public void render(@NotNull StargateAbstractBaseTile baseTile, float renderTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        @SuppressWarnings("unchecked") S rendererState = (S) baseTile.getRendererStateClient();
        if(rendererState != null){
            if(baseTile.isMerged) {
                stack.pushPose();

                stack.mulPose(new Quaternion(0, FacingToRotation.getHorizontal(baseTile.facing), 0, true));
                //GL30.glRotatef(FacingToRotation.getHorizontal(baseTile.facing), 0, 1, 0);
                stack.mulPose(new Quaternion(FacingToRotation.getHorizontal(baseTile.facing), 0, 0, true));
                //GL30.glRotatef(FacingToRotation.getVertical(baseTile.facing), 1, 0, 0);

                renderGate(baseTile, renderTicks);
                renderRing(baseTile, renderTicks);
                renderChevrons(baseTile, renderTicks);
                Logging.info("cs1");
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

    public abstract void renderGate(StargateAbstractBaseTile e, float renderTicks);
    public abstract void renderChevrons(StargateAbstractBaseTile e, float renderTicks);
    public abstract void renderRing(StargateAbstractBaseTile e, float renderTicks);

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
