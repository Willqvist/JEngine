package engine.engine2D;

public class SpriteBatchDebugger {

    private SpriteBatch batch;

    public void debug(SpriteBatch batch) {
        this.batch = batch;
    }

    public int getDrawCalls() {
        return batch.getDrawCalls();
    }

    public int getTextureFlips() {
        return getTextureFlips();
    }

}
