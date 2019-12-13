package engine.model;

import engine.render.IRenderable;
import engine.render.Material;
import engine.materials.MaterialBank;
import engine.render.Transform;

public class ModelInstance implements IRenderable {
    private Model model;
    private Transform transform;
    private Material material = MaterialBank.getMaterial("standard");

    public ModelInstance(Model model){
        this.model = model;
        transform = new Transform(0,0,0);
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public Model getModel() {
        return model;
    }

    @Override
    public Transform getTransform() {
        return transform;
    }

    @Override
    public Material getMaterial() {
        return material;
    }
}
