package engine.shader;

import engine.Disposable;
import engine.camera.Camera;
import engine.render.Transform;
import engine.tools.Const;
import engine.tools.FileTools;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public abstract class Shader implements Disposable {

    public static final int POSITION = 0;
    public static final int UV = 1;
    public static final int COLOR = 2;
    public static final int NORMALS = 3;

    private HashMap<String, Integer> uniformLocations = new HashMap<>();
    private int program;
    private int vertexShader, fragmentShader;
    private static int boundID = -1;
    private boolean updateable = true;
    private Consumer<Shader> onBindCallback;

    protected Shader(String shaderName) {
        this(shaderName,shaderName);
    }
    protected Shader(String vertexShader,String fragmentShader) {

        program = GL20.glCreateProgram();
        String pathVertex = Const.SHADER_SRC_RENDERER() + vertexShader;
        String pathFragment = Const.SHADER_SRC_RENDERER() + fragmentShader;
        String vertexSrc = FileTools.readFile(pathVertex + ".vs");
        String fragmentSrc = FileTools.readFile(pathFragment + ".fs");



        this.vertexShader = bindShader(GL20.GL_VERTEX_SHADER, vertexSrc);
        this.fragmentShader = bindShader(GL20.GL_FRAGMENT_SHADER, fragmentSrc);
        bindAttributes();
        linkProgram();

        bindUniforms();
    }
    public void onBind(Consumer<Shader> cb) {
        this.onBindCallback = cb;
    }

    protected abstract void bindAttributes();

    protected abstract void bindUniforms();

    protected abstract void onBind(Transform transform, Camera camera);

    public void bind(Transform transform, Camera camera) {
        if (boundID != -1 && boundID == program)
            this.onBind(transform, camera);

        if(!bind()) return;
        this.onBind(transform, camera);
    }
    public boolean bind() {

        if (boundID == program) return false;
        boundID = program;

        GL20.glUseProgram(program);

        if (this.onBindCallback != null)
            this.onBindCallback.accept(this);

        return true;
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    protected void bindAttribute(int attribute, String variableName) {
        GL20.glBindAttribLocation(program, attribute, variableName);
    }

    private void linkProgram() {

        GL20.glLinkProgram(program);
        if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == 0)
            System.err.println("Error linking shader: " + GL20.glGetProgramInfoLog(program, 1024));

        if (vertexShader != 0) {
            GL20.glDetachShader(program, vertexShader);
            GL20.glDeleteShader(vertexShader);
        }
        if (fragmentShader != 0) {
            GL20.glDetachShader(program, fragmentShader);
            GL20.glDeleteShader(fragmentShader);
        }

        GL20.glValidateProgram(program);
        if (GL20.glGetProgrami(program, GL20.GL_VALIDATE_STATUS) == 0)
            System.err.println("Error compiling shader: " + GL20.glGetProgramInfoLog(program, 1024));
    }

    private int bindShader(int shader, String src) {
        int shaderID = GL20.glCreateShader(shader);
        if (shaderID == 0)
            System.err.println("Could not load " + (shader == GL20.GL_VERTEX_SHADER ? "vertx" : "fragment") + " shader");
        GL20.glShaderSource(shaderID, src);
        GL20.glCompileShader(shaderID);

        int status = GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS);
        if (status == GL11.GL_FALSE)
            System.err.println("Error compiling shader code: " + GL20.glGetShaderInfoLog(shaderID, 1024));

        GL20.glAttachShader(program, shaderID);
        return shaderID;
    }

    protected int getUniform(String name) {
        if (uniformLocations.containsKey(name)) return uniformLocations.get(name);
        int loc = GL20.glGetUniformLocation(program, name);
        uniformLocations.put(name, loc);
        return loc;
    }

    public void setUniform(int uniform, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            value.get(fb);
            GL20.glUniformMatrix4fv(uniform, false, fb);
        }
    }

    public void setUniform(int uniform, float value) {
        GL20.glUniform1f(uniform, value);
    }

    public void setUniform(int uniform, int value) {
        GL20.glUniform1i(uniform, value);
    }

    public void setUniform(int uniform, Vector3f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(3);
            value.get(fb);
            GL20.glUniform3fv(uniform, fb);
        }
    }

    public void setUniform(int uniform, List<Vector3f> value) {
        int i = 0;
        for(Vector3f v : value){
            setUniform(uniform+i,v);
            i++;
        }
    }

    public void setUniform(int uniform, Vector4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(4);
            value.get(fb);
            GL20.glUniform4fv(uniform, fb);
        }
    }

    public void setUniform(int uniform, Vector2f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(2);
            value.get(fb);
            GL20.glUniform2fv(uniform, fb);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + program;
        result = prime * result + vertexShader;
        result = prime * result + fragmentShader;
        return result;
    }

    @Override
    public void destroy() {
        GL20.glDetachShader(program, vertexShader);
        GL20.glDetachShader(program, fragmentShader);
        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);
        GL20.glDeleteProgram(program);

    }

    public static enum StaticShader {
        TRUE,
        FALSE
    }

    public void setUniform(String string, Vector2f offset) {
        this.setUniform(this.getUniform(string), offset);
    }

}