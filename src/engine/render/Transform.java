package engine.render;

import org.joml.Vector3d;

public class Transform<T extends Transform> {
    protected Vector3d  position = new Vector3d(0,0,0), localPosition = new Vector3d(0,0,0),
                        rotation = new Vector3d(0,0,0),
                        scale = new Vector3d(1,1,1),
                        origin = new Vector3d(0,0,0);

    protected T parent;

    public Transform(float x,float y,float z){
        position.set(x,y,z);
    }

    public Vector3d getPosition() {
        if(parent == null)
            return localPosition;
        return position.set(localPosition).add(parent.getPosition());
    }

    public Vector3d getRotation() {
        return rotation;
    }

    public Vector3d getScale() {
        return scale;
    }

    public void setPosition(double x,double y,double z) {
        this.localPosition.set(x,y,z);
    }

    public void setPosition(Vector3d pos) {
        this.localPosition.set(pos);
    }

    public void setRotation(double x,double y,double z) {
        this.rotation.set(x,y,z);
    }

    public void setScale(double x,double y,double z) {
        this.scale.set(x,y,z);
    }

    public void translate(double x,double y,double z){
        this.localPosition.x += x;
        this.localPosition.y += y;
        this.localPosition.z += z;
    }

    public void rotate(double x,double y,double z){
        this.rotation.x += x;
        this.rotation.y += y;
        this.rotation.z += z;
    }

    public Vector3d getOrigin() {
        return origin;
    }

    public void setParent(T transform) {
        parent= transform;
    }



}
