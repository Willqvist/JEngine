package engine.texture;

public class TextureAtlas extends Texture {
    private int tileSize;
    public TextureAtlas(Texture texture, int tileSize){
        this.texture = texture.texture;
        this.tileSize = tileSize;
        texture.clone(this);
    }

    public Texture getTexture(int x,int y) {
        return getSubTexture(x*tileSize,y*tileSize,tileSize,tileSize);
    }

    public int getTileSize() {
        return tileSize;
    }
}