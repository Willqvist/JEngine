package engine;

public interface EventQueue {
    void addEvent(Runnable runnable);
}
