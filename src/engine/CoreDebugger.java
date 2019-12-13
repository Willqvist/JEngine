package engine;

public class CoreDebugger {
    private int drawCalls = 0;
    private static CoreDebugger debugger;
    public static CoreDebugger init() {
        if(debugger == null)
            debugger = new CoreDebugger();
        return debugger;
    }

    public void onRender(){
        drawCalls ++;
    }
    public int getDrawCalls(){
        int d = drawCalls;drawCalls = 0;
        return d;
    }

}
