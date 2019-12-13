package engine.physics;

public interface ICollideable {
    ICollider getCollider();
    CollisionResponse onCollision(ColliderData info);
}
