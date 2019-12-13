package engine.shader;

import engine.camera.Camera;
import engine.render.Transform;
import engine.tools.MatrixTools;
import engine.tools.RoffColor;

public class UIShader extends Shader{


    private int mvp,color,textureActive;
    private boolean textureActiveState=false;
    public UIShader() {
        super("ui_shader");
    }

    protected UIShader(String src) {
        super(src);
    }

    public void setTextureActive(boolean state) {
        textureActiveState = state;
        setUniform(textureActive,state?1f:0f);
    }

    public boolean isTextureActive() {
        return textureActiveState;
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(Shader.POSITION,"position");
        super.bindAttribute(Shader.UV,"uv");
    }

    @Override
    protected void bindUniforms() {
        mvp = super.getUniform("mvp");
        color = super.getUniform("color");
        textureActive= super.getUniform("textureActive");
    }

    public void setColor(RoffColor color){
        super.setUniform(this.color,color.toVec4());
    }

    @Override
    protected void onBind(Transform transform, Camera camera) {
        setUniform(mvp, MatrixTools.toMVP(MatrixTools.createTransformMatrix(transform),camera.getViewProjectionMatrix()));
    }
}
