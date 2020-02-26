package engine.render;

import engine.materials.StandardMaterial;
import engine.model.Model;
import engine.model.ModelBuilder;
import engine.model.ModelInstance;
import engine.shader.CubeMapShader;
import engine.tools.Const;
import engine.tools.FileTools;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

public class CubeMap {
    public static final String[] TEXTURE_NAMES = new String[]{"right","left","top","bottom","back","front"};
    public static final float[] offset = new float[]{1/3f,0.5f,2/3f,0.5f,2/3f,0,0,0,0,0.5f,1/3f,0};
    protected int texture;
    protected CubeMapShader shader;
    protected static Model model;
    protected ModelInstance instance;
    protected int tick = 0;
    public CubeMap(String name){
        if(model == null)
            model = ModelBuilder.createCube(400,400,400);
        texture = GL11.glGenTextures();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP,texture);
        glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

        int j = 0;
        for (int i = 0; i < offset.length; i+= 2) {
            FileTools.GeneratedBuffer data = FileTools.getBuffer(Const.CUBEMAP_SRC + name + ".png",offset[i],offset[i+1],1/3f,0.5f);
            GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X+j,0, GL11.GL_RGBA8,data.width,data.height,0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE,data.buffer);
            //GL11.glTexSubImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X+j,0,(int)(offset[i]*data.width),(int)(offset[i+1]*data.height),(int)(data.width*1/3f),(int)(data.height*0.5f),GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE,data.buffer);
            System.out.println("ERROR: " + glGetError());
            j++;
        }
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP,0);
        shader = new CubeMapShader("skybox");
        instance = new ModelInstance(model);
        StandardMaterial mat = new StandardMaterial();
        mat.setShader(shader);
        instance.setMaterial(mat);
    }

    public void destroy(){

    }
    public void render(Renderer renderer){

        GL11.glDepthMask(false);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP,texture);
        Renderer.RenderMode mode = renderer.getRenderMode();
        renderer.setRenderMode(Renderer.RenderMode.TRIANGLES);
        shader.bind();
        //shader.setTime(tick/2000f);
        renderer.render(instance);
        renderer.setRenderMode(mode);
        GL11.glDepthMask(true);


    }
}
