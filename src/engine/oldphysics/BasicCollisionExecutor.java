package engine.oldphysics;

import engine.physics.IPhysicsBody;
import org.joml.Vector3f;

import java.util.List;
/*
public class BasicCollisionExecutor implements PhysicsExecutor {

    private ColliderData data = new ColliderData();

    @Override
    public void onPhysics(IPhysicsBody body, ICollisionPool pool) {
        List<IPhysicsBody> bodies = pool.getEntities();
        bodies.forEach((b)->{
            if(pool.getExecutor().isColliding(body.getCollider(),b.getCollider())){
                CollisionResponse r1 = body.onCollision(data.set(b.getCollider(),b.getVelocity()));
                CollisionResponse r2 = b.onCollision(data.set(body.getCollider(),body.getVelocity()));
                if(r1 == CollisionResponse.PUSH){
                    if(r2 != CollisionResponse.STATIC) {
                        b.getVelocity().addForce(0.9f, body.getVelocity().getVelocity());
                        body.getVelocity().decreaseForce(0.8f);
                    }else{
                        Vector3f step = body.getVelocity().getVelocity();
                        float off = pool.getExecutor().yClipping(body.getCollider(),body.getVelocity().getVelocity().x,bodies);
                        if(step.y != off){
                            body.getTransform().translate(0,off,0);
                        }

                        off = pool.getExecutor().xClipping(body.getCollider(),body.getVelocity().getVelocity().x,bodies);
                        if(step.x != off){
                            body.getTransform().translate(off,0,0);
                        }

                        off = pool.getExecutor().xClipping(body.getCollider(),body.getVelocity().getVelocity().x,bodies);
                        if(step.z != off){
                            body.getTransform().translate(0,0,off);
                        }

                    }

                }
            }
        });
    }

}

 */
