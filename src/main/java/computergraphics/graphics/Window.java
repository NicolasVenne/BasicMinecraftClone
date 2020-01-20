package computergraphics.graphics;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;


/**
 * Window
 */
public class Window {

    public static final int DEFAULT_WIDTH = 1280;
    public static final int DEFAULT_HEIGHT = 720;

    private final long id;

    private final GLFWKeyCallback keyInputCallback;
    private final GLFWCursorPosCallback cursorPosCallback;



    private boolean vsync;

    private static int width;
    private static int height;

    public Window(int width, int height, CharSequence title, boolean vsync) {
        this.vsync = vsync;

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        Window.width = width;
        Window.height = height;
        id = glfwCreateWindow(width, height, title, NULL, NULL);

        if (id == NULL) {
            glfwTerminate();
            throw new RuntimeException("Cannot create window.");
        }
        GLFWVidMode videoModePrimary = glfwGetVideoMode(glfwGetMonitors().get(0));
        // GLFWVidMode videoMode = glfwGetVideoMode(glfwGetMonitors().get(1));
        GLFWVidMode videoMode = null;

        if(videoMode != null) {
            glfwSetWindowPos(id,
                (width / 2) - videoModePrimary.width(),
                (videoMode.height() - height) / 2
            );
        } else {
            glfwSetWindowPos(id,
                (videoModePrimary.width() - width) / 2,
                (videoModePrimary.height() - height) / 2
            );
        }
       
        

        glfwMakeContextCurrent(id);
        GL.createCapabilities();

        if (vsync) {
            glfwSwapInterval(1);
        }

        keyInputCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int code, int action, int mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                    glfwSetWindowShouldClose(window, true);
                }
            }
        };

        cursorPosCallback = new GLFWCursorPosCallback(){
        
            @Override
            public void invoke(long window, double xpos, double ypos) {
                // TODO Auto-generated method stub
                
            }
        };
        glfwSetCursorPosCallback(id, cursorPosCallback);
        glfwSetKeyCallback(id, keyInputCallback);
        glfwSetInputMode(id, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        glViewport(0,0,width,height);
        



    }

    public Window(CharSequence title) {
        this(Window.DEFAULT_WIDTH, Window.DEFAULT_HEIGHT, title, false);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(id);
    }
    
    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public void update() {
        glfwSwapBuffers(id);
        glfwPollEvents();
    }

    public void dispose() {
        glfwDestroyWindow(id);
        keyInputCallback.free();
    }

    public void setVsync(boolean vsync) {
        this.vsync = vsync;
        if(vsync) {
            glfwSwapInterval(1);
        } else {
            glfwSwapInterval(0);
        }
    }

    public boolean isVsyncOn() {
        return this.vsync;
    }

   
}