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
 * Handles the creation of the window and input
 */
public class Window {

    public static final int DEFAULT_WIDTH = 1280;
    public static final int DEFAULT_HEIGHT = 720;

    private final long id;

    private final GLFWKeyCallback keyInputCallback;
    private final GLFWCursorPosCallback cursorPosCallback;

    private static Window instance;

    private boolean vsync;

    private static int width;
    private static int height;

    /**
     * Create a window
     * @param width width screen size
     * @param height height screen size
     * @param title Title of the window
     * @param vsync Should use VSYNC
     */
    public Window(int width, int height, CharSequence title, boolean vsync) {
        this.vsync = vsync;
        instance = this;
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

        glfwSetWindowPos(id,
            (videoModePrimary.width() - width) / 2,
            (videoModePrimary.height() - height) / 2
        );
       
        

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
                
            }
        };
        glfwSetCursorPosCallback(id, cursorPosCallback);
        glfwSetKeyCallback(id, keyInputCallback);
        glfwSetInputMode(id, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        glfwSetCursorPos(id, 0,0);
        glViewport(0,0,width,height);
    }
    
    /**
     * Create a window using default size
     * @param title Title of the window
     */
    public Window(CharSequence title) {
        this(Window.DEFAULT_WIDTH, Window.DEFAULT_HEIGHT, title, false);
    }

    
    /** 
     * Check if a key is being pressed
     * @param key the opengl key id
     * @return boolean true if key is pressed
     */
    public static boolean isKeyPressed(int key) {
        return glfwGetKey(instance.id, key) == GLFW_PRESS;
    }


    
    /** 
     * Return if the window is closing
     * @return boolean true if the window is closing
     */
    public boolean shouldClose() {
        return glfwWindowShouldClose(id);
    }
    
    
    /** 
     * Get the width of the window
     * @return int
     */
    public static int getWidth() {
        return width;
    }

    
    /** 
     * Get the height of the window
     * @return int
     */
    public static int getHeight() {
        return height;
    }

    /**
     * Called by game loop
     * Update the window to the newly rendered scene
     */
    public void update() {
        glfwSwapBuffers(id);
        glfwPollEvents();
    }


    /**
     * Delete window
     */
    public void dispose() {
        glfwDestroyWindow(id);
        keyInputCallback.free();
    }

    
    /** 
     * Set if the window is using vsync
     * @param vsync boolean
     */
    public void setVsync(boolean vsync) {
        this.vsync = vsync;
        if(vsync) {
            glfwSwapInterval(1);
        } else {
            glfwSwapInterval(0);
        }
    }

    
    /** 
     * Check if vsync is currently on
     * @return boolean 
     */
    public boolean isVsyncOn() {
        return this.vsync;
    }


    
    
    /** 
     * Get the window ID
     * @return long
     */
    public long getCurrentWindow() {
        return id;
    }

	
    /** 
     * Check if a key has been released
     * @param key open gl id of key
     * @return boolean true if key is released
     */
    public static boolean isKeyReleased(int key) {
		return glfwGetKey(instance.id, key) == GLFW_RELEASE;
	}

   
}