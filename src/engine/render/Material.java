package engine.render;

import engine.camera.Camera;
import engine.model.Model;

public interface Material {

    boolean bind(Model model, Transform transform, Camera camera);
    void unbind();
}
