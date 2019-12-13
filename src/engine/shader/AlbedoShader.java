package engine.shader;

import engine.camera.Camera;
import engine.render.Transform;
import engine.tools.MatrixTools;
import engine.tools.RoffColor;
import org.joml.Matrix4f;

public class AlbedoShader extends Shader {

    private int mvp,color;

    public AlbedoShader() {
        super("albedo");
    }

    protected AlbedoShader(String src) {
        super(src);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(Shader.POSITION,"position");
        super.bindAttribute(Shader.UV,"uv");
        super.bindAttribute(Shader.NORMALS,"normal");
        super.bindAttribute(Shader.COLOR,"color");
    }

    @Override
    protected void bindUniforms() {
        mvp = super.getUniform("mvp");
        color = super.getUniform("color");
    }

    public void setColor(RoffColor color){
        super.setUniform(this.color,color.toVec3());
    }

    @Override
    protected void onBind(Transform transform, Camera camera) {
        setUniform(mvp,MatrixTools.toMVP(MatrixTools.createTransformMatrix(transform),camera.getViewProjectionMatrix()));
    }
}
