package engine.ui.font;

public class HieroCharacterBuilder {
    private int id,x,y,width,height,xOffset,yOffset,xAdvance,page,chnl;

    public HieroCharacterBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public HieroCharacterBuilder setX(int x) {
        this.x = x;
        return this;
    }

    public HieroCharacterBuilder setY(int y) {
        this.y = y;
        return this;
    }

    public HieroCharacterBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    public HieroCharacterBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public HieroCharacterBuilder setxOffset(int xOffset) {
        this.xOffset = xOffset;
        return this;
    }

    public HieroCharacterBuilder setyOffset(int yOffset) {
        this.yOffset = yOffset;
        return this;
    }

    public HieroCharacterBuilder setxAdvance(int xAdvance) {
        this.xAdvance = xAdvance;
        return this;
    }

    public HieroCharacterBuilder setPage(int page) {
        this.page = page;
        return this;
    }

    public HieroCharacterBuilder setChnl(int chnl) {
        this.chnl = chnl;
        return this;
    }

    public HieroCharacter build() {
        return new HieroCharacter(id,x,y,width,height,xOffset,yOffset,xAdvance,page,chnl);
    }

}
