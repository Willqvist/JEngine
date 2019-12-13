package engine.camera;

import org.joml.Matrix4f;

public class UICamera extends Camera2D {
    public UICamera(int width, int height) {
        super(width, height, 0, 0);
        getTransform().setPosition(0,0,-1);
        update();
    }

    @Override
    protected Matrix4f buildProjectionMatrix(int width, int height, float zNear, float zFar) {
        return new Matrix4f().ortho2D(0,width,height,0);

    }
}
