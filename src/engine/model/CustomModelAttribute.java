package engine.model;

public class CustomModelAttribute extends ModelAttribute {
    protected int id;
    public CustomModelAttribute(int shaderId, int size) {
        super(Type.CUSTOM, size);
        this.id = shaderId;
    }
}
