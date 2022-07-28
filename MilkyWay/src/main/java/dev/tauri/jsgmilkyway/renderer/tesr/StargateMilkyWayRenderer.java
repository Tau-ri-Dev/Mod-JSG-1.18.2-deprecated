package dev.tauri.jsgmilkyway.renderer.tesr;

import com.mojang.blaze3d.platform.GlStateManager;
import dev.tauri.jsgcore.render.ChevronEnum;
import dev.tauri.jsgcore.render.tesr.StargateClassicRenderer;
import dev.tauri.jsgcore.tileentity.StargateAbstractBaseTile;
import dev.tauri.jsgcore.utils.BiomeOverlayEnum;
import dev.tauri.jsgmilkyway.renderer.EnumElement;
import dev.tauri.jsgmilkyway.renderer.state.StargateMilkyWayRendererState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class StargateMilkyWayRenderer extends StargateClassicRenderer<StargateMilkyWayRendererState> {
    @Override
    public void renderIris(StargateAbstractBaseTile stargateAbstractBaseTile, float v) {

    }

    @Override
    public void renderGate(StargateAbstractBaseTile stargateAbstractBaseTile, float v) {
        EnumElement.STARGATE_GATE.bindTextureAndRender(BiomeOverlayEnum.NORMAL);
    }

    @Override
    public void renderChevrons(StargateAbstractBaseTile stargateAbstractBaseTile, float v) {
        BiomeOverlayEnum overlay = BiomeOverlayEnum.NORMAL;

        float chevronTranslation = 0;

        for(ChevronEnum chevron : ChevronEnum.values()) {

            if(chevron.index == 9) chevronTranslation = 1f;
            GL11.glPushMatrix();
            GL11.glRotatef(chevron.rotation, 1, 0, 0);
            EnumElement.STARGATE_CHEVRON_BACK.bindTextureAndRender(overlay);
            EnumElement.STARGATE_CHEVRON_LIGHT.bindTextureAndRender(overlay);
            EnumElement.STARGATE_CHEVRON_FRAME.bindTextureAndRender(overlay);

            GL11.glPushMatrix();
            GL11.glTranslatef(0, chevronTranslation, 0);
            EnumElement.STARGATE_CHEVRON_MOVING.bindTextureAndRender(overlay);
            GL11.glPopMatrix();

            GL11.glPopMatrix();
        }


        StargateMilkyWayRendererState rendererState = (StargateMilkyWayRendererState) stargateAbstractBaseTile.getRendererStateClient();
        if(rendererState != null) {
            rendererState.chevronTextureList.iterate(Objects.requireNonNull(stargateAbstractBaseTile.getLevel()), v);
        }
    }

    @Override
    public void renderRing(StargateAbstractBaseTile stargateAbstractBaseTile, float v) {

    }
}
