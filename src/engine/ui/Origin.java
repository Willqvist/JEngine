package engine.ui;

public enum Origin {
    TOP_LEFT(0,0),
    CENTER_LEFT(0,-0.5f),
    BOTTOM_LEFT(0,-1),
    BOTTOM_CENTER(-0.5f,-1),
    BOTTOM_RIGHT(-1,-1),
    CENTER_RIGHT(-1,-0.5f),
    RIGHT_TOP(-1,0),
    CENTER_TOP(-0.5f,0),
    CENTER(-0.5f,-0.5f),
    CUSTOM(0,0);
    private float x,y;

    Origin(float x, float y) {
        this.x = x;
        this.y = y;
    }

    private Origin setOrigin(float x,float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public static Origin set(float x,float y) {
        return CUSTOM.setOrigin(x,y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
