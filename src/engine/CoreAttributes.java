package engine;

public class CoreAttributes {

    protected int fps=60,ups=60,width=1080,height=720;
    protected String windowTitle="Title";
    public static CoreAttributes create(){
        return new CoreAttributes();
    }

    public CoreAttributes setFps(int fps) {
        this.fps = fps;
        return this;
    }

    public CoreAttributes setUps(int ups) {
        this.ups = ups;
        return this;
    }

    public CoreAttributes setWidth(int width) {
        this.width = width;
        return this;
    }

    public CoreAttributes setHeight(int height) {
        this.height = height;
        return this;
    }

    public CoreAttributes setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
        return this;
    }
}
