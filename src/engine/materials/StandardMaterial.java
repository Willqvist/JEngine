package engine.materials;

import engine.camera.Camera;
import engine.model.Model;
import engine.render.Material;
import engine.render.Transform;
import engine.shader.AlbedoShader;
import engine.shader.ShaderBank;
import engine.texture.Texture;
import engine.tools.RoffColor;

import java.awt.*;

public class StandardMaterial implements Material {
    private Texture albedoTexture;
    private RoffColor albedoColor = RoffColor.from(Color.WHITE);
    private AlbedoShader shader = ShaderBank.getShader("albedo",AlbedoShader.class);

    public void setAlbedoTexture(Texture texture){
        this.albedoTexture = texture;
    }

    public void setShader(AlbedoShader shader){
        this.shader = shader;
    }
    /*
    TODO: uniform caching... maybe?
     */
    @Override
    public void bind(Model model, Transform transform, Camera camera) {
        if(albedoTexture != null)
            albedoTexture.bind(0);
        shader.bind(transform,camera);
        shader.setColor(albedoColor);
    }

    public void setColor(RoffColor color){
        this.albedoColor.set(color);
    }

    @Override
    public void unbind() {

    }
}
