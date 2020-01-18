package engine.render;

import engine.CoreDebugger;
import engine.model.Model;
import engine.model.ModelInstance;
import org.lwjgl.opengl.GL11;

public class LWJGLRenderer extends Renderer {

    @Override
    public void render(Model model, Transform transform, Material material) {
        if(!material.bind(model,transform,camera)) {
            return;
        }
        model.bind();
        GL11.glDrawArrays(mode.getGLType(), 0, model.getVertexCount());
        CoreDebugger.init().onRender();
    }

}
