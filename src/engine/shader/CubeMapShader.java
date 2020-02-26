package engine.shader;

import engine.camera.Camera;
import engine.render.Transform;
import org.joml.Matrix4f;

public class CubeMapShader extends AlbedoShader {
    private Matrix4f viewMat = new Matrix4f();
    public CubeMapShader(String shaderName) {
        super(shaderName);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(POSITION,"position");
    }

    @Override
    protected void onBind(Transform transform, Camera camera) {
        viewMat.set(camera.getViewProjectionMatrix());
        viewMat.m30(0);
        viewMat.m31(0);
        viewMat.m32(0);
        setUniform(viewProj,viewMat);
    }
}
