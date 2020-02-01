package engine.physics;

import org.joml.FrustumIntersection;
import org.joml.Vector3d;
import org.joml.Vector3f;

public interface ICollider {

    Vector3d getPosition();
    Type getType();
    boolean testFrustum(FrustumIntersection frumstum);

    enum Type{
        AABB,CIRCLE
    }
}
