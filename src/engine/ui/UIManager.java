package engine.ui;

import engine.camera.Camera2D;
import engine.camera.UICamera;
import engine.render.LWJGLRenderer;
import engine.render.Renderer;
import engine.window.Window;

public class UIManager {

    private static Frame frame;
    private static Renderer renderer = new LWJGLRenderer();
    private static UICamera camera;
    public static void setWindow(Window window) {
        window.onResize((w,h) -> {
            camera.setViewport(w,h);
        });
        camera = new UICamera(window.getWidth(),window.getHeight());
        WindowComponent windowComponent = new WindowComponent(window);
        frame = new Frame(windowComponent,Scale.SCALE_TO_FIT);
        renderer.setRenderMode(Renderer.RenderMode.QUADS);
        renderer.begin(camera);
    }

    public static void addComponent(Component component) {
        frame.add(component);
    }

    public static Frame getFrame() {
        return frame;
    }

    public static void render() {
        renderer.setRenderMode(Renderer.RenderMode.QUADS);
        renderer.begin(camera);
        frame.render(renderer);
    }

}
