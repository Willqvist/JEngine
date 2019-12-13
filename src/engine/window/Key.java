package engine.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;

public class Key {

    private byte[] keysPressed = new byte[1024];
    private static int[] pressedKeys = new int[1024];
    private static int pressedKeySize = 0;
    public boolean isKeyPressed(int key){
        boolean clicked = keysPressed[key] == 0b1;
        if(clicked){
            keysPressed[key] = 0b0;
        }
        return clicked;
    }

    public boolean isKeyDown(int keyCode){
        return (keysPressed[keyCode] & 0b1) == 1;
    }

    protected void onKey(int key, int scancode, int action, int mods){
        if (action == GLFW.GLFW_RELEASE) {
            keysPressed[key] = 0b0;
            return;
        }
        keysPressed[key] = 0b1;
    }

}
