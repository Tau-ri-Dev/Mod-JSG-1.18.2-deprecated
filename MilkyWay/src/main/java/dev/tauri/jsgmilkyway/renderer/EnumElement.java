package dev.tauri.jsgmilkyway.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.tauri.jsgcore.utils.BiomeOverlayEnum;
import dev.tauri.jsgmilkyway.JSGMilkyWay;
import dev.tauri.jsgmilkyway.utils.Logging;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dev.tauri.jsgmilkyway.JSGMilkyWay.*;

/**
 * Used to store all models in this module
 */
public enum EnumElement {
    STARGATE_GATE("stargate/gate.obj", "stargate/gate/gatering.jpg", true),
    STARGATE_RING("stargate/ring.obj", "stargate/gate/gatering.jpg", true),
    STARGATE_CHEVRON_BACK("stargate/chevron_back.obj", "stargate/gate/chevron0.jpg", true),
    STARGATE_CHEVRON_FRAME("stargate/chevron_frame.obj", "stargate/gate/chevron0.jpg", true),
    STARGATE_CHEVRON_LIGHT("stargate/chevron_light.obj", "stargate/gate/chevron0.jpg", true),
    STARGATE_CHEVRON_MOVING("stargate/chevron_moving.obj", "stargate/gate/chevron0.jpg", true),
    ;

    public final ResourceLocation model;
    public final Map<BiomeOverlayEnum, ResourceLocation> biomeTextureResourceMap = new HashMap<>();
    private final List<BiomeOverlayEnum> nonExistingReported = new ArrayList<>();

    EnumElement(String modelPath, String texturePath, boolean byOverlay) {
        this.model = MODEL_LOADER.getModelResource(modelPath);

        for (BiomeOverlayEnum biomeOverlay : BiomeOverlayEnum.values()){
            if (!byOverlay) {
                biomeTextureResourceMap.put(biomeOverlay, TEXTURE_LOADER.getTextureResource(texturePath));
            } else {
                String[] split = texturePath.split("\\.");
                biomeTextureResourceMap.put(biomeOverlay, TEXTURE_LOADER.getTextureResource(split[0] + biomeOverlay.suffix + "." + split[1]));
            }
        }
    }

    public void render(PoseStack ps) {
        MODEL_LOADER.getModel(model).render(ps);
    }

    public void bindTexture(BiomeOverlayEnum biomeOverlay) {
        ResourceLocation resourceLocation = biomeTextureResourceMap.get(biomeOverlay);
        bindTexture(biomeOverlay, resourceLocation);
    }

    public void bindTexture(BiomeOverlayEnum biomeOverlay, ResourceLocation resourceLocation) {
        Minecraft.getInstance().getTextureManager().getTexture(resourceLocation).bind();

        if (!TEXTURE_LOADER.isTextureLoaded(resourceLocation)) {
            if (!nonExistingReported.contains(biomeOverlay)) {
                Logging.error(this + " tried to use BiomeOverlay " + biomeOverlay + " but it doesn't exist. (" + resourceLocation + ")");
                nonExistingReported.add(biomeOverlay);
            }
            resourceLocation = biomeTextureResourceMap.get(BiomeOverlayEnum.NORMAL);
        }

        TEXTURE_LOADER.getTexture(resourceLocation).bindTexture();
    }

    public void bindTextureAndRender(BiomeOverlayEnum biomeOverlay, PoseStack ps) {
        bindTexture(biomeOverlay);
        render(ps);
    }
}
