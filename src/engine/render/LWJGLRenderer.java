package engine.render;

import engine.CoreDebugger;
import engine.model.ModelInstance;
import org.lwjgl.opengl.GL11;

public class LWJGLRenderer extends Renderer {

    @Override
    public void render(IRenderable instance) {
        instance.getModel().bind();
        instance.getMaterial().bind(instance.getModel(),instance.getTransform(),camera);
        GL11.glDrawArrays(mode.getGLType(), 0, instance.getModel().getVertexCount());
        CoreDebugger.init().onRender();
    }

}
