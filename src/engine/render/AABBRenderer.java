package engine.render;

import engine.materials.StandardMaterial;
import engine.model.Model;
import engine.model.ModelInstance;
import engine.physics.AABB;
import engine.shader.ColorShader;
import engine.tools.RoffColor;
import org.joml.Vector3f;

import java.text.NumberFormat;

public class AABBRenderer {
    private static ModelInstance instance;
    private static StandardMaterial mat;
    private static Transform transform;
    private static Model model;
    public static void init(){
        mat = new StandardMaterial();
        mat.setShader(new ColorShader());
        model = Model.createOutlinedCube(/*0.08f*/1);
        transform = new Transform(0,0,0);
    }
    public static void setColor(RoffColor color){
        mat.setColor(color);
    }

    public static void render(Renderer renderer, AABB ab){
        transform.setScale(ab.getWidth(),ab.getHeight(),ab.getDepth());
        transform.setPosition(ab.getPosition());
        Renderer.RenderMode mode = renderer.getRenderMode();
        renderer.setRenderMode(Renderer.RenderMode.LINES);
        renderer.render(model,transform,mat);
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
