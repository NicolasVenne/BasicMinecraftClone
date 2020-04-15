package computergraphics.core;

import computergraphics.graphics.Window;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector2d;
import org.joml.Vector2f;

/**
 * MouseInput
 * Gather mouse input
 */
public class MouseInput {

    private final Vector2d previousPos;

    private final Vector2d currentPos;

    private final Vector2f displVec;

    private boolean inWindow = false;

    private boolean leftButtonPressed = false;

    private boolean rightButtonPressed = false;

    private boolean leftButtonReleased = false;

    public MouseInput() {
        previousPos = new Vector2d(-1, -1);
        currentPos = new Vector2d(0, 0);
        displVec = new Vector2f();
    }



    
    /** 
     * Initialize the mouse input callbacks
     * @param window The window to bind the callback too
     */
    public void init(Window window) {
        glfwSetCursorPosCallback(window.getCurrentWindow(), (windowHandle, xpos, ypos) -> {
            currentPos.x = xpos;
            currentPos.y = ypos;
        });
        glfwSetCursorEnterCallback(window.getCurrentWindow(), (windowHandle, entered) -> {
            inWindow = entered;
        });
        glfwSetMouseButtonCallback(window.getCurrentWindow(), (windowHandle, button, action, mode) -> {
            leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
            rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
            leftButtonReleased = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_RELEASE;
        });
    }

    
    /** 
     * Get the displacement vector of the mouse
     * @return Vector2f
     */
    public Vector2f getDisplVec() {
        return displVec;
    }

    
    /**
     * Get the current possition of the mouse on screen 
     * @return Vector2d
     */
    public Vector2d getCurrentPos() {
        return currentPos;        
    }
    
    /**
     * Calculate the displacement vector on input
     */
    public void input() {
        displVec.x = 0;
        displVec.y = 0;
        if (inWindow) {
            double deltax = currentPos.x - previousPos.x;
            double deltay = currentPos.y - previousPos.y;
            boolean rotateX = deltax != 0;
            boolean rotateY = deltay != 0;
            if (rotateX) {
                displVec.y = (float) deltax;
            }
            if (rotateY) {
                displVec.x = (float) deltay;
            }
        }
        previousPos.x = currentPos.x;
        previousPos.y = currentPos.y;
    }

    
    /** 
     * Check to see if the left button was pressed
     * @return boolean
     */
    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    
    /** 
     * Check to see if the right button was pressed
     * @return boolean
     */
    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }

	
    /** 
     * Check to see of the left button was released
     * @return boolean
     */
    public boolean isLeftButtonReleased() {
		return leftButtonReleased;
	}

}
