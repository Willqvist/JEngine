package engine.physics;

public interface IPhysicsEngine {

    void addCollisionPool(ICollisionPool pool);
    void removeCollisionPool(ICollisionPool pool);

    void addPhysicsBody(IPhysicsBody body);
    void removePhysicsBody(IPhysicsBody body);
}
