package engine.tools;

public class Range<T extends Comparable<T>> {
    private T min,max;
    public Range(T min, T max){
        this.max = max;
        this.min = min;
    }

    public T min(){
        return min;
    }
    public T max(){
        return max;
    }

    public boolean isBetween(T value){
        return value.compareTo(min) >= 0 && value.compareTo(value) <= 0;
    }
    public static <T extends Comparable<T>> Range from(T min, T max){
        return new Range<T>(min,max);
    }
}
