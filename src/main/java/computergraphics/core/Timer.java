package computergraphics.core;
import static org.lwjgl.glfw.GLFW.glfwGetTime;

/**
 * Timer
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

    public static double now() {
        return glfwGetTime();
    }

    public static float delta() {
        double time = now();
        float delta = (float) (time - lastFrameTime);
        lastFrameTime = time;
        timeCounter += delta;
        return delta;
    }

    public static void nextFrame() {
        fpsCounter++;
    }

    public static void nextSimulatedUpdate() {
        upsCounter++;
    }

    public static void update() {
        if(timeCounter > 1f) {
            fps = fpsCounter;
            fpsCounter = 0;

            ups = upsCounter;
            upsCounter = 0;

            timeCounter -= 1f;
        }
    }

    public static int getFPS() {
        return fps > 0 ? fps : fpsCounter;
    }

    public static int getUPS() {
        return ups > 0 ? ups : upsCounter;
    }

    public static double getLastFrameTime() {
        return lastFrameTime;
    }


    
}