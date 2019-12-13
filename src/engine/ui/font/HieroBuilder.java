package engine.ui.font;

import engine.texture.Texture;

import java.util.ArrayList;
import java.util.HashMap;

public class HieroBuilder {
    private Texture fontTexture;
    private int lineHeight, base, scaleW, scaleH, pages, packed;
    private HashMap<Integer,HieroCharacter> characters = new HashMap<>();

    public HieroBuilder setFontTexture(Texture fontTexture) {
        this.fontTexture = fontTexture;
        return this;
    }

    public HieroBuilder setLineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
        return this;
    }

    public HieroBuilder setBase(int base) {
        this.base = base;
        return this;
    }

    public HieroBuilder setScaleW(int scaleW) {
        this.scaleW = scaleW;
        return this;
    }

    public HieroBuilder setScaleH(int scaleH) {
        this.scaleH = scaleH;
        return this;
    }

    public HieroBuilder setPages(int pages) {
        this.pages = pages;
        return this;
    }

    public HieroBuilder setPacked(int packed) {
        this.packed = packed;
        return this;
    }

    public HieroBuilder addCharacter(int id,HieroCharacter character) {
        characters.put(id,character);
        return this;
    }

    public Hiero build() {
        return new Hiero(fontTexture,lineHeight,base,scaleW,scaleH,pages,packed,characters);
    }
}
