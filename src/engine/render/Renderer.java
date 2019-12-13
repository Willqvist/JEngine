package engine.render;

import engine.camera.Camera;
import engine.model.ModelInstance;
import org.lwjgl.opengl.GL11;

public abstract class Renderer {

    protected RenderMode mode = RenderMode.TRIANGLES;
    protected Camera camera;

    public Camera getCamera(){
        return this.camera;
    }

    public void setRenderMode(RenderMode mode){
        this.mode = mode;
    }

    public void begin(Camera camera){
        this.camera = camera;
    }

    public abstract void render(IRenderable instance);

    public RenderMode getRenderMode(){
        return mode;
    }

    public enum RenderMode{
        TRIANGLES(GL11.GL_TRIANGLES),QUADS(GL11.GL_QUADS),LINES(GL11.GL_LINES);
        private int id;
        RenderMode(int id){
            this.id = id;
        }

        public int getGLType(){
            return id;
        }
    }
}
