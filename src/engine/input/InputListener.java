package engine.input;

public interface InputListener {
    boolean isKeyDown(int key);
    boolean isKeyUp(int key);
    boolean isMouseUp(int mouse);
    boolean isMouseDown(int mouse);
}
