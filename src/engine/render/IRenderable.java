package engine.render;

import engine.model.Model;

public interface IRenderable extends ITransformable{
    Model getModel();
    Material getMaterial();
}
