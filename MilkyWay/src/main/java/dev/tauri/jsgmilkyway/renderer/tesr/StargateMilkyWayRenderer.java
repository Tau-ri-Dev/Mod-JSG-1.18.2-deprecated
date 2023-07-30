package dev.tauri.jsgmilkyway.renderer.tesr;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import dev.tauri.jsgcore.render.ChevronEnum;
import dev.tauri.jsgcore.render.tesr.StargateClassicRenderer;
import dev.tauri.jsgcore.tileentity.StargateAbstractBaseTile;
import dev.tauri.jsgcore.utils.BiomeOverlayEnum;
import dev.tauri.jsgmilkyway.renderer.EnumElement;
import dev.tauri.jsgmilkyway.renderer.state.StargateMilkyWayRendererState;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class StargateMilkyWayRenderer extends StargateClassicRenderer<StargateMilkyWayRendererState> {
    @Override
    public void renderIris(StargateAbstractBaseTile stargateAbstractBaseTile, float v, PoseStack stack, MultiBufferSource bufferSource) {

    }

    @Override
    public void renderGate(StargateAbstractBaseTile stargateAbstractBaseTile, float v, PoseStack stack, MultiBufferSource bufferSource) {
        EnumElement.STARGATE_GATE.bindTextureAndRender(BiomeOverlayEnum.NORMAL, stack);
    }

    @Override
    public void renderChevrons(StargateAbstractBaseTile stargateAbstractBaseTile, float v, PoseStack stack, MultiBufferSource bufferSource) {
        BiomeOverlayEnum overlay = BiomeOverlayEnum.NORMAL;

        float chevronTranslation = 0;

        for (ChevronEnum chevron : ChevronEnum.values()) {

            if (chevron.index == 9) chevronTranslation = 1f;
            stack.pushPose();
            stack.mulPose(new Quaternion(chevron.rotation, 0, 0, true));
            EnumElement.STARGATE_CHEVRON_BACK.bindTextureAndRender(overlay, stack);
            EnumElement.STARGATE_CHEVRON_LIGHT.bindTextureAndRender(overlay, stack);
            EnumElement.STARGATE_CHEVRON_FRAME.bindTextureAndRender(overlay, stack);

            stack.pushPose();
            stack.translate(0, chevronTranslation, 0);
            EnumElement.STARGATE_CHEVRON_MOVING.bindTextureAndRender(overlay, stack);
            stack.popPose();

            stack.popPose();
        }


        StargateMilkyWayRendererState rendererState = (StargateMilkyWayRendererState) stargateAbstractBaseTile.getRendererStateClient();
        if (rendererState != null) {
            rendererState.chevronTextureList.iterate(Objects.requireNonNull(stargateAbstractBaseTile.getLevel()), v);
        }
    }

    @Override
    public void renderRing(StargateAbstractBaseTile stargateAbstractBaseTile, float v, PoseStack stack, MultiBufferSource bufferSource) {

    }
}
