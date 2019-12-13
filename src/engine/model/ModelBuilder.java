package engine.model;

import engine.tools.FloatArray;

public class ModelBuilder {
    private Model model;
    private FloatArray data;
    public ModelBuilder(int startSize){
        data = new FloatArray(startSize);
    }

    public static float[] cubeData(int width, int height, int depth) {
        return new float[]{
                0,0,0,
                0,height,0,
                width,height,0,
                width,0,0,

                //FRONT
                0,0,depth,
                0,height,depth,
                width,height,depth,
                width,0,depth,

                //LEFT
                0,0,0,
                0,0,depth,
                0,height,depth,
                0f,height,0,

                //RIGHT
                width,0,0,
                width,0,depth,
                width,height,depth,
                width,height,0,

                //TOP
                0,height,0,
                width,height,0,
                width,height,depth,
                0,height,depth,

                //BOTTOM
                0,0,0,
                width,0,0,
                width,0,depth,
                0,0,depth
        };
    }

    private static Model quad;

    public static Model createQuad() {
        if(quad != null)
            return quad;
        ModelBuilder builder = new ModelBuilder(2*12);
        builder.addFloats(
                 0,1, 0,0,
                        1,1, 1,0,
                        1,0, 1,1,
                        0,0, 0,1
        );
        quad = builder.build(new ModelAttribute(ModelAttribute.Type.POSITION,2),
                             new ModelAttribute(ModelAttribute.Type.UV,2));
        return quad;
    }

    public void addFloat(float data){
        this.data.put(data);
    }

    public void addFloats(float ...data){
        this.data.put(data);
    }

    public Model build(){
        if(data.toArray().length == 0) return null;
        return new Model(data.toArray(), new ModelAttribute(ModelAttribute.Type.POSITION,3));
    }
    public Model build(ModelAttribute ...attributes){
        if(data.length() <= 0) return null;
        return new Model(data.toArray(),attributes);
    }


    public static Model createCube(float width,float height,float depth){
        ModelBuilder builder = new ModelBuilder(3*4*6);
        builder.addFloats(
                //BACK
         0,0,0,
                0,height,0,
                width,height,0,
                width,0,0,

                //FRONT
                0,0,depth,
                0,height,depth,
                width,height,depth,
                width,0,depth,

                //LEFT
                0,0,0,
                0,0,depth,
                0,height,depth,
                0f,height,0,

                //RIGHT
                width,0,0,
                width,0,depth,
                width,height,depth,
                width,height,0,

                //TOP
                0,height,0,
                width,height,0,
                width,height,depth,
                0,height,depth,

                //BOTTOM
                0,0,0,
                width,0,0,
                width,0,depth,
                0,0,depth
        );
        return builder.build();
    }
    public boolean isEmpty() {
        return data.length() == 0;
    }

    public void clear() {
        data.flush();
    }

    public int size() {
        return data.length();
    }
}
