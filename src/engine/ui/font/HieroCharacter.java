package engine.ui.font;

public class HieroCharacter {
    private int id,x,y,width,height,xOffset,yOffset,xAdvance,page,chnl;

    public HieroCharacter(int id, int x, int y, int width, int height, int xOffset, int yOffset, int xAdvance, int page, int chnl) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.xAdvance = xAdvance;
        this.page = page;
        this.chnl = chnl;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getxOffset() {
        return xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public int getxAdvance() {
        return xAdvance;
    }

    public int getPage() {
        return page;
    }

    public int getChnl() {
        return chnl;
    }
}
