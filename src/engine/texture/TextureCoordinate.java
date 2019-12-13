package engine.texture;

public class TextureCoordinate {

    private Texture texture;
    private float offsetX,offsetY,width,height;
    public static TextureCoordinate from(int x,int y,int width,int height,Texture texture){
        return new TextureCoordinate( x, y, width, height, texture);
    }

    public static TextureCoordinate from(int x,int y,TextureAtlas atlas){
        return new TextureCoordinate( x*atlas.getTileSize(), y*atlas.getTileSize(), atlas.getTileSize(), atlas.getTileSize(), atlas);
    }

    private TextureCoordinate(int x,int y,int width,int height,Texture texture){
        offsetX = x/(texture.getFullWidth()*1f);
        offsetY = y/(texture.getFullWidth()*1f);
        this.width = width/(texture.getFullWidth()*1f);
        this.height= height/(texture.getFullWidth()*1f);
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Texture getTexture(){
        return texture;
    }
}
