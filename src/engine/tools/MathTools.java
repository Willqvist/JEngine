package engine.tools;

import org.joml.Vector3f;

public class MathTools {

    public static final Vector3f DOWN = new Vector3f(0,-1,0);
    public static final Vector3f UP = new Vector3f(0,1,0);
    public static final Vector3f LEFT = new Vector3f(-1,0,0);
    public static final Vector3f RIGHT = new Vector3f(1,0,0);
    public static final Vector3f BACK = new Vector3f(0,0,-1);
    public static final Vector3f FRONT = new Vector3f(0,0,1);

    public static float clamp(float val, float min,float max){
        return Math.min(Math.max(val,min),max);
    }
}
