package engine.model;

import engine.shader.Shader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class Model {
    private int vao,vbo;
    private int stride = 0;
    private int vertexCount = 0;
    private int[] attribArrays;
    private static int boundModel = 0;
    public Model(float[] data,ModelAttribute ...attributes){
        int pointer = 0;
        for(int i = 0; i < attributes.length; i++) {
            stride += attributes[i].size;
        }
        stride *= 4;
        attribArrays = new int[attributes.length];
        vao = createVAO();
        vbo = createVBO(data);
        for(int i = 0; i < attributes.length; i++){
            ModelAttribute attribute = attributes[i];
            switch (attributes[i].type){
                case COLOR:
                    point(i,Shader.COLOR, attributes[i].size, pointer);
                    break;
                case POSITION:
                    point(i,Shader.POSITION, attribute.size, pointer);
                    break;
                case UV:
                    point(i,Shader.UV, attribute.size, pointer);
                    break;
                case NORMALS:
                    point(i,Shader.NORMALS, attribute.size, pointer);
                    break;
                case CUSTOM:
                    point(i,((CustomModelAttribute) attribute).id, attribute.size, pointer);
                    break;
            }
            pointer += attribute.size;
        }
        vertexCount = data.length / (stride / 4);
    }
    private static Model outlineCube;
    public static Model createOutlinedCube(float padding) {
        if (outlineCube != null)
            return outlineCube;
        float width = 1+padding, height = 1+padding, depth = 1+padding;
        ModelBuilder builder = new ModelBuilder(12*6);
        builder.addFloats(
                0, 0, 0, width, 0, 0,
                width, 0, 0, width, 0, depth,
                width, 0, depth, 0, 0, depth,
                0, 0, depth, 0, 0, 0,

                0, height, 0, width, height, 0,
                width, height, 0, width, height, depth,
                width, height, depth, 0, height, depth,
                0, height, depth, 0, height, 0,

                0, 0, 0, 0, height, 0,
                width, 0, 0, width, height, 0,
                width, 0, depth, width, height, depth,
                0, 0, depth, 0, height, depth
        );
        outlineCube = builder.build();
        return outlineCube;
    }

    private int createVAO(){
        int id = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(id);
        return id;
    }

    private int createVBO(float[] meshData){
        int ID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, ID);
        FloatBuffer data = ToBuffer(meshData);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
        memFree(data);

        return ID;
    }

    private void memFree(FloatBuffer data) {
        if (data != null)
            MemoryUtil.memFree(data);
    }

    private FloatBuffer ToBuffer(float[] data) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data).flip();
        return buffer;
    }

    public int getVertexCount(){
        return vertexCount;
    }

    private void point(int index,int type,int size,int offset){
        attribArrays[index] = type;
        GL20.glVertexAttribPointer(type, size, GL11.GL_FLOAT, false, stride, offset * 4);
    }

    public void bind(){
        if (boundModel == vao) return;
        boundModel = vao;

        GL30.glBindVertexArray(vao);
        for (int i = 0; i < attribArrays.length; i++)
            GL20.glEnableVertexAttribArray(attribArrays[i]);


    }

    public int getVao(){
        return vao;
    }

    public void dispose() {
        //TODO: create model disposing from graphgicsd card
    }
}
