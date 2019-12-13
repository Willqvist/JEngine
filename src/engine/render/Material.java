package engine.render;

import engine.camera.Camera;
import engine.model.Model;

public interface Material {

    void bind(Model model, Transform transform, Camera camera);
    void unbind();
}
