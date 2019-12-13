package engine;

import engine.camera.CameraHandler;
import engine.physics.IPhysicsEngine;
import engine.physics.PhysicsEngine;
import engine.window.Key;
import engine.window.Mouse;
import engine.window.WindowInterface;

public class Engine {
    public static WindowInterface window;
    public static Key key = new Key();
    public static Mouse mouse = new Mouse();
    public static Graphics graphics = new Graphics();
    public static CameraHandler camera = new CameraHandler();
    public static IPhysicsEngine physics;
}
