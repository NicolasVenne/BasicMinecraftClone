package computergraphics.core;
import static org.lwjgl.glfw.GLFW.glfwGetTime;

/**
 * Timer
 * A timer for the game to calculate frames and delta time.
 */
public class Timer {

    private static double lastFrameTime;

    private static float timeCounter;

    private static int fps;

    private static int fpsCounter;

    private static int ups;

    private static int upsCounter;

    public static void initialize() {
        lastFrameTime = now();
    }

    
    /**
     * Get the current time 
     * @return double current time
     */
    public static double now() {
        return glfwGetTime();
    }

    
    /** 
     * Return the delta time
     * @return float delta time
     */
    public static float delta() {
        double time = now();
        float delta = (float) (time - lastFrameTime);
        lastFrameTime = time;
        timeCounter += delta;
        return delta;
    }

    /**
     * Go to th enext frame
     */
    public static void nextFrame() {
        fpsCounter++;
    }

    /**
     * Go to the next fixed update frame
     */
    public static void nextSimulatedUpdate() {
        upsCounter++;
    }

    /**
     * Called by main game loop
     * To update the timer for the frame update
     */
    public static void update() {
        if(timeCounter > 1f) {
            fps = fpsCounter;
            fpsCounter = 0;

            ups = upsCounter;
            upsCounter = 0;

            timeCounter -= 1f;
        }
    }

    
    /** 
     * Get the games FPS
     * @return int
     */
    public static int getFPS() {
        return fps > 0 ? fps : fpsCounter;
    }

    
    /** 
     * Get the games UPS
     * @return int
     */
    public static int getUPS() {
        return ups > 0 ? ups : upsCounter;
    }

    
    /**
     * Get the last frame time 
     * @return double
     */
    public static double getLastFrameTime() {
        return lastFrameTime;
    }


    
}