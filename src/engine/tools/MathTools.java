package engine.tools;

import org.joml.Vector3d;
import org.joml.Vector3f;

public class MathTools {

    public static final Vector3d DOWN = new Vector3d(0,-1,0);
    public static final Vector3d UP = new Vector3d(0,1,0);
    public static final Vector3d LEFT = new Vector3d(-1,0,0);
    public static final Vector3d RIGHT = new Vector3d(1,0,0);
    public static final Vector3d BACK = new Vector3d(0,0,-1);
    public static final Vector3d FRONT = new Vector3d(0,0,1);

    public static double clamp(double val, double min,double max){
        return Math.min(Math.max(val,min),max);
    }
    public static double lerp(double v0, double v1, double t) {
        return v0+t*(v1-v0);
    }

}
