package engine.texture;

import engine.tools.Const;
import engine.tools.FileTools;
import org.w3c.dom.Text;

import java.util.HashMap;

public class TextureLoader {

    //TODO: cache texture, old textures will be removed...
    private static HashMap<String, Texture> textures = new HashMap<>();
    public static Texture load(String src){
        if(textures.containsKey(src))
            return textures.get(src);

        Texture t = new Texture(src);
        textures.put(src,t);
        return t;
    }
    public static TextureAtlas loadAtlas(String src,int tiles){
        if(textures.containsKey(src))
            return (TextureAtlas) textures.get(src);

        Texture t = new Texture(src);
        TextureAtlas atlas = new TextureAtlas(t,t.getFullWidth()/tiles);
        textures.put(src,atlas);
        return atlas;
    }
    public static TextureAtlas getAtlas(String src){
        if(textures.containsKey(src))
            return (TextureAtlas) textures.get(src);
        return null;
    }
    public static Texture get(String src){
        if(textures.containsKey(src))
            return textures.get(src);
        return load(src);
    }
}
