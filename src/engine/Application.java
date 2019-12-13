package engine;

import engine.render.Renderer;
import engine.window.Window;

public interface Application {
    void init();
    void update();
    void render(Renderer renderer);
    void end();
}
