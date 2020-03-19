package computergraphics.core;

import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;

import computergraphics.graphics.Renderer;
import computergraphics.graphics.Window;
import computergraphics.states.TriangleDisplayState;

/**
 * Game
 */
public class Game {

    public static final int TARGET_FPS = 75;
    public static final int TARGET_UPS = 30;

    private GLFWErrorCallback errorCallback;

    protected boolean running;

    protected Window window;

    protected Timer timer;

    protected Renderer renderer;

    protected FiniteStateMachine state;

    protected GameStatus status;

    protected MouseInput mouseInput;

    public Game() {
        status = GameStatus.INIT;
        timer = new Timer();
        renderer = new Renderer();
        state = new FiniteStateMachine();
        mouseInput = new MouseInput();
    }

    public void begin() {
        status = initialize();
        startGameLoop();
        dispose();
    }

    public GameStatus initialize() {
        errorCallback = GLFWErrorCallback.createPrint();
        glfwSetErrorCallback(errorCallback);

        if(!glfwInit()) {
            return GameStatus.GLError;
        }

        window = new Window("Minecraft Clone");

        Timer.initialize();

        renderer.initialize();
        
        mouseInput.init(window);
        

        initializeStates();

        return GameStatus.OK;

    }

    private void initializeStates() {
        state.add("game", new TriangleDisplayState());
        state.swap("game");
    }

    public void startGameLoop() {
        float delta;
        float accumulator = 0f;
        float dt = 1f / TARGET_UPS;
        float alpha;

        while(status == GameStatus.OK) {
            if(window.shouldClose()) {
                status = GameStatus.EXIT;
            }

            delta = Timer.delta();
            accumulator += delta;

            input(delta);

            while(accumulator >= dt) {
                fixedUpdate();
                Timer.nextSimulatedUpdate();
                accumulator -= dt;
            }

            alpha = accumulator / dt;

            render(alpha);

            update(delta);

            Timer.nextFrame();

            Timer.update();

            window.update();

            if(!window.isVsyncOn()) {
                sync(TARGET_FPS);
            }

        }
    }

    public void dispose() {
        renderer.dispose();

        state.swap(null);

        window.dispose();

        glfwTerminate();
        errorCallback.free();
    }

    public void input(float delta) {
        mouseInput.input();
        state.input(delta, mouseInput);
    }

    public void update(float delta) {
        state.update(delta);
    }

    public void fixedUpdate() {
        state.fixedUpdate();
    }

    public void render(float alpha) {
        state.render(alpha);
    }

    public void sync(int fps) {
        double lastFrameTIme = Timer.getLastFrameTime();
        double now = Timer.now();
        float targetTime = 1f / fps;

        while(now - lastFrameTIme < targetTime) {
            Thread.yield();

            now = Timer.now();
        }
    }


}