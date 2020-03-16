package computergraphics.entities;

import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.*;

import computergraphics.math.Transform;

import org.joml.Matrix4f;
import org.joml.Vector3f;




/**
 * Camera
 */
public class Camera {

    private final float ROTATE_SPEED = 0.5f;

    private Transform transform;
    private double[] mouseX, mouseY;

    public Camera() {
        transform = new Transform(new Vector3f(0,0,0),new Vector3f(0,0,0), new Vector3f(0,0,0));
        mouseX = new double[1];
        mouseY = new double[1];
        mouseX[0] = 0;
        mouseY[0] = 0;
    }

    public Matrix4f getViewMatrix() {
        Matrix4f view = new Matrix4f();
        Transform t = new Transform(transform);
        view.rotate(t.rotation.x, new Vector3f(1f,0,0));
        view.rotate(t.rotation.y, new Vector3f(0,1f,0));
        view.rotate(t.rotation.z, new Vector3f(0,0,1f));
        view.translate(t.position);
        return view;
    }

    public void move(float delta) {
        long window = GLFW.glfwGetCurrentContext();
        

        float oldMouseX = (float)mouseX[0];
        float oldMouseY = (float)mouseY[0];
        glfwGetCursorPos(window, mouseX, mouseY);
        
        float deltaX = (float)mouseX[0] - oldMouseX;
        float deltaY = (float)mouseY[0] - oldMouseY;

        if(transform.rotation.y > 270f - 30f && transform.rotation.y < 270f + 30f) {
            deltaY = 0f;
        }
        transform.rotate(new Vector3f(ROTATE_SPEED * deltaY * delta, ROTATE_SPEED * deltaX * delta, 0f));


        if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
            transform.position.z -= 0.02f * Math.cos(Math.toRadians(transform.rotation.y));
            transform.position.x += 0.02f * Math.sin(Math.toRadians(transform.rotation.y));
            transform.position.y += 0.02f * Math.cos(Math.toRadians(transform.rotation.x + 90f));
            

        }
        if(glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
            transform.position.z -= 0.02f * Math.cos(Math.toRadians(transform.rotation.y + 90f));
            transform.position.x += 0.02f * Math.sin(Math.toRadians(transform.rotation.y + 90f));
        }
        if(glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
            transform.position.z -= 0.02f * Math.cos(Math.toRadians(transform.rotation.y -90f));
            transform.position.x += 0.02f * Math.sin(Math.toRadians(transform.rotation.y -90f));
        }
        if(glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
            transform.position.z += 0.02f * Math.cos(Math.toRadians(transform.rotation.y));
            transform.position.x -= 0.02f * Math.sin(Math.toRadians(transform.rotation.y));
        }
        if(glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
            transform.position.z -= 0.02f * Math.cos(Math.toRadians(transform.rotation.y + 90f));
            transform.position.x += 0.02f * Math.sin(Math.toRadians(transform.rotation.y + 90f));
        }
        if(glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
            transform.position.z -= 0.02f * Math.cos(Math.toRadians(transform.rotation.y -90f));
            transform.position.x += 0.02f * Math.sin(Math.toRadians(transform.rotation.y -90f));
        }
        if(glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
            transform.position.z += 0.02f * Math.cos(Math.toRadians(transform.rotation.y));
            transform.position.x -= 0.02f * Math.sin(Math.toRadians(transform.rotation.y));
        }

    }

    public Transform transform() {
        return this.transform;
    }
}