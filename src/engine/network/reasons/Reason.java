package engine.network.reasons;

public interface Reason<T> {
    T type();
    String getMessage();
}
