package engine.materials;

import engine.camera.Camera;
import engine.model.Model;
import engine.render.Material;
import engine.render.Transform;
import engine.shader.ShaderBank;
import engine.shader.UIShader;
import engine.texture.Texture;
import engine.tools.RoffColor;

import java.awt.*;

public class UIMaterial implements Material {

    private Texture albedoTexture;
    private RoffColor albedoColor = RoffColor.from(Color.WHITE);
    private UIShader shader = ShaderBank.getShader("ui_shader",UIShader.class);
    public void setAlbedoTexture(Texture texture){
        this.albedoTexture = texture;
    }


    public void setShader(UIShader shader){
        this.shader = shader;
    }

    public void setColor(RoffColor color){
        this.albedoColor.set(color);
    }

    public void setAlpha(int alpha) {
        this.albedoColor.setAlpha(alpha);
    }

    /*
    TODO: uniform caching... maybe?
     */
    @Override
    public boolean bind(Model model, Transform transform, Camera camera) {
        shader.bind(transform,camera);
        if(albedoColor.getAlpha() == 0) {
            return false;
        }

        if(albedoTexture != null) {
            //if(!shader.isTextureActive())
            shader.setTextureActive(true);
            albedoTexture.bind(0);
        } else {
            //if(shader.isTextureActive())
            shader.setTextureActive(false);
        }
        shader.setColor(albedoColor);
        return true;
    }


    @Override
    public void unbind() {
    }
}
