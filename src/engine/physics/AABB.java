package engine.physics;
import engine.render.Transform;
import org.joml.FrustumIntersection;
import org.joml.Vector3f;

public class AABB implements ICollider {
    private float width, height, depth;
    private Vector3f position;
    private static float epsilon = 0.01F;
    private Vector3f offset = new Vector3f(0,0,0);
    private Vector3f realPosition = new Vector3f(0,0,0);
    private Runnable pos;
    public AABB(float x, float y, float z, float width, float height, float depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.position = new Vector3f(x,y,z);
    }

    public AABB(Transform transform, float width, float height, float depth) {
        this.position = transform.getPosition();
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }
    private static Vector3f abPos = new Vector3f(0,0,0);

    public void resize(float width,float height,float depth){
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public float yClipping(AABB ab, float yAcc) {
        Vector3f position = getPosition();
        abPos.set(ab.getPosition()).add(0, yAcc, 0);
        if (abPos.x + ab.width > position.x && abPos.x < position.x + width) {
            if (abPos.z + ab.depth > position.z && abPos.z < position.z + depth) {
                if (abPos.y + ab.height > position.y && abPos.y < position.y + height) {
                    float max;
                    if (yAcc > 0.0f && abPos.y + ab.height >= position.y) {
                        max = yAcc - (abPos.y + ab.height - position.y) - epsilon;
                        if (max < yAcc) return Math.max(0,max);
                    }
                    else if (yAcc < 0.0f && abPos.y <= position.y + height) {
                        max = yAcc + (position.y + height) - abPos.y + epsilon;
                        if (max > yAcc) return Math.min(0,max);
                    }
                    return yAcc;
                }
            }
        }
        return yAcc;
    }

    public float xClipping(AABB ab, float xAcc) {
        Vector3f position = getPosition();
        abPos.set(ab.getPosition()).add(xAcc, 0, 0);
        if (abPos.y + ab.height > position.y && abPos.y < position.y + height) {
            if (abPos.z + ab.depth > position.z && abPos.z < position.z + depth) {
                if (abPos.x + ab.width > position.x && abPos.x < position.x + width) {
                    float max;
                    if (xAcc > 0.0f && abPos.x + ab.width >= position.x) {
                        max = xAcc - (abPos.x + ab.width - position.x) - epsilon;
                        if (max < xAcc) return Math.max(0,max);
                    }
                    else if (xAcc < 0.0f && abPos.x < position.x + width) {
                        max = xAcc + position.x + width - abPos.x + epsilon;
                        if (max > xAcc) return Math.min(0,max);
                    }
                    return xAcc;
                }
            }
        }
        return xAcc;
    }

    public float zClipping(AABB ab, float zAcc) {
        Vector3f position = getPosition();
        abPos.set(ab.getPosition()).add(0, 0, zAcc);
        if (abPos.y + ab.height > position.y && abPos.y < position.y + height) {
            if (abPos.x + ab.width > position.x && abPos.x < position.x + width) {
                if (abPos.z + ab.depth > position.z && abPos.z < position.z + depth) {
                    float max;
                    if (zAcc > 0.0f && abPos.z + ab.depth >= position.z) {
                        max = zAcc - (abPos.z + ab.depth - position.z) - epsilon;
                        if (max < zAcc) return Math.max(0,max);
                    }
                    else if (zAcc < 0.0f && abPos.z <= position.z + depth) {
                        max = zAcc + position.z + depth - abPos.z + epsilon;
                        if (max > zAcc) return Math.min(0,max);
                    }
                    return zAcc;
                }
            }
        }
        return zAcc;
    }


    private static Vector3f a = new Vector3f(0,0,0),b = new Vector3f(0,0,0);

    public boolean isColliding(AABB other) {
        Vector3f pos = getPosition();
        Vector3f abPos = other.getPosition();

        return (pos.x <= abPos.x + other.width && pos.x + width >= abPos.x) &&
                (pos.y <= abPos.y + other.height && pos.y + height >= abPos.y) &&
                (pos.z <= abPos.z + other.depth && pos.z + depth >= abPos.z);
    }

    public boolean isInside(AABB other) {
        Vector3f pos = getPosition();
        Vector3f abPos = other.getPosition();

        return (pos.x < abPos.x + other.width && pos.x + width > abPos.x) &&
                (pos.y < abPos.y + other.height && pos.y + height > abPos.y) &&
                (pos.z < abPos.z + other.depth && pos.z + depth > abPos.z);
    }



    @Override
    public Vector3f getPosition() {
        return realPosition.set(position).add(offset);
    }

    public void setOffset(float x,float y,float z) {
        offset.set(x,y,z);
    }

    @Override
    public Type getType() {
        return Type.AABB;
    }

    public boolean testFrustum(FrustumIntersection frustum) {
        Vector3f pos = getPosition();
        return frustum.testAab(pos.x,pos.y,pos.z,pos.x+width,pos.y+height,pos.z+depth);
    }

    public float getDepth() {
        return depth;
    }

    public AABB move(int x, int y, int z) {
        this.position.set(x,y,z);
        return this;
    }
}
