package engine.render;

import org.joml.Vector3f;

public class Transform<T extends Transform> {
    protected Vector3f  position = new Vector3f(0,0,0), localPosition = new Vector3f(0,0,0),
                        rotation = new Vector3f(0,0,0),
                        scale = new Vector3f(1,1,1),
                        origin = new Vector3f(0,0,0);

    protected T parent;

    public Transform(float x,float y,float z){
        position.set(x,y,z);
    }

    public Vector3f getPosition() {
        if(parent == null)
            return localPosition;
        return position.set(localPosition).add(parent.getPosition());
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setPosition(float x,float y,float z) {
        this.localPosition.set(x,y,z);
    }

    public void setPosition(Vector3f pos) {
        this.position.set(pos);
    }

    public void setRotation(float x,float y,float z) {
        this.rotation.set(x,y,z);
    }

    public void setScale(float x,float y,float z) {
        this.scale.set(x,y,z);
    }

    public void translate(float x,float y,float z){
        this.localPosition.x += x;
        this.localPosition.y += y;
        this.localPosition.z += z;
    }

    public void rotate(float x,float y,float z){
        this.rotation.x += x;
        this.rotation.y += y;
        this.rotation.z += z;
    }

    public Vector3f getOrigin() {
        return origin;
    }

    public void setParent(T transform) {
        parent= transform;
    }



}
