package dev.tauri.jsgcore.loader.model;

public class OBJCord {
    public final float v1;
    public final float v2;
    public final float v3;

    public final float n1;
    public final float n2;
    public final float n3;

    public final float t1;
    public final float t2;

    public final boolean hasTex;

    public OBJCord(float v1, float v2, float v3, float n1, float n2, float n3){
        hasTex = false;

        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;

        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;

        this.t1 = 0;
        this.t2 = 0;
    }

    public OBJCord(float v1, float v2, float v3, float n1, float n2, float n3, float t1, float t2){
        hasTex = true;

        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;

        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;

        this.t1 = t1;
        this.t2 = t2;
    }
}
