package engine.physics;

import java.util.List;

public interface ICollisionPool {
    List<IPhysicsBody> getEntities();

    void executeCollisions();
}
