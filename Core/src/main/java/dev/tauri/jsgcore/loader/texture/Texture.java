package dev.tauri.jsgcore.loader.texture;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

@OnlyIn(Dist.CLIENT)
public class Texture extends AbstractTexture {
	private final ResourceLocation resourceLocation;
	public Texture(BufferedImage bufferedImage, ResourceLocation resourceLocation) {
		this.resourceLocation = resourceLocation;
		if (!RenderSystem.isOnRenderThread()) {
			RenderSystem.recordRenderCall(() -> {
				uploadTextureImageAllocate(getId(), bufferedImage);
			});
		}
		else{
			uploadTextureImageAllocate(getId(), bufferedImage);
		}
	}


	public void deleteTexture() {
		releaseId();
	}

	public void bindTexture() {
		RenderSystem.enableTexture();
		RenderSystem.enableBlend();
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(this.id, resourceLocation);
		GlStateManager._bindTexture(getId());
		//Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(resourceLocation);
	}

	@Override
	public void load(@NotNull ResourceManager resourceManager) throws IOException {
	}


	// ---- Copy from minecraft 1.12.2 - edited by MineDragonCZ_ ----
	private final IntBuffer DATA_BUFFER = ByteBuffer.allocateDirect(4194304 << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
	public void uploadTextureImageAllocate(int textureId, BufferedImage texture){
		TextureUtil.prepareImage(textureId, texture.getWidth(), texture.getHeight());
		GlStateManager._bindTexture(textureId);
		uploadTextureImageSubImpl(texture);
	}
	private void uploadTextureImageSubImpl(BufferedImage texture){
		int i = texture.getWidth();
		int j = texture.getHeight();
		int k = 4194304 / i;
		int[] ant = new int[k * i];

		for (int l = 0; l < i * j; l += i * k)
		{
			int i1 = l / i;
			int j1 = Math.min(k, j - i1);
			int k1 = i * j1;
			texture.getRGB(0, i1, i, j1, ant, 0, i);
			copyToBufferPos(ant, k1);
			GlStateManager._texImage2D(3553, 0, 0, i1, i, j1, 32993, 33639, DATA_BUFFER);
		}
	}
	private void copyToBufferPos(int[] ints, int i) {
		DATA_BUFFER.clear();
		DATA_BUFFER.put(ints, 0, i);
		DATA_BUFFER.position(0).limit(i);
	}
	// ---- END OF COPY ----
}