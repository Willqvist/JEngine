package engine.engine2D;

import engine.materials.MaterialBank;
import engine.model.Model;
import engine.model.ModelBuilder;
import engine.render.IRenderable;
import engine.render.Material;
import engine.render.Transform;
import engine.texture.Texture;

public class Sprite implements IRenderable {

    private int textureID, width, height;
    private Material mat = MaterialBank.getMaterial("standard");
    private Transform transform;
    protected Sprite(Texture texture, int width, int height) {
        this.textureID = textureID;
        this.width = width;
        this.height = height;
        this.transform = new Transform(0,0,0);
    }

    public int getTextureID() {
        return textureID;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    @Override
    public Model getModel() {
        return ModelBuilder.createQuad();
    }

    @Override
    public Material getMaterial() {
        return mat;
    }

    @Override
    public Transform getTransform() {
        return transform;
    }
}
