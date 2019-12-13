package engine.oldphysics;
/*
import engine.physics.AABB;
import engine.physics.ICollider;
import engine.physics.IPhysicsBody;
import org.joml.Vector3f;

import java.util.List;

public class CollisionExecutor {
    public static final CollisionExecutor BASIC = new CollisionExecutor();
    public boolean isColliding(ICollider col1, ICollider col2){
        if(col1.getType() == ICollider.Type.AABB && col1.getType() == ICollider.Type.AABB){
            return ((AABB)col1).isColliding((AABB)col2);
        }
        return false;
    }
    private Vector3f offset = new Vector3f(0,0,0);
    private float off = 0;

    public float yClipping(ICollider col1,float stepY, List<IPhysicsBody> colliders){
        //offset.set(step);
        off = stepY;
        colliders.forEach((col)->{
            if(col1.getType() == ICollider.Type.AABB && col1.getType() == ICollider.Type.AABB)
                off = yClipping(col1,col.getCollider(),off);
        });
        return off;
    }

    public float xClipping(ICollider col1,float stepY, List<IPhysicsBody> colliders){
        //offset.set(step);
        off = stepY;
        colliders.forEach((col)->{
            if(col1.getType() == ICollider.Type.AABB && col1.getType() == ICollider.Type.AABB)
                off = xClipping(col1,col.getCollider(),off);
        });
        return off;
    }

    public float zClipping(ICollider col1,float stepY, List<IPhysicsBody> colliders){
        //offset.set(step);
        off = stepY;
        colliders.forEach((col)->{
            if(col1.getType() == ICollider.Type.AABB && col1.getType() == ICollider.Type.AABB)
                off = zClipping(col1,col.getCollider(),off);
        });
        return off;
    }

    private float yClipping(ICollider col1, ICollider col2,float yAcc){
        if(col1.getType() == ICollider.Type.AABB && col1.getType() == ICollider.Type.AABB){
            return ((AABB)col1).yClipping((AABB)col2,yAcc);
        }
        return 0;
    }

    private float xClipping(ICollider col1, ICollider col2,float yAcc){
        if(col1.getType() == ICollider.Type.AABB && col1.getType() == ICollider.Type.AABB){
            return ((AABB)col1).xClipping((AABB)col2,yAcc);
        }
        return 0;
    }
    private float zClipping(ICollider col1, ICollider col2,float yAcc){
        if(col1.getType() == ICollider.Type.AABB && col1.getType() == ICollider.Type.AABB){
            return ((AABB)col1).zClipping((AABB)col2,yAcc);
        }
        return 0;
    }

}

 */
