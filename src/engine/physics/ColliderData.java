package engine.physics;

public class ColliderData {
    private ICollider collider;
    private Velocity velocity;
    public ColliderData set(ICollider collider, Velocity velocity){
        this.collider = collider;
        this.velocity = velocity;
        return this;
    }
}
