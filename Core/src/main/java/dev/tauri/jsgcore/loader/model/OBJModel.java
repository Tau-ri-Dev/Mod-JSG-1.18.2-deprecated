package dev.tauri.jsgcore.loader.model;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL46.*;

public class OBJModel {
	
	//private int drawCount;
	private boolean modelInitialized;
	
	/*private int vId;
	private int tId;
	private int nId;
	private int iId;*/
	private final boolean hasTex;
	
	/*private final float[] vertices;
	private final float[] textureCoords;
	private final float[] normals;
	private final int[] indices;
	
	
	public OBJModel(float[] vertices, float[] textureCoords, float[] normals, int[] indices, boolean hasTex) {
		this.vertices = vertices;
		this.textureCoords = textureCoords;
		this.normals = normals;
		this.indices = indices;
		this.hasTex = hasTex;

		modelInitialized = false;
	}*/

	private final List<OBJCord> cords;

	public OBJModel(List<OBJCord> cords, boolean hasTex) {
		this.cords = cords;
		this.hasTex = hasTex;
		modelInitialized = false;
	}
	
	public void initializeModel() {
		/*drawCount = indices.length;

		vId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vId);
		glBufferData(GL_ARRAY_BUFFER, createFloatBuffer(vertices), GL_STATIC_DRAW);
		
		if (hasTex) {
			tId = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, tId);
			glBufferData(GL_ARRAY_BUFFER, createFloatBuffer(textureCoords), GL_STATIC_DRAW);
		}
		
		nId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, nId);
		glBufferData(GL_ARRAY_BUFFER, createFloatBuffer(normals), GL_STATIC_DRAW);
		
		iId = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, createIntBuffer(indices), GL_STATIC_DRAW);*/
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		modelInitialized = true;
	}
	
	public void render() {
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder buff = tesselator.getBuilder();
		RenderSystem.enableTexture();
		buff.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

		if (!modelInitialized)
			initializeModel();

		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_NORMAL_ARRAY);
		if (hasTex) glEnableClientState(GL_TEXTURE_COORD_ARRAY);

		for(OBJCord cord : cords){
			buff.vertex(cord.v1, cord.v2, cord.v3);
			buff.color(0xa2a2a2);
			if(cord.hasTex){
				buff.uv(cord.t1, cord.t2);
			}
			buff.normal(cord.n1, cord.n2, cord.n3);
			buff.endVertex();
		}
		
		/*glBindBuffer(GL_ARRAY_BUFFER, vId);
		nglVertexPointer(3, GL_FLOAT, 0, caps.glVertexPointer);
		
		if (hasTex) {
			glBindBuffer(GL_ARRAY_BUFFER, tId);
			nglTexCoordPointer(2, GL_FLOAT, 0, caps.glTexCoordPointer);
		}
		
		glBindBuffer(GL_ARRAY_BUFFER, nId);
		nglNormalPointer(GL_FLOAT, 0, caps.glNormalPointer);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iId);		
		glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0);*/
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		glDisableClientState(GL_VERTEX_ARRAY);
		glDisableClientState(GL_NORMAL_ARRAY);
		if (hasTex) glDisableClientState(GL_TEXTURE_COORD_ARRAY);

		tesselator.end();
	}

	/*public void vertex(float a, float b, float c, float d, float e, float f, float g, float h, float i, int j, int k, float l, float m, float n) {
		this.vertex((double)a, (double)b, (double)c);
		this.color(d, e, f, g);
		this.uv(h, i);
		this.overlayCoords(j);
		this.uv2(k);
		this.normal(l, m, n);
		this.endVertex();
	}*/
	
	private FloatBuffer createFloatBuffer(float[] input) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(input.length);
		buffer.put(input).flip();
		
		return buffer;
	}
	
	private IntBuffer createIntBuffer(int[] input) {
		IntBuffer buffer = BufferUtils.createIntBuffer(input.length);
		buffer.put(input).flip();
		
		return buffer;
	}
}
