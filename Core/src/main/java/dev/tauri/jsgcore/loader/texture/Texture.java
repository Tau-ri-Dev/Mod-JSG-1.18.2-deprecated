package dev.tauri.jsgcore.loader.texture;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import dev.tauri.jsgcore.utils.Logging;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;

public class Texture {
	
	private final int textureId;
	public Texture(BufferedImage bufferedImage, boolean desaturate) {
		Logging.info("H1");
		this.textureId = GL11.glGenTextures();
		Logging.info("H2");
		
		if (desaturate) {
			Logging.info("Started desaturation of texture");
			
        	WritableRaster raster = bufferedImage.getRaster();
        	
        	int w = raster.getWidth();
        	int h = raster.getHeight();
        	
        	List<RasterThread> th = Arrays.asList(
        			new RasterThread(0, 0, w/2, h/2, raster),
        			new RasterThread(w/2, 0, w/2, h/2, raster),
        			new RasterThread(w/2, h/2, w/2, h/2, raster),
        			new RasterThread(0, h/2, w/2, h/2, raster));
        	
        	for (Thread t : th)
        		t.start();
        	
        	try {
	        	th.get(0).join();
	        	th.get(1).join();
	        	th.get(2).join();
	        	th.get(3).join();
        	}
        	
        	catch (InterruptedException e) {
        		e.printStackTrace();
        	}
        	
			Logging.info("Finished desaturation of texture");
		}
		
		uploadTextureImageAllocate(textureId, bufferedImage);
		Logging.info("H3");
	}


	// ---- Copy from minecraft 1.12.2 - edited by MineDragonCZ_ ----
	private final IntBuffer DATA_BUFFER = ByteBuffer.allocateDirect(4194304 << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
	public void uploadTextureImageAllocate(int textureId, BufferedImage texture){
		Logging.info("P1");
		TextureUtil.prepareImage(textureId, texture.getWidth(), texture.getHeight());
		Logging.info("P2");
		GlStateManager._bindTexture(textureId);
		Logging.info("P3");
		uploadTextureImageSubImpl(texture);
		Logging.info("P4");
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
			Logging.info("O1");
			copyToBufferPos(ant, k1);
			Logging.info("O2");
			GlStateManager._texImage2D(3553, 0, 0, i1, i, j1, 32993, 33639, DATA_BUFFER);
			Logging.info("O3");
		}
	}
	private void copyToBufferPos(int[] ints, int i) {
		DATA_BUFFER.clear();
		DATA_BUFFER.put(ints, 0, i);
		DATA_BUFFER.position(0).limit(i);
	}
	// ---- END OF COPY ----

	
	public void deleteTexture() {
		GL11.glDeleteTextures(textureId);
	}

	public void bindTexture() {
		GlStateManager._bindTexture(textureId);
	}
	
	private static class RasterThread extends Thread {
		
		private final int x;
		private final int y;
		private final int w;
		private final int h;
		private final WritableRaster raster;

		public RasterThread(int x, int y, int w, int h, WritableRaster raster) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.raster = raster;
		}
		
		@Override
		public void run() {
			Logging.debug(String.format("Starting thread (%d, %d) to (%d, %d)", x, y, x+w, y+h));
			
			float[] pixel = new float[3];
        	float gray;
			
			for (int w=x; w<x+this.w; w++) {
        		for (int h=y; h<y+this.h; h++) {
        			raster.getPixel(w, h, pixel);
        			if (pixel[0] == 0 && pixel[1] == 0 && pixel[2] == 0)
        				continue;
        			
        			gray = 0;
        			gray += pixel[0] * 0.299;
        			gray += pixel[1] * 0.587;
        			gray += pixel[2] * 0.114;
        			
        			pixel[0] = gray;
        			pixel[1] = gray;
        			pixel[2] = gray;
        			
        			raster.setPixel(w, h, pixel);
        		}	            		
        	}
						
			Logging.debug(String.format("Finished thread (%d, %d) to (%d, %d)", x, y, x+w, y+h));
		}
	}
}