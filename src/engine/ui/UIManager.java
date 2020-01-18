package engine.ui;

import engine.camera.Camera2D;
import engine.camera.UICamera;
import engine.render.LWJGLRenderer;
import engine.render.Renderer;
import engine.window.Window;

public class UIManager {

    private static WindowComponent windowComponent;
    private static Renderer renderer = new LWJGLRenderer();
    private static UICamera camera;
    public static void setWindow(Window window) {
        window.onResize((w,h) -> {
            camera.setViewport(w,h);
        });
        camera = new UICamera(window.getWidth(),window.getHeight());
        windowComponent = new WindowComponent(window);
        renderer.setRenderMode(Renderer.RenderMode.QUADS);
        renderer.begin(camera);
    }

    public static WindowComponent getFrame() {
        return windowComponent;
    }

    public static void render() {
        renderer.setRenderMode(Renderer.RenderMode.QUADS);
        renderer.begin(camera);
        windowComponent.render(renderer);
    }

}
