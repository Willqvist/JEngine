package engine.ui;

import engine.Engine;
import engine.window.Window;
import engine.window.WindowInterface;

public class Frame extends Panel{

    private WindowComponent window;

    public Frame(WindowComponent window, Scale scale) {
        super(scale);
        setParent(window);
        setOpacity(0f);
        this.window = window;
        System.out.println("here: " + window);
    }

    @Override
    public void revalidate() {
        super.revalidate();
        window.revalidate();
    }
}
