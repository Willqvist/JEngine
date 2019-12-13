package engine.render;

import engine.materials.StandardMaterial;
import engine.model.Model;
import engine.model.ModelInstance;
import engine.physics.AABB;
import engine.shader.ColorShader;
import engine.tools.RoffColor;

public class AABBRenderer {
    private static ModelInstance instance;
    private static StandardMaterial mat;
    public static void init(){
        mat = new StandardMaterial();
        mat.setShader(new ColorShader());
        instance = new ModelInstance(Model.createOutlinedCube(0.08f));
        instance.setMaterial(mat);
    }
    public static void setColor(RoffColor color){
        mat.setColor(color);
    }

    public static void render(Renderer renderer, AABB ab){
        instance.getTransform().setScale(ab.getWidth(),ab.getHeight(),ab.getDepth());
        instance.getTransform().setPosition(ab.getPosition());
        Renderer.RenderMode mode = renderer.getRenderMode();
        renderer.setRenderMode(Renderer.RenderMode.LINES);
        renderer.render(instance);
        renderer.setRenderMode(mode);
    }
    public static void render(Renderer renderer, AABB ab,float addedHeight){
        instance.getTransform().setScale(ab.getWidth(),ab.getHeight()+addedHeight,ab.getDepth());
        instance.getTransform().setPosition(ab.getPosition());
        Renderer.RenderMode mode = renderer.getRenderMode();
        renderer.setRenderMode(Renderer.RenderMode.LINES);
        renderer.render(instance);
        renderer.setRenderMode(mode);
    }
}
