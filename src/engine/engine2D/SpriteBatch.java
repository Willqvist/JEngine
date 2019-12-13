package engine.engine2D;

import engine.materials.MaterialBank;
import engine.model.Model;
import engine.model.ModelAttribute;
import engine.model.ModelBuilder;
import engine.render.IRenderable;
import engine.render.Material;
import engine.render.Renderer;
import engine.render.Transform;
import engine.texture.Texture;

public class SpriteBatch implements IRenderable {

    private static final int MAX_DATA = 10000;

    private Model model;
    private ModelBuilder modelBuilder;
    private Material material = MaterialBank.getMaterial("standard");
    private Transform transform;
    private Texture lastTexture;
    private int drawCalls = 0;
    private int textureFlips = 0;

    protected ModelAttribute[] attributes = new ModelAttribute[] {
      new ModelAttribute(ModelAttribute.Type.POSITION,2),
      new ModelAttribute(ModelAttribute.Type.UV,2)
    };

    public SpriteBatch() {
        this.transform = new Transform(0,0,0);
        modelBuilder = new ModelBuilder(MAX_DATA);
    }

    public void transform(int x,int y) {
        this.transform.setPosition(x,y,0);
    }

    private Renderer renderer;
    public void start(Renderer renderer) {
        drawCalls= 0;
        textureFlips = 0;
        this.renderer = renderer;

    }

    public void draw(Texture texture, int x,int y) {
        draw(texture,x,y,texture.getWidth(),texture.getHeight());
    }

    private void flipTexture(Texture texture) {
        if(texture != null && !texture.equals(lastTexture)) {
            textureFlips ++;
            flush();
        }
    }

    public void draw(Texture texture, int x,int y,int width,int height) {
        flipTexture(texture);
        modelBuilder.addFloats(

        );
    }

    public void end() {
        flush();
    }

    private void flush() {
        model = modelBuilder.build(attributes);
        modelBuilder.clear();
        renderer.render(this);
        drawCalls++;
    }

    int getDrawCalls() {
        return drawCalls;
    }

    int getTextureFlips() {
        return textureFlips;
    }

    @Override
    public Model getModel() {
        return model;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public Transform getTransform() {
        return transform;
    }
}
