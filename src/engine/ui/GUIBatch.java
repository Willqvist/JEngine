package engine.ui;

import engine.camera.Camera;
import engine.materials.UIMaterial;
import engine.model.Model;
import engine.model.ModelAttribute;
import engine.model.ModelBuilder;
import engine.model.ModelInstance;
import engine.render.Renderer;
import engine.texture.Texture;
import engine.texture.TextureCoordinate;
import engine.tools.RoffColor;

import java.util.ArrayList;

public class GUIBatch {
    private ArrayList<ModelInstance> models;
    private UIMaterial material;
    private RoffColor color;
    private Texture currentTexture;
    private ModelBuilder modelBuilder = new ModelBuilder(1024);
    private int hasTextureState = -1;

    private static ModelAttribute[] attributes = new ModelAttribute[] {
      new ModelAttribute(ModelAttribute.Type.POSITION,2),
      new ModelAttribute(ModelAttribute.Type.UV,2),
    };

    private Camera camera;

    public void begin() {
        if(models != null) {
            for(ModelInstance model : models) {
                model.getModel().dispose();
            }
        }
        this.models = new ArrayList<>();
        this.modelBuilder.clear();
        material = new UIMaterial();

    }

    private boolean shouldFlush(Texture texture) {
        if(hasTextureState == -1) {
            if(texture == null ){
                hasTextureState = 1;
            } else {
                hasTextureState = 0;
            }
            return false;
        }

        if(hasTextureState == 0 && !currentTexture.isSameTexture(texture)) {
            if(texture == null) {
                hasTextureState = 1;
            }
            return true;
        }
        if(hasTextureState == 1 && texture != null) {
            hasTextureState = 0;
            return true;
        }

        return false;
    }

    public void renderTexture(int x,int y,int width,int height, Texture texture) {
        renderTexture(x,y,width,height,texture,RoffColor.WHITE);

    }

    public void renderTexture(int x,int y,int width,int height, RoffColor color) {
        renderTexture(x,y,width,height,null,color);
    }

    public void renderTexture(int x,int y,int width,int height,Texture texture, RoffColor color) {
        if(shouldFlush(texture)) {
            flush();
        }

        this.color = color;
        currentTexture = texture;
        System.out.println("color: " + color.getAlpha());
        material.setColor(color);
        if(texture != null) {
            material.setAlbedoTexture(texture);
            TextureCoordinate coordinate = TextureCoordinate.from(0,0,
                    texture.getWidth(),texture.getHeight(),texture);

            modelBuilder.addFloats(
                    x,y+height,coordinate.getOffsetX(),coordinate.getOffsetY(),
                    x+width,y+height,coordinate.getOffsetX()+coordinate.getWidth(),coordinate.getOffsetY(),

                    x+width,y,coordinate.getOffsetX()+coordinate.getWidth(),
                    coordinate.getOffsetY()+coordinate.getHeight(),

                    x,y,coordinate.getOffsetX(),coordinate.getOffsetY()+coordinate.getHeight()
            );
        } else {
            modelBuilder.addFloats(
                    x,y+height,0,0,
                    x+width,y+height,1,0,
                    x+width,y,1,1,
                    x,y,0,1
            );
        }
    }

    public void render(Renderer renderer) {
        if(models != null) {
            for (ModelInstance instance : models) {
                if(instance.getModel() != null) {
                    renderer.render(instance);
                }
            }
        }
    }

    public void flush() {
        Model model = modelBuilder.build(attributes);
        modelBuilder.clear();
        ModelInstance instance = new ModelInstance(model);
        instance.setMaterial(material);
        models.add(instance);
        material = new UIMaterial();
    }
}
