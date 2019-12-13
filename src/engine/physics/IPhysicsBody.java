package engine.physics;

import engine.render.Transform;

public interface IPhysicsBody extends ICollideable {
    float friction();
    float weight();
    float volume();
    Velocity getVelocity();
    Transform getTransform();
}
