package computergraphics.entities;

import org.joml.Matrix4f;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;

import computergraphics.core.Chunk;
import computergraphics.core.MouseInput;
import computergraphics.core.TerrainGenerator;
import computergraphics.graphics.Window;
import computergraphics.math.Box2D;
import computergraphics.math.BoxCollider;
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

    private Chunk currentChunk;
    private Box2D[] collisionblock;
    private BoxCollider collider;
    private boolean grounded = false;
    private BoxCollider wallCollider;
    private float dX;
    private float dZ;



    public Player() {
        transform = new Transform(new Vector3f(0,60,0), new Vector3f(0,0,0), new Vector3f(1,1,1));
        blockPosition = new Vector3i();
        chunkPosition = new Vector2i(1,1);
        collisionblock = new Box2D[6];
        collider = new BoxCollider(transform.position, new Vector3f(0.3f, 1.5f, 0.3f));
        viewMatrix = new Matrix4f();
        viewMatrix.identity();
    }

    public void fixedUpdate() {
        Vector2i prevChunk = new Vector2i(chunkPosition);
        int x = transform.position.x > 0 ? Math.round(transform.position.x) : Math.round(transform.position.x) - 15;
        int z = transform.position.z > 0 ? Math.round(transform.position.z) : Math.round(transform.position.z) - 15;
        chunkPosition.x =  x / Chunk.CHUNK_WIDTH;
        chunkPosition.y = z / Chunk.CHUNK_WIDTH;
        if(!prevChunk.equals(chunkPosition)) {
            currentChunk = TerrainGenerator.instance.world.get(chunkPosition);
        }
        x = transform.position.x  < 0 ? Math.round(transform.position.x + ((16 * -chunkPosition.x)))  : Math.round(transform.position.x );
        z = transform.position.z  < 0 ? Math.round(transform.position.z + ((16 * -chunkPosition.y)))  : Math.round(transform.position.z );
        blockPosition.x = (Math.abs(x) % Chunk.CHUNK_WIDTH);
        blockPosition.z = (Math.abs(z) % Chunk.CHUNK_WIDTH);
        // System.out.print(transform.position);
        // System.out.print(chunkPosition);
        // System.out.print(blockPosition);

        // if(blockPosition.z == 0 || blockPosition.z == 15 || blockPosition.x == 0 || blockPosition.x == 15) {
        //     for(Block block : currentChunk.visibleEdgeBlocks) {
        //         if(block.blockChunkCoordinates.x == blockPosition.x && block.blockChunkCoordinates.z == blockPosition.z) {
        //             collisionblock[FaceSide.BOTTOM.index] = block.bounds;
        //             break;
        //         }
        //     }
        // } else {
        //     for (Block block : currentChunk.visibleInnerBlocks) {
        //         if(block.blockChunkCoordinates.x == blockPosition.x && block.blockChunkCoordinates.z == blockPosition.z) {
        //             collisionblock[FaceSide.BOTTOM.index] = block.bounds;
        //             break;
        //         }
        //     }
        // }
        

        // System.out.println(collisionblock[FaceSide.BOTTOM.index]);

    }

    public void update(float delta) {

        transform.position.y -= PLAYER_GRAVITY * delta;
        float nextX = transform.position.x += dX;
        float nextZ = transform.position.z += dZ;
        // if(transform.position.y <= collisionblock[FaceSide.BOTTOM.index].y + collisionblock[FaceSide.BOTTOM.index].width) {
        //     transform.position.y = collisionblock[FaceSide.BOTTOM.index].y + collisionblock[FaceSide.BOTTOM.index].width;
        // }

        
        for(BoxCollider collider : TerrainGenerator.instance.colliders) {

            if(this.collider.isColliding(collider)) {
                if(!(this.collider.origin.y - (this.collider.size.y / 2) > collider.origin.y + collider.size.y)) {
                    if(this.collider.origin.x <= collider.origin.x + collider.size.x &&
                        this.collider.origin.x >= collider.origin.x - collider.size.x) {
                        if(this.collider.origin.z < collider.origin.z) {
                            nextZ = collider.origin.z - collider.size.z - this.collider.size.z;
                        } else {
                            nextZ = collider.origin.z + collider.size.z + this.collider.size.z;
                        }
                    }

                    if(this.collider.origin.z <= collider.origin.z + collider.size.z &&
                        this.collider.origin.z >= collider.origin.z - collider.size.z) {
                        if(this.collider.origin.x < collider.origin.x) {
                            nextX = collider.origin.x - collider.size.x - this.collider.size.x;
                        } else {
                            nextX = collider.origin.x + collider.size.x + this.collider.size.x;
                        }
                    
                    }

                    
                      
                } else {
                    transform.position.y = collider.origin.y + collider.size.y + this.collider.size.y;
                    grounded = true;
                    
                    
                }               
            }
        }

        transform.position.x = nextX;
        transform.position.z = nextZ;
    }

    public void input(final float delta, final MouseInput mouse) {
        dX = 0;
        dZ = 0;
        Vector2f direction = mouse.getDisplVec();
        transform.rotate(direction.x * delta * PLAYER_ROT_SPEED, direction.y * delta * PLAYER_ROT_SPEED, 0f);
        if(transform.rotation.x < 0) transform.rotation.x = 359;
        if(transform.rotation.y < 0) transform.rotation.y = 359;
        transform.rotation.x = transform.rotation.x % 360;
        transform.rotation.y = transform.rotation.y % 360;

        if(transform.rotation.x > 90 && transform.rotation.x < 180) transform.rotation.x = 90;
        if(transform.rotation.x < 270 && transform.rotation.x > 180) transform.rotation.x = 270;
        
        if(Window.isKeyPressed(GLFW_KEY_W)) {
            dX += (float)Math.cos(Math.toRadians(transform.rotation.y - 90)) * PLAYER_SPEED * delta;
            dZ += (float)Math.sin(Math.toRadians(transform.rotation.y - 90)) * PLAYER_SPEED * delta;
        } 

        if(Window.isKeyPressed(GLFW_KEY_D)) {
            dX += (float)Math.cos(Math.toRadians(transform.rotation.y)) * PLAYER_SPEED * delta;
            dZ += (float)Math.sin(Math.toRadians(transform.rotation.y)) * PLAYER_SPEED * delta;
        }
        if(Window.isKeyPressed(GLFW_KEY_A)) {
            dX += (float)Math.cos(Math.toRadians(transform.rotation.y + 180)) * PLAYER_SPEED * delta;
            dZ += (float)Math.sin(Math.toRadians(transform.rotation.y + 180)) * PLAYER_SPEED * delta;
        }

        if(Window.isKeyPressed(GLFW_KEY_S)) {
            dX += (float)Math.cos(Math.toRadians(transform.rotation.y + 90)) * PLAYER_SPEED * PLAYER_SLOW_SPEED * delta;
            dZ += (float)Math.sin(Math.toRadians(transform.rotation.y + 90)) * PLAYER_SPEED * PLAYER_SLOW_SPEED * delta;
        }

        if(Window.isKeyPressed(GLFW_KEY_SPACE) && grounded) {
            transform.position.y += 60f * delta;
            grounded = false;
        }

        if(Window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            dX *= 2;
            dZ *= 2;
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