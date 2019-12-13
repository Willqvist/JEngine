package engine.ui;

import engine.window.WindowInterface;

public class WindowComponent extends Component {

    private WindowInterface window;

    public WindowComponent(WindowInterface window) {
        super(Scale.NONE);
        this.window = window;

        setWidth(window.getWidth());
        setHeight(window.getHeight());
        window.onResize((w,h) -> {
            setWidth(w);
            setHeight(h);
            onParentResize();
        });
    }

    public WindowInterface getWindow() {
        return window;
    }


}
