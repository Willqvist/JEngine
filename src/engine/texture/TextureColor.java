package engine.texture;

import engine.tools.RoffColor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TextureColor extends Texture {
    private RoffColor color = RoffColor.from(Color.MAGENTA);

    public TextureColor() {
        super();
    }
    public TextureColor(BufferedImage image) {
        super(image);
    }

    public TextureColor(String src) {
        super(src);
    }

    public TextureColor(BufferedImage image, int x, int y, int width, int height) {
        super(image, x, y, width, height);
    }

    public TextureColor(BufferedImage image, int width, int height) {
        super(image, width, height);
    }

    public TextureColor(int textureID, int width, int height) {
        super(textureID, width, height);
    }

    public TextureColor(int textureID, int width, int height, int fullWidth, int fullHeight) {
        super(textureID, width, height, fullWidth, fullHeight);
    }

    public TextureColor(int textureID, int x, int y, int width, int height, int fullWidth, int fullHeight) {
        super(textureID, x, y, width, height, fullWidth, fullHeight);
    }

    public void setTexture(Texture texture){
        this.texture = texture.texture;
    }

    @Override
    public void bind() {
        if(texture != -1)
            super.bind();
    }

    public RoffColor getColor() {
        return color;
    }
}
