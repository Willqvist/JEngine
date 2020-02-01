package engine.camera;

import engine.render.ITransformable;
import org.joml.FrustumIntersection;
import org.joml.Matrix4f;
import org.joml.Vector3d;
import org.joml.Vector3f;

public interface Camera extends ITransformable {
    Matrix4f getViewMatrix();
    Matrix4f getProjectionMatrix();
    Matrix4f getViewProjectionMatrix();
    void setViewport(int width,int height);
    Vector3d getDirection(Direction direction);
    Vector3d getPosition();
    FrustumIntersection getFrustum();

    void move(float x,float y,float z);
    void rotate(float x,float y,float z);
    void update();
    enum Direction{
        LEFT,RIGHT,FORWARD,BACKWARD;
    }
}
