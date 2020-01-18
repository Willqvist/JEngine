package engine.ui;

import engine.render.Renderer;
import engine.window.WindowInterface;

public class WindowComponent extends Component {

    private WindowInterface window;
    private GUIBatch guiBatch = new GUIBatch();
    public WindowComponent(WindowInterface window) {
        super(Scale.NONE);
        this.window = window;
        setOpacity(0);
        setWidth(window.getWidth());
        setHeight(window.getHeight());
        window.onResize((w,h) -> {
            setWidth(w);
            setHeight(h);
            revalidate();
        });
    }

    @Override
    public void revalidate() {
        super.revalidate();
        this.guiBatch.begin();
        this.render(guiBatch);
        this.guiBatch.flush();
    }

    public void render(Renderer renderer) {
        guiBatch.render(renderer);
    }

    public WindowInterface getWindow() {
        return window;
    }


}
