package computergraphics.core;

import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;

import computergraphics.graphics.Renderer;
import computergraphics.graphics.Window;
import computergraphics.states.GameState;

/**
 * Game
 * The main game process / loop
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

    /**
     * Start the game
     */
    public void begin() {
        status = initialize();
        startGameLoop();
        dispose();
    }

    
    /** 
     * Try and initialize all the components if good it will return OK
     * @return GameStatus return the status of the initialization
     */
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

    /**
     * Initialize the main game state
     */
    private void initializeStates() {
        state.add("game", new GameState());
        state.swap("game");
    }

    /**
     * The main game loop process
     */
    public void startGameLoop() {
        float delta;
        float accumulator = 0f;
        float dt = 1f / TARGET_UPS;
        float alpha;

        //While the status of the game is OK
        while(status == GameStatus.OK) {
            if(window.shouldClose()) {
                status = GameStatus.EXIT;
            }

            //Calculate delta time
            delta = Timer.delta();
            accumulator += delta;

            
            //Poll of input
            input(delta);

            //Run fix update for simulation
            while(accumulator >= dt) {
                fixedUpdate();
                Timer.nextSimulatedUpdate();
                accumulator -= dt;
            }

            alpha = accumulator / dt;

            //Render the current state
            render(alpha);

            //Lock the delta to not be greater then 0.15 so if game 
            //freeze the player will not jump a massive distance.
            if(delta > 0.15f) {
                delta = 0.15f;
            }

            //Update the current state.
            update(delta);

            //Update the timer for delta time.
            Timer.nextFrame();

            Timer.update();

            //Swap buffers on window
            window.update();

            if(!window.isVsyncOn()) {
                sync(TARGET_FPS);
            }

        }
    }

    /**
     * Clean the game for close.
     */
    public void dispose() {

        state.swap(null);

        window.dispose();

        glfwTerminate();
        errorCallback.free();
    }

    
    /** 
     * Poll the current state for input
     * @param delta delta time of loop
     */
    public void input(float delta) {
        mouseInput.input();
        state.input(delta, mouseInput);
    }

    
    /** 
     * Update the current game state
     * @param delta
     */
    public void update(float delta) {
        state.update(delta);
    }

    /**
     * Fixed udpate for simulation of current state
     */
    public void fixedUpdate() {
        state.fixedUpdate();
    }

    
    /** 
     * Render the current state
     * @param alpha Time for the fixed update to finish.
     */
    public void render(float alpha) {
        state.render(alpha);
    }

    
    /** 
     * VSYNC for variable game
     * @param fps
     */
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