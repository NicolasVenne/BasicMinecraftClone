package computergraphics.entities;

import org.joml.Matrix4f;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;

import computergraphics.core.Chunk;
import computergraphics.core.MouseInput;
import computergraphics.graphics.Window;
import computergraphics.math.Transform;

import static org.lwjgl.glfw.GLFW.*;

import javax.swing.text.Position;


/**
 * Player
 */
public class Player {

    public static final float PLAYER_SPEED = 10f;
    public static final float PLAYER_SLOW_SPEED = 0.5f;
    public static final float PLAYER_ROT_SPEED = 5f;
    public static final float PLAYER_GRAVITY = 9.8f;

    public Transform transform;
    public Vector3i blockPosition;
    public Vector2i chunkPosition;
    private Matrix4f viewMatrix;


    public Player() {
        transform = new Transform(new Vector3f(0,20,0), new Vector3f(0,0,0), new Vector3f(1,1,1));
        blockPosition = new Vector3i();
        chunkPosition = new Vector2i();
        viewMatrix = new Matrix4f();
        viewMatrix.identity();
    }

    public void fixedUpdate() {
        int x = transform.position.x > 0 ? Math.round(transform.position.x) : Math.round(transform.position.x - 16);
        int z = transform.position.z > 0 ? Math.round(transform.position.z) : Math.round(transform.position.z - 16);
        chunkPosition.x =  x / Chunk.CHUNK_WIDTH;
        chunkPosition.y = z / Chunk.CHUNK_WIDTH;
        blockPosition.x = (Math.round(transform.position.x) % Chunk.CHUNK_WIDTH);
        blockPosition.z = (Math.round(transform.position.z) % Chunk.CHUNK_WIDTH);
        System.err.println(transform.position + " " + chunkPosition + " " + blockPosition);
        // System.out.println()
    }

    public void update(float delta) {
        // transform.position.y -= PLAYER_GRAVITY * delta;
    }

    public void input(final float delta, final MouseInput mouse) {
        Vector2f direction = mouse.getDisplVec();
        transform.rotate(direction.x * delta * PLAYER_ROT_SPEED, direction.y * delta * PLAYER_ROT_SPEED, 0f);
        if(transform.rotation.x < 0) transform.rotation.x = 359;
        if(transform.rotation.y < 0) transform.rotation.y = 359;
        transform.rotation.x = transform.rotation.x % 360;
        transform.rotation.y = transform.rotation.y % 360;

        if(transform.rotation.x > 90 && transform.rotation.x < 180) transform.rotation.x = 90;
        if(transform.rotation.x < 270 && transform.rotation.x > 180) transform.rotation.x = 270;
        
        if(Window.isKeyPressed(GLFW_KEY_W)) {
            transform.position.x += (float)Math.cos(Math.toRadians(transform.rotation.y - 90)) * PLAYER_SPEED * delta;
            transform.position.z += (float)Math.sin(Math.toRadians(transform.rotation.y - 90)) * PLAYER_SPEED * delta;
        }

        if(Window.isKeyPressed(GLFW_KEY_D)) {
            transform.position.x += (float)Math.cos(Math.toRadians(transform.rotation.y)) * PLAYER_SPEED * delta;
            transform.position.z += (float)Math.sin(Math.toRadians(transform.rotation.y)) * PLAYER_SPEED * delta;
        }
        if(Window.isKeyPressed(GLFW_KEY_A)) {
            transform.position.x += (float)Math.cos(Math.toRadians(transform.rotation.y + 180)) * PLAYER_SPEED * delta;
            transform.position.z += (float)Math.sin(Math.toRadians(transform.rotation.y + 180)) * PLAYER_SPEED * delta;
        }

        if(Window.isKeyPressed(GLFW_KEY_S)) {
            transform.position.x += (float)Math.cos(Math.toRadians(transform.rotation.y + 90)) * PLAYER_SPEED * PLAYER_SLOW_SPEED * delta;
            transform.position.z += (float)Math.sin(Math.toRadians(transform.rotation.y + 90)) * PLAYER_SPEED * PLAYER_SLOW_SPEED * delta;
        }
        

    }

    public Matrix4f getViewMatrix() {
        viewMatrix.identity();
        viewMatrix.rotate((float)Math.toRadians(transform.rotation.x), new Vector3f(1f,0,0));
        viewMatrix.rotate((float)Math.toRadians(transform.rotation.y), new Vector3f(0,1f,0));
        viewMatrix.translate(transform.position.negate());
        return viewMatrix;
    }
    
}