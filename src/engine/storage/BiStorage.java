package engine.storage;

public class BiStorage<T,S> {
    private T first;
    private S last;

    public BiStorage(T first, S last) {
        this.first = first;
        this.last = last;
    }

    public T getFirst() {
        return first;
    }

    public S getLast() {
        return last;
    }
}
