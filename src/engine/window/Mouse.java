package engine.window;

import engine.Engine;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

public class Mouse{

    private Vector2f mousePos = new Vector2f(0,0);
    private Vector2f oldMousePos = new Vector2f(-1,-1);
    private Vector2f delta = new Vector2f(0,0);

    private boolean[] isMouseDown = new boolean[2];

    protected void setPosition(double xpos, double ypos){
        oldMousePos.set(mousePos);
        mousePos.set((float)xpos,(float)ypos);
    }

    protected void onKeyChange(int button,int action,int mods){
        isMouseDown[button] = action == GLFW.GLFW_PRESS ? true : false;
    }

    public boolean isLeftDown(){
        return isMouseDown[GLFW.GLFW_MOUSE_BUTTON_LEFT];
    }

    public boolean isLeftPressed(){
        boolean ild = isLeftDown();
        if(ild){
            isMouseDown[GLFW.GLFW_MOUSE_BUTTON_LEFT] = false;
        }
        return ild;
    }

    public boolean isRightPressed(){
        boolean ird = isRightDown();
        if(ird){
            isMouseDown[GLFW.GLFW_MOUSE_BUTTON_RIGHT] = false;
        }
        return ird;
    }

    public boolean isRightDown(){
        return isMouseDown[GLFW.GLFW_MOUSE_BUTTON_RIGHT];
    }

    public Vector2f getPosition() {
        return mousePos;
    }

    public Vector2f getSpeed() {
        delta.set(mousePos.x-oldMousePos.x,mousePos.y - oldMousePos.y);
        oldMousePos.x = mousePos.x;
        oldMousePos.y = mousePos.y;
        return delta;
    }

}
