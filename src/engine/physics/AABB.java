package engine.physics;
import engine.render.Transform;
import org.joml.FrustumIntersection;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class AABB implements ICollider {
    private float width, height, depth;
    private Vector3d position;
    private static float epsilon = 0.01F;
    private Vector3d offset = new Vector3d(0,0,0);
    private Vector3d realPosition = new Vector3d(0,0,0);
    public AABB(float x, float y, float z, float width, float height, float depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.position = new Vector3d(x,y,z);
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
    private static Vector3d abPos = new Vector3d(0,0,0);

    public void resize(float width,float height,float depth){
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public double yClipping(AABB ab, double yAcc, boolean minMax) {
        Vector3d position = getPosition();
        abPos.set(ab.getPosition()).add(0, yAcc, 0);
        if (abPos.x + ab.width > position.x && abPos.x < position.x + width) {
            if (abPos.z + ab.depth > position.z && abPos.z < position.z + depth) {
                if (abPos.y + ab.height > position.y && abPos.y < position.y + height) {
                    double max;
                    if (yAcc > 0.0f && abPos.y + ab.height >= position.y) {
                        max = yAcc - (abPos.y + ab.height - position.y) - epsilon;
                        if (max < yAcc) return  minMax ? Math.max(0,max) : max;
                    }
                    else if (yAcc < 0.0f && abPos.y <= position.y + height) {
                        max = yAcc + (position.y + height) - abPos.y + epsilon;
                        if (max > yAcc) return  minMax ? Math.min(0,max) : max;
                    }
                    return yAcc;
                }
            }
        }
        return yAcc;
    }

    public double xClipping(AABB ab, double xAcc,boolean minMax) {
        Vector3d position = getPosition();
        abPos.set(ab.getPosition()).add(xAcc, 0, 0);
        if (abPos.y + ab.height > position.y && abPos.y < position.y + height) {
            if (abPos.z + ab.depth > position.z && abPos.z < position.z + depth) {
                if (abPos.x + ab.width > position.x && abPos.x < position.x + width) {
                    double max;
                    if (xAcc > 0.0f && abPos.x + ab.width >= position.x) {
                        max = xAcc - (abPos.x + ab.width - position.x) - epsilon;
                        if (max < xAcc) return minMax ? Math.max(0,max) : max;
                    }
                    else if (xAcc < 0.0f && abPos.x <= position.x + width) {
                        max = xAcc + position.x + width - abPos.x + epsilon;
                        if (max > xAcc) return minMax ? Math.min(0,max) : max;
                    }
                    return xAcc;
                }
            }
        }
        return xAcc;
    }

    public double zClipping(AABB ab, double zAcc, boolean minMax) {
        Vector3d position = getPosition();
        abPos.set(ab.getPosition()).add(0, 0, zAcc);
        if (abPos.y + ab.height > position.y && abPos.y < position.y + height) {
            if (abPos.x + ab.width > position.x && abPos.x < position.x + width) {
                if (abPos.z + ab.depth > position.z && abPos.z < position.z + depth) {
                    double max;
                    if (zAcc > 0.0f && abPos.z + ab.depth >= position.z) {
                        max = zAcc - (abPos.z + ab.depth - position.z) - epsilon;
                        if (max < zAcc) return  minMax ? Math.max(0,max) : max;
                    }
                    else if (zAcc < 0.0f && abPos.z <= position.z + depth) {
                        max = zAcc + position.z + depth - abPos.z + epsilon;
                        if (max > zAcc) return  minMax ? Math.min(0,max) : max;
                    }
                    return zAcc;
                }
            }
        }
        return zAcc;
    }


    private static Vector3f a = new Vector3f(0,0,0),b = new Vector3f(0,0,0);

    public boolean isColliding(AABB other) {
        Vector3d pos = getPosition();
        Vector3d abPos = other.getPosition();

        return (pos.x <= abPos.x + other.width && pos.x + width >= abPos.x) &&
                (pos.y <= abPos.y + other.height && pos.y + height >= abPos.y) &&
                (pos.z <= abPos.z + other.depth && pos.z + depth >= abPos.z);
    }

    public boolean isInside(AABB other) {
        Vector3d pos = getPosition();
        Vector3d abPos = other.getPosition();

        return (pos.x < abPos.x + other.width && pos.x + width > abPos.x) &&
                (pos.y < abPos.y + other.height && pos.y + height > abPos.y) &&
                (pos.z < abPos.z + other.depth && pos.z + depth > abPos.z);
    }


    @Override
    public Vector3d getPosition() {
        return realPosition.set(position).add(offset);
    }

    public void setOffset(float x,float y,float z) {
        offset.set(x,y,z);
    }

    @Override
    public Type getType() {
        return Type.AABB;
    }

    private Vector3f pos = new Vector3f(0,0,0);

    public boolean testFrustum(FrustumIntersection frustum) {
        pos.set(getPosition());
        return frustum.testAab(pos.x,pos.y,pos.z,pos.x+width,pos.y+height,pos.z+depth);
    }

    public float getDepth() {
        return depth;
    }

    public AABB move(double x, double y, double z) {
        this.position.set(x,y,z);
        return this;
    }
}
