package engine.tools;

public class FloatArray {
    public float[] arr;
    private int size;

    public FloatArray(int cap){
        arr = new float[cap];
        this.size = 0;
    }

    public void flush(){
        size = 0;
    }

    public void put(float val){
        if(size==arr.length){
            grow(arr.length);
        }
        arr[size++] = val;
    }

    public void put(float ...val){
        for(int i = 0; i < val.length; i++)
            put(val[i]);
    }

    public float[] toArray(){
        float[] dat = new float[size];
        System.arraycopy(arr,0,dat,0,size);

        return dat;
    }

    private void grow(int amt){
        float[] dat = arr;
        arr = new float[arr.length+amt];
        System.arraycopy(dat,0,arr,0,dat.length);
    }

    public int length() {
        return size;
    }
}
