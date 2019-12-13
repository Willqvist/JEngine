package engine.tools;

import engine.render.Transform;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class MatrixTools {
    private static Matrix4f TRANSFORM = new Matrix4f();
    private static Matrix4f MVP = new Matrix4f();
    private static Matrix4f VP = new Matrix4f();
    public static Matrix4f createTransformMatrix(Transform transform) {
        return createTransformationMatrix(transform.getPosition(), transform.getRotation(), transform.getScale(), transform.getOrigin());
    }

    public static Matrix4f createTransformationMatrix(Vector3f position, Vector3f rotation, Vector3f scale, Vector3f origin) {
        return TRANSFORM.identity().translate(position.x, position.y, position.z).translate(origin.x * scale.x, origin.y * scale.y, origin.z * scale.z).rotateX(rotation.x).rotateY(rotation.y).rotateZ(rotation.z).scale(scale.x, scale.y, scale.z).translate(-origin.x * scale.x, -origin.y * scale.y, -origin.z * scale.z);
    }

    public static Matrix4f toMVP(Matrix4f model, Matrix4f view, Matrix4f projection){
        return toMVP(model,VP.identity().mul(projection).mul(view));
    }
    public static Matrix4f toMVP(Matrix4f model, Matrix4f vp){
        return MVP.identity().mul(vp).mul(model);
    }


}
