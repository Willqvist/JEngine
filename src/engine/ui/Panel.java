package engine.ui;

import engine.render.Renderer;

import java.util.ArrayList;

public class Panel extends Component{

    private ArrayList<Component> components = new ArrayList<>();

    public Panel(Scale scale) {
        super(scale);
    }

    public void add(Component component) {
        component.setParent(this);
        components.add(component);
        component.onParentComponentResize();
    }

    @Override
    public void render(Renderer renderer) {
        super.render(renderer);
        components.forEach((c)->{
            c.render(renderer);
        });
    }

}
