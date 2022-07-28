package dev.tauri.jsgcore.loader.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import dev.tauri.jsgcore.loader.FolderLoader;
import dev.tauri.jsgcore.utils.Logging;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TextureLoader {

	private final String modId;
	private final Class modMainClass;
	public final String texturesPath;
	private final Map<ResourceLocation, Texture> LOADED_TEXTURES = new HashMap<>();

	public TextureLoader(String modId, Class modMainClass){
		this.modId = modId;
		this.modMainClass = modMainClass;
		this.texturesPath = "assets/" + modId + "/textures/tesr";
		Logging.info("Created TextureLoader for domain " + modId);
	}
	
	public Texture getTexture(ResourceLocation resourceLocation) {
		return LOADED_TEXTURES.get(resourceLocation);
	}

	public boolean isTextureLoaded(ResourceLocation resourceLocation) {
		return LOADED_TEXTURES.containsKey(resourceLocation);
	}
	
	public void loadTextures(){
		ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
		try {
			for (Texture texture : LOADED_TEXTURES.values())
				texture.deleteTexture();

			List<String> texturePaths = FolderLoader.getAllFiles(modMainClass, texturesPath, ".png", ".jpg");

			long start = System.currentTimeMillis();

			Logging.info("Started loading textures for domain " + modId + "...");

			for (String texturePath : texturePaths) {
				texturePath = texturePath.replaceFirst("assets/" + modId + "/", "");

				ResourceLocation resourceLocation = new ResourceLocation(modId, texturePath);
				Resource resource = null;

				try {
					Logging.info("Loading texture: " + texturePath + " for domain " + modId);
					resource = resourceManager.getResource(resourceLocation);
					BufferedImage bufferedImage = readBufferedImage(resource.getInputStream());
					LOADED_TEXTURES.put(resourceLocation, new Texture(bufferedImage));
					Logging.info("Texture " + texturePath + " for domain " + modId + " loaded!");
				} catch (IOException e) {
					Logging.error("Failed to load texture " + texturePath);
					e.printStackTrace();
				} finally {
					IOUtils.closeQuietly((Closeable) resource);
				}
			}

			Logging.info("Loaded " + texturePaths.size() + " textures for domain " + modId + " in " + (System.currentTimeMillis() - start) + " ms");
		}
		catch (Exception ignored){}
	}

	public ResourceLocation getTextureResource(String texture) {
		return new ResourceLocation(modId, "textures/tesr/" + texture);
	}

	public ResourceLocation getBlockTexture(BlockState blockState) {
		Minecraft minecraft = Minecraft.getInstance();
		BlockRenderDispatcher ren = minecraft.getBlockRenderer();
		String blockTexture = ren.getBlockModel(blockState).getQuads(blockState, Direction.NORTH, new Random()).get(0).getSprite().getName().toString();
		String domain = "minecraft";
		String path = blockTexture;
		int domainSeparator = blockTexture.indexOf(':');

		if (domainSeparator >= 0) {
			path = blockTexture.substring(domainSeparator + 1);

			if (domainSeparator > 1) {
				domain = blockTexture.substring(0, domainSeparator);
			}
		}

		String resourcePath = "textures/" + path + ".png";  // base path and PNG are hardcoded in Minecraft
		return new ResourceLocation(domain.toLowerCase(), resourcePath);
	}

	// ---- Copy from minecraft 1.12.2 - edited by MineDragonCZ_ ----
	public static BufferedImage readBufferedImage(InputStream imageStream) throws IOException {
		BufferedImage bufferedimage;

		try{
			bufferedimage = ImageIO.read(imageStream);
		}
		finally{
			IOUtils.closeQuietly(imageStream);
		}
		return bufferedimage;
	}
	// ---- END OF COPY ----
}
