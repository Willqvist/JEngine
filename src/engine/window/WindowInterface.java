package engine.window;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface WindowInterface {
    int getWidth();
    int getHeight();
    void lockMouse();
    void unlockMouse();
    int onResize(BiConsumer<Integer,Integer> callback);

    void onExit(Runnable runnable);
}
