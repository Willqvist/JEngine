package engine.camera;

import engine.render.Transform;
import org.joml.FrustumIntersection;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera3D implements Camera {

    private Matrix4f viewMatrix,projectionMatrix,vpm;
    private Transform transform;
    private Transform follow;
    private Vector3f direction = new Vector3f(0,0,0);
    private FrustumIntersection frustum;
    private float fov, zNear, zFar;
    public Camera3D(float fov, int width,int height,float zNear,float zFar){
        projectionMatrix = new Matrix4f().perspective(fov,width/(height*1f),zNear,zFar);
        viewMatrix = new Matrix4f().identity();
        vpm = new Matrix4f().identity();
        transform = new Transform(0,0,0);
        frustum = new FrustumIntersection();
        this.follow = transform;
        this.fov = fov;
        this.zFar = zFar;
        this.zNear = zNear;
    }

    public FrustumIntersection getFrustum(){
        return frustum;
    }

    public void lookAt(Vector3f point){

    }

    public void setViewport(int width,int height) {
        projectionMatrix = new Matrix4f().perspective(fov,width/(height*1f),zNear,zFar);
    }

    public void follow(Transform transform){
        this.follow = transform;
    }
    private Vector3f position = new Vector3f(0,0,0);
    public void update(){
        getPosition();
        Vector3f rotation = transform.getRotation();
        viewMatrix.identity().rotateX(-rotation.x).rotateY(-rotation.y).rotateZ(-rotation.z).translate(-position.x, -position.y, -position.z);
        vpm.set(projectionMatrix).mul(viewMatrix);
        frustum.set(vpm);
    }

    public Vector3f getPosition() {
        return position.set(follow.getPosition()).add(this.transform.getPosition());
    }

    @Override
    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    @Override
    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    @Override
    public Matrix4f getViewProjectionMatrix() {
        return vpm;
    }

    @Override
    public Vector3f getDirection(Direction direction) {
        switch (direction){
            case RIGHT:
                this.direction.set((float)Math.sin(-transform.getRotation().y+Math.PI/2),-(float)Math.cos(-transform.getRotation().y+Math.PI/2),0);
                return this.direction;
            case LEFT:
                this.direction.set((float)Math.sin(-transform.getRotation().y-Math.PI/2),-(float)Math.cos(-transform.getRotation().y-Math.PI/2),0);
                return this.direction;
            case FORWARD:
                this.direction.set((float)Math.sin(-transform.getRotation().y),-(float)Math.cos(-transform.getRotation().y),0);
                return this.direction;
            case BACKWARD:
                this.direction.set(-(float)Math.sin(-transform.getRotation().y),(float)Math.cos(-transform.getRotation().y),0);
                return this.direction;
        }
        return this.direction;
    }

    public Vector3f getForward(){
        Vector3f rotation = transform.getRotation();
        return new Vector3f((float) (-Math.sin(rotation.y) * Math.cos(rotation.x)), (float) Math.sin(rotation.x), (float) (-Math.cos(rotation.y) * Math.cos(rotation.x)));
    }

    public void lerp(float speed,float t,float x,float y,float z){
        this.transform.rotate(0,0,0);
    }

    @Override
    public void move(float x, float y, float z) {
        this.transform.translate(x,y,z);
    }

    @Override
    public void rotate(float x, float y, float z) {
        this.transform.rotate(x,y,z);
    }


    @Override
    public Transform getTransform() {
        return transform;
    }

    public Transform getFollow(){
        return this.follow;
    }
}