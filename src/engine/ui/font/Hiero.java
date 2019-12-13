package engine.ui.font;

import engine.model.Model;
import engine.model.ModelAttribute;
import engine.model.ModelBuilder;
import engine.texture.Texture;

import java.util.ArrayList;
import java.util.HashMap;

public class Hiero {
    private Texture fontTexture;
    private int lineHeight, base, scaleW, scaleH, pages, packed;
    private HashMap<Integer,HieroCharacter> characters;
    private static ModelAttribute[] attributes = new ModelAttribute[] {
            new ModelAttribute(ModelAttribute.Type.POSITION,2),
            new ModelAttribute(ModelAttribute.Type.UV,2)
    };
    public Hiero(Texture fontTexture, int lineHeight, int base, int scaleW, int scaleH, int pages, int packed, HashMap<Integer,HieroCharacter> characters) {
        this.fontTexture = fontTexture;
        this.lineHeight = lineHeight;
        this.base = base;
        this.scaleW = scaleW;
        this.scaleH = scaleH;
        this.pages = pages;
        this.packed = packed;
        this.characters = characters;
    }

    public Model toModel(String text,float scale,int width) {
        ModelBuilder builder = new ModelBuilder(text.length()*4*2*3);
        char[] chars = text.toCharArray();
        float xPointer = 0;
        float yPointer = 0;
        int higest = 0;
        for(int i = 0; i < chars.length; i++) {
            HieroCharacter character = characters.get((int)chars[i]);
            if(character.getHeight() > higest)
                higest = character.getHeight();
            float xUv = (character.getX()*1f) /fontTexture.getFullWidth();
            float yUv = (character.getY()*1f) /fontTexture.getFullHeight();

            float widthUv = (character.getWidth()*1f) /fontTexture.getFullWidth();
            float heightUv = (character.getHeight()*1f) /fontTexture.getFullHeight();

            boolean wrap = xPointer+character.getWidth() > width;

            if(wrap) {
                yPointer += higest;
                xPointer = 0;
                higest = character.getHeight();
            }

            builder.addFloats(
                    xPointer,yPointer+character.getHeight()*scale+character.getyOffset(),                                  xUv,yUv+heightUv,
                    xPointer+character.getWidth()*scale,yPointer+character.getHeight()*scale+character.getyOffset(),       xUv+widthUv,yUv+heightUv,
                    xPointer+character.getWidth()*scale,yPointer+character.getyOffset(),                                   xUv+widthUv,yUv,
                    xPointer,yPointer+character.getyOffset(),                                                           xUv,yUv
                    );

            xPointer += character.getxAdvance()*scale;

        }
        return builder.build(attributes);
    }

    public Texture getFontTexture() {
        return fontTexture;
    }
}
