package engine;

import engine.camera.CameraHandler;
import engine.physics.IPhysicsEngine;
import engine.physics.PhysicsEngine;
import engine.window.Key;
import engine.window.Mouse;
import engine.window.WindowInterface;

import java.util.LinkedList;

public class Engine {
    public static WindowInterface window;
    public static Key key = new Key();
    public static Mouse mouse = new Mouse();
    public static Graphics graphics = new Graphics();
    public static CameraHandler camera = new CameraHandler();
    public static IPhysicsEngine physics;

    private static LinkedList<Runnable> runnables = new LinkedList<>();

    public static synchronized void invokeLater(Runnable runnable) {
        runnables.add(runnable);
    }

    protected static synchronized void executeEvents() {
        for(Runnable runnable : runnables) {
            runnable.run();
        }
        runnables.clear();
    }

    public static synchronized void addUpdateable(Updateable updateable) {

    }
}
