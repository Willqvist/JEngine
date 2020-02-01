package engine.physics;
import engine.tools.MathTools;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class Velocity {
    private Vector3d velocity = new Vector3d();
    private float maxVelocity;
    private static Vector3f dir = new Vector3f(0,0,0);

    public Velocity(IPhysicsBody body){
        maxVelocity = body.weight();
    }

    public void clear(Axis axis) {
        velocity.x = axis == Axis.X ? 0 : velocity.x;
        velocity.y = axis == Axis.Y ? 0 : velocity.y;
        velocity.z = axis == Axis.Z ? 0 : velocity.z;
    }

    public void addForce(float amt, Vector3d direction, Force force){
        switch (force) {
            case ADD:
                this.velocity.add(dir.set(direction).mul(amt));
                break;
            case PUSH:
                if(direction.x != 0)
                    this.velocity.x = direction.x;

                if(direction.y != 0)
                    this.velocity.y = direction.y;

                if(direction.z != 0)
                    this.velocity.z = direction.z;
                break;
        }
        float lengthXZ = (float) Math.sqrt(this.velocity.x*this.velocity.x+this.velocity.z*this.velocity.z);
        if(lengthXZ > maxVelocity) {
            this.velocity.x = (this.velocity.x/lengthXZ)*maxVelocity;
            this.velocity.z = (this.velocity.z/lengthXZ)*maxVelocity;
        }

        //this.velocity.x = MathTools.clamp(this.velocity.x, -maxVelocity, maxVelocity);
        this.velocity.y = MathTools.clamp(this.velocity.y, -maxVelocity, maxVelocity);
        //this.velocity.z = MathTools.clamp(this.velocity.z, -maxVelocity, maxVelocity);


    }

    public Vector3d getVelocity() {
        return velocity;
    }

    public float getMaxVelocity() {
        return maxVelocity;
    }

    public Vector3f getDirection(){
        return dir.set(velocity).normalize();
    }

    private static Vector3f reverse = new Vector3f();
    public void removeForce(float amt, Vector3f dir){
        reverse.set(dir).mul(amt);
        velocity.add(reverse.x,reverse.y,reverse.z);
        if (Math.abs(velocity.x) < 0.0001f) velocity.x = 0;
        if (Math.abs(velocity.y) < 0.0001f) velocity.y = 0;
        if (Math.abs(velocity.z) < 0.0001f) velocity.z = 0;
    }

    public void decreaseForce(float amt){
        reverse.set(velocity).mul(amt);
        velocity.add(-reverse.x,0,-reverse.z);
        if (Math.abs(velocity.x) < 0.0001f) velocity.x = 0;
        if (Math.abs(velocity.y) < 0.0001f) velocity.y = 0;
        if (Math.abs(velocity.z) < 0.0001f) velocity.z = 0;
    }

    public boolean isZero() {
        return (this.velocity.x == 0) &&
                (this.velocity.y == 0) &&
                (this.velocity.z == 0);
    }
}
