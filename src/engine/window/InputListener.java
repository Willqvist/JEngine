package engine.window;

import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

public class InputListener implements GLFWCursorPosCallbackI, GLFWMouseButtonCallbackI, GLFWKeyCallbackI {
    private Mouse mouse;
    private Key key;

    public InputListener(Mouse mouse,Key key){
        this.key = key;
        this.mouse = mouse;
        System.out.println("setup");
    }

    @Override
    public String getSignature() {
        return GLFWCursorPosCallbackI.SIGNATURE;
    }

    @Override
    public void callback(long args) { }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        this.key.onKey(key,scancode,action,mods);
    }

    @Override
    public void invoke(long window, double xpos, double ypos) {
        mouse.setPosition(xpos,ypos);
    }

    @Override
    public void invoke(long window, int button, int action, int mods) {
        mouse.onKeyChange(button,action,mods);
    }
}
