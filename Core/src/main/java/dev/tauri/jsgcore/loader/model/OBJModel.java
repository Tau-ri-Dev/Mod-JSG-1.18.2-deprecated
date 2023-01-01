package dev.tauri.jsgcore.loader.model;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL46.*;

public class OBJModel {

    private int drawCount;
    private boolean modelInitialized;

    private int vId;
    private int tId;
    private int nId;
    private int iId;
    private final boolean hasTex;

    private final float[] vertices;
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
    }

    public void initializeModel() {
        drawCount = indices.length;

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
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, createIntBuffer(indices), GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        modelInitialized = true;
    }

    public void render() {
        if (!modelInitialized)
            initializeModel();

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_NORMAL_ARRAY);
        if (hasTex) glEnableClientState(GL_TEXTURE_COORD_ARRAY);

        glBindBuffer(GL_ARRAY_BUFFER, vId);
        glVertexAttribPointer(vId, 3, GL_FLOAT, false, 3 * Float.SIZE, 0);
        glEnableVertexAttribArray(vId);

        if (hasTex) {
            glBindBuffer(GL_ARRAY_BUFFER, tId);
            glVertexAttribPointer(tId, 2, GL_FLOAT, false, 2 * Float.SIZE, 0);
            glEnableVertexAttribArray(tId);
            //glTexCoordPointer(2, GL_FLOAT, 0, 0);
        }

        glBindBuffer(GL_ARRAY_BUFFER, nId);
        glVertexAttribPointer(nId, 1, GL_FLOAT, false, Float.SIZE, 0);
        glEnableVertexAttribArray(nId);
        //glNormalPointer(GL_FLOAT, 0, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iId);
        RenderSystem.drawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT);
        //glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        glDisableClientState(GL_VERTEX_ARRAY);
        glDisableClientState(GL_NORMAL_ARRAY);
        if (hasTex) glDisableClientState(GL_TEXTURE_COORD_ARRAY);
    }

    private FloatBuffer createFloatBuffer(float[] input) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(input.length);
        ((Buffer) buffer.put(input)).flip();

        return buffer;
    }

    private IntBuffer createIntBuffer(int[] input) {
        IntBuffer buffer = BufferUtils.createIntBuffer(input.length);
        ((Buffer) buffer.put(input)).flip();

        return buffer;
    }
}
