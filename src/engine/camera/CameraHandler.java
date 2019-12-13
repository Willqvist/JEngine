package engine.camera;

import java.util.ArrayList;
import java.util.HashMap;

public class CameraHandler {
    private HashMap<String,Camera> cameras = new HashMap<>();

    public void addCamera(String name,Camera camera){
        cameras.put(name,camera);
    }
    public Camera getCamera(String name){
        return cameras.get(name);
    }
}
