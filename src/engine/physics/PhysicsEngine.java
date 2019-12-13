package engine.physics;


import engine.render.Transform;
import engine.tools.MathTools;
import org.joml.Vector3f;

import java.util.ArrayList;

public class PhysicsEngine implements IPhysicsEngine {

    private ArrayList<ICollisionPool> collisionPools = new ArrayList<>();
    private ArrayList<IPhysicsBody> physicsBodies = new ArrayList<>();

    public void addCollisionPool(ICollisionPool pool){
        collisionPools.add(pool);
    }

    public void removeCollisionPool(ICollisionPool pool){
        this.collisionPools.remove(pool);
    }

    @Override
    public void addPhysicsBody(IPhysicsBody body) {
        this.physicsBodies.add(body);
    }

    @Override
    public void removePhysicsBody(IPhysicsBody body) {
        this.physicsBodies.remove(body);
    }

    public void update() {
        for(IPhysicsBody body: physicsBodies){
            body.getVelocity().addForce(Physics.GRAVITY/(body.volume()*1f), MathTools.DOWN,Force.ADD);
            body.getVelocity().decreaseForce(Physics.AIR_DRAG*body.friction());
        }


    }

    public void postUpdate(){
        for(ICollisionPool pool : collisionPools){
            pool.executeCollisions();
        }
        for(IPhysicsBody body: physicsBodies){
            applyVelocity(body.getTransform(),body.getVelocity());
        }
    }

    private void applyVelocity(Transform transform, Velocity velocity){
        Vector3f vel = velocity.getVelocity();
        transform.translate(vel.x,vel.y,vel.z);
    }

}
