package engine.tools;

import engine.camera.Camera;
import engine.render.Transform;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class MatrixTools {
    private static Matrix4f TRANSFORM = new Matrix4f();
    private static Matrix4f MVP = new Matrix4f();
    private static Matrix4f VP = new Matrix4f();

    private static Camera origin;

    public static void setOrigin(Camera origin) {
        MatrixTools.origin = origin;
    }

    public static Matrix4f createTransformMatrix(Transform transform) {
        return createTransformationMatrix(transform.getPosition(), transform.getRotation(), transform.getScale(), transform.getOrigin());
    }

    private static Vector3d position = new Vector3d(0,0,0);

    public static Matrix4f createTransformationMatrix(Vector3d pos, Vector3d rotation, Vector3d scale, Vector3d origin) {
        if(MatrixTools.origin != null)
            position.set(pos).sub(MatrixTools.origin.getPosition());
        return TRANSFORM.identity().translate((float)position.x, (float)position.y, (float)position.z).translate((float)(origin.x * scale.x), (float)(origin.y * scale.y), (float)(origin.z * scale.z)).rotateX((float)rotation.x).rotateY((float)rotation.y).rotateZ((float)rotation.z).scale((float)scale.x, (float)scale.y, (float)scale.z).translate((float)(-origin.x * scale.x),(float)( -origin.y * scale.y),(float)( -origin.z * scale.z));
    }

    public static Matrix4f toMVP(Matrix4f model, Matrix4f view, Matrix4f projection){
        return toMVP(model,VP.identity().mul(projection).mul(view));
    }
    public static Matrix4f toMVP(Matrix4f model, Matrix4f vp){
        return MVP.identity().mul(vp).mul(model);
    }


}
