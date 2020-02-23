package engine.network;

import engine.input.InputListener;
import org.lwjgl.glfw.GLFW;

public class TestUpdate {

    private InputListener inListener;
    public TestUpdate(InputListener inListener) {
        this.inListener = inListener;
    }

    public void update() {
        if(inListener.isKeyDown(GLFW.GLFW_KEY_A)) {
            System.out.println("[SERVER] key A down!");
        }
    }

}
