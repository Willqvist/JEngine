package engine.camera;

import engine.render.Transform;
import org.joml.FrustumIntersection;
import org.joml.Matrix4f;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class Camera2D implements Camera {
    private Matrix4f viewMatrix,projectionMatrix,vpm;
    private Transform transform;
    private Transform follow;
    private Vector3d direction = new Vector3d(0,0,0);
    private FrustumIntersection frustum;
    private float zNear,zFar;
    public Camera2D(int width,int height,float zNear,float zFar){
        projectionMatrix = buildProjectionMatrix(width,height,zNear,zFar);
        viewMatrix = new Matrix4f().identity();
        vpm = new Matrix4f().identity();
        transform = new Transform(0,0,0);
        frustum = new FrustumIntersection();
        this.follow = transform;
        this.zNear = zNear;
        this.zFar = zFar;
        update();
    }

    protected Matrix4f buildProjectionMatrix(int width,int height,float zNear,float zFar) {
        return new Matrix4f().ortho(-width/2,width/2,height/2,-height/2,zNear,zFar);
    }

    public FrustumIntersection getFrustum(){
        return frustum;
    }

    public void lookAt(Vector3f point){

    }

    public void follow(Transform transform){
        this.follow = transform;
    }

    public void update(){
        Vector3d position = follow.getPosition();
        Vector3d rotation = transform.getRotation();
        viewMatrix.identity().rotateX((float)-rotation.x).rotateY((float)-rotation.y).rotateZ((float)-rotation.z).translate((float)-position.x, (float)-position.y,(float)-position.z);
        vpm.set(projectionMatrix).mul(viewMatrix);
        frustum.set(vpm);
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
    public void setViewport(int width, int height) {
        projectionMatrix = buildProjectionMatrix(width,height,zNear,zFar);
        update();
    }

    @Override
    public Vector3d getDirection(Direction direction) {
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

    @Override
    public Vector3d getPosition() {
        return null;
    }

    public Vector3f getForward(){
        Vector3d rotation = transform.getRotation();
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
