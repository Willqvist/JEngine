package engine.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.lwjgl.system.MemoryUtil.NULL;

public class Window implements WindowInterface{
    private int width,height;
    private String title;
    private long window;
    private boolean isMouseLocked=false;
    private boolean closed = false;
    private ArrayList<Runnable> exitListeners = new ArrayList<>();
    private ArrayList<BiConsumer<Integer,Integer>> resizeListeners = new ArrayList<>();
    public Window(String title, int width, int height, InputListener listener){
        GLFWErrorCallback.createPrint(System.err).set();

        this.width = width;
        this.height = height;
        this.title = title;

        if(!GLFW.glfwInit()){
            throw new IllegalStateException("GLFW init");
        }
        GLFW.glfwDefaultWindowHints();

        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE,GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE,GLFW.GLFW_FALSE);

        window = GLFW.glfwCreateWindow(width,height,title,NULL,NULL);
        if(window == NULL){
            throw new RuntimeException("window create");
        }

        GLFW.glfwSetKeyCallback(window,listener::invoke);
        GLFW.glfwSetMouseButtonCallback(window,listener::invoke);
        GLFW.glfwSetCursorPosCallback(window, listener::invoke);


        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(GLFW.GLFW_TRUE);


        GLFW.glfwShowWindow(window);
        GL.createCapabilities();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);
        GLFW.glfwSetWindowSizeCallback(window,( window, w, h)->{
            GL11.glViewport(0,0,w,h);
            resizeListeners.forEach((c)->c.accept(w,h));
        });

    }

    public boolean shouldClose(){
        closed = GLFW.glfwWindowShouldClose(window);
        if(closed) {
            exitListeners.forEach((e)->e.run());
        }
        return closed;
    }

    public void preRender(){
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);
        GL11.glClearColor(0 ,1, 1,1);
    }
    public void postRender(){
        GLFW.glfwSwapBuffers(window);
    }
    public void close(){
        GLFW.glfwDestroyWindow(window);
    }
    public void update(){
        GLFW.glfwPollEvents();
    }


    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int onResize(BiConsumer<Integer,Integer> callback) {
        resizeListeners.add(callback);
        return 0;
    }

    @Override
    public void onExit(Runnable runnable) {
        exitListeners.add(runnable);
    }

    public void lockMouse() {
        isMouseLocked = false;
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
    }
    public void unlockMouse() {
        isMouseLocked = true;
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
        GLFW.glfwSetCursorPos(window, getWidth() / 2, getHeight() / 2);
    }
}
