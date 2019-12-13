package engine.model;

public class ModelAttribute {

    protected int size;
    protected Type type;
    public ModelAttribute(Type type,int size){
        this.size = size;
        this.type = type;
    }

    public enum Type{
        POSITION,
        COLOR,
        UV,
        NORMALS,
        CUSTOM
    }
}
