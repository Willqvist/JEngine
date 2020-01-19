package engine;

import engine.materials.MaterialBank;
import engine.materials.UIMaterial;
import engine.physics.PhysicsEngine;
import engine.render.Renderer;
import engine.shader.ShaderBank;
import engine.shader.UIShader;
import engine.ui.UIManager;
import engine.window.InputListener;
import engine.window.Window;
import org.lwjgl.opengl.GL11;

import java.util.LinkedList;

public class Core{
    private CoreAttributes attributes;
    private Application application;
    private Window window;
    private CoreDebugger debugger;
    private Renderer renderer;
    private PhysicsEngine physics;

    public static Core init(CoreAttributes attributes, Application application, Renderer renderer){
        return new Core(attributes,application, renderer);
    }
    private Core(CoreAttributes attributes, Application application, Renderer renderer){
        this.window = new Window(attributes.windowTitle,attributes.width,attributes.height,new InputListener(Engine.mouse,Engine.key));
        this.application = application;
        this.attributes = attributes;
        this.renderer = renderer;
        physics = new PhysicsEngine();
        Engine.physics = physics;
        debugger = CoreDebugger.init();
        Engine.window = window;
        ShaderBank.addShader("ui_shader",new UIShader());
        MaterialBank.addMaterial("ui_material",new UIMaterial());
        UIManager.setWindow(window);
        start();
    }

    public void start(){
        this.application.init();
        update();
    }


    private void update(){
        long lastTime = System.nanoTime(), lastTimer = System.currentTimeMillis(), lastMilliSecond = System.currentTimeMillis();
        long now;
        double updateTicks = 1000000000 / attributes.ups;
        double updateFps = 1000000000 / attributes.fps;
        double delta = 0;
        int frames = 0, ticks = 0;
        boolean shouldRender = false;
        double deltaTicks = 0, deltaFps = 0;
        Time.deltaTime = 1f / attributes.ups;
        while (!window.shouldClose()) {
            now = System.nanoTime();
            deltaFps += (now - lastTime) / updateFps;
            deltaTicks += (now - lastTime) / updateTicks;
            lastTime = now;

            while (deltaTicks >= 1) {
                Engine.graphics.deltaTime = (float)deltaTicks;
                ticks++;
                Engine.graphics.tick++;
                Engine.executeEvents();
                window.update();
                physics.update();
                application.update();
                physics.postUpdate();
                deltaTicks--;
            }
            if (deltaFps >= 1) {
                frames++;

                window.preRender();
                application.render(renderer);
                //GL11.glDisable(GL11.GL_DEPTH_TEST);
                UIManager.render();
                window.postRender();
                deltaFps--;
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (System.currentTimeMillis() - lastTimer >= 1000) {
                System.out.println("ticks: " + ticks + " frames: " + frames);
                ticks = 0;
                frames = 0;
                lastTimer += 1000;
            }
        }
        window.close();
        application.end();
    }

}
