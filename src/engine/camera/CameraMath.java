package engine.camera;

import engine.tools.MathTools;
import engine.window.Mouse;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class CameraMath {
    private static Vector3f mouseDelta = new Vector3f(0,0,0);
    private static float rad180 = (float)Math.toRadians(90);
    public static boolean followMouse(Camera camera, Mouse mouse,float scale,float t){
        Vector2f speed = mouse.getSpeed();
        Vector3f camRot = camera.getTransform().getRotation();
        if(speed.x == 0 && speed.y == 0) return false;
        mouseDelta.set(camRot.x - speed.y*scale,camRot.y - speed.x*scale,0);
        Vector3f rot = camera.getTransform().getRotation();
        rot.lerp(mouseDelta,t);
        camera.getTransform().setRotation(MathTools.clamp(rot.x,-rad180,rad180),rot.y,rot.z);
        return true;
    }

    public static Vector3f pickMouse(Camera3D camera, Mouse mouse){
        return null;
    }

}
