package computergraphics.entities;

import org.joml.Intersectionf;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;

import computergraphics.core.Chunk;
import computergraphics.core.MouseInput;
import computergraphics.core.TerrainGenerator;
import computergraphics.graphics.Window;
import computergraphics.math.BoxCollider;
import computergraphics.math.Transform;

import static org.lwjgl.glfw.GLFW.*;



/**
 * Player
 * Handles player movement and block selection
 */
public class Player {

    //Constant variables options for player movement
    public static final float PLAYER_SPEED = 1f;
    public static final float PLAYER_AIR_SPEED = 0.1f;
    public static final float PLAYER_SLOW_SPEED = 0.5f;
    public static final float PLAYER_ROT_SPEED = 5f;
    public static final float PLAYER_GRAVITY = 16f;
    public static final float PLAYER_DRAG = 10f;
    public static final float PLAYER_AIR_DRAG = 1f;
    public static final float PLAYER_RUN_MULTIPLIER = 2f;
    public static final float JUMP_FORCE = 6f;


    public Transform transform;
    public Vector3i blockPosition;
    public Vector2i chunkPosition;
    private Matrix4f viewMatrix;
    private Vector3f moveDirection;
    private Vector3f velocity;
    private BoxCollider collider;
    private boolean grounded = false;
    private boolean jump = false;
    private boolean jumping = false;
    private boolean running;
    private Vector3f direction;
    private Vector3f min;
    private Vector3f max;
    private Vector2f nearFar;
    private Block selectedBlock;
    private static float selectDistance = 8;
    private boolean clicked = false;
    private boolean breakBlock = false;
    private boolean reset = false;



    public Player() {
        transform = new Transform(new Vector3f(0,60,0), new Vector3f(0,0,0), new Vector3f(1,1,1));
        blockPosition = new Vector3i();
        chunkPosition = new Vector2i(1,1);
        collider = new BoxCollider(transform.position, new Vector3f(0.3f, 0.99f, 0.3f), this);
        viewMatrix = new Matrix4f();
        viewMatrix.identity();
        direction = new Vector3f();
        min = new Vector3f();
        max = new Vector3f();
        nearFar = new Vector2f();
        moveDirection = new Vector3f();
        velocity = new Vector3f();
    }

    /**
     * Select the block that the player is currently looking at.
     */
    public void selectBlock() {

        Block tempSelectBlock = null;

        float distance = Player.selectDistance;
        direction = new Matrix4f(viewMatrix).positiveZ(direction).negate();

        for(BoxCollider collider : TerrainGenerator.instance.colliders) {
            Block t = (Block)collider.parent;
            t.setSelected(false);
            min.set(collider.origin.x, collider.origin.y, collider.origin.z);
            max.set(collider.origin.x, collider.origin.y, collider.origin.z);
            min.add(-collider.size.x, -collider.size.y, -collider.size.z);
            max.add(collider.size.x, collider.size.y, collider.size.z);
            Vector3f temp = new Vector3f(transform.position);
            temp.y += 0.5f;
            if (Intersectionf.intersectRayAab(temp, direction, min, max, nearFar) && nearFar.x < distance) {
                distance = nearFar.x;
                tempSelectBlock = (Block)collider.parent;

            }
        }

        if(tempSelectBlock != null) {
            selectedBlock = tempSelectBlock;
            selectedBlock.setSelected(true);
        }

    }

    

    
    /** 
     * Called by game loop
     * Update the players possition and check for collisions
     * @param delta
     */
    public void update(float delta) {
        
        //If player pressed space
        if(jump && !jumping) {
            //Add jump velocity
            velocity.y = JUMP_FORCE;
            jumping = true;
            jump = false;

        }

        if(reset) {
            reset = false;
            velocity.y = 0;
            transform.position.y = 60f;

        }

        //Move the player based on the direction from input
        this.move(moveDirection, delta);

        //Calculate the players current chunk coordinates
        int x = transform.position.x > 0 ? Math.round(transform.position.x) : Math.round(transform.position.x) - 15;
        int z = transform.position.z > 0 ? Math.round(transform.position.z) : Math.round(transform.position.z) - 15;
        chunkPosition.x =  x / Chunk.CHUNK_WIDTH;
        chunkPosition.y = z / Chunk.CHUNK_WIDTH;

        //Calculate the players current block coordinate in the chunk
        x = transform.position.x  < 0 ? Math.round(transform.position.x + ((16 * -chunkPosition.x)))  : Math.round(transform.position.x );
        z = transform.position.z  < 0 ? Math.round(transform.position.z + ((16 * -chunkPosition.y)))  : Math.round(transform.position.z );
        blockPosition.x = (Math.abs(x) % Chunk.CHUNK_WIDTH);
        blockPosition.z = (Math.abs(z) % Chunk.CHUNK_WIDTH);

        //Check if player is colliding with a block
        this.checkCollisions();

        //Select the block where the player is looking
        this.selectBlock();

        //If player wants to break block, break the block that was selected previously
        if(this.breakBlock) {
            this.breakBlock = false;
            for(Chunk chunk : TerrainGenerator.instance.visibleChunks) {
                if(selectedBlock != null && chunk.coordinates.equals(selectedBlock.currentChunkCoordinates)) {
                    chunk.breakBlock(selectedBlock.blockChunkCoordinates);
                    selectedBlock = null;
                }
            }
        }


    }

    
    /** 
     * Called by game loop
     * Poll for input that is used by the player
     * @param delta Delta time
     * @param mouse Mouse input
     */
    public void input(final float delta, final MouseInput mouse) {

        Vector2f direction = mouse.getDisplVec();
        transform.rotate(direction.x * delta * PLAYER_ROT_SPEED, direction.y * delta * PLAYER_ROT_SPEED, 0f);
        if(transform.rotation.x < 0) transform.rotation.x = 359;
        if(transform.rotation.y < 0) transform.rotation.y = 359;
        transform.rotation.x = transform.rotation.x % 360;
        transform.rotation.y = transform.rotation.y % 360;

        if(transform.rotation.x > 90 && transform.rotation.x < 180) transform.rotation.x = 90;
        if(transform.rotation.x < 270 && transform.rotation.x > 180) transform.rotation.x = 270;

        if(mouse.isLeftButtonPressed() && !clicked) {
            clicked = true;
            breakBlock = true;
        }

        if(mouse.isLeftButtonReleased()) {
            clicked = false;
        }

        moveDirection.z = 0;
        moveDirection.x = 0;
        
        if(Window.isKeyPressed(GLFW_KEY_W)) {
            moveDirection.z = 1;
        } 

        if(Window.isKeyPressed(GLFW_KEY_D)) {
            moveDirection.x = 1;
        }
        if(Window.isKeyPressed(GLFW_KEY_A)) {
            moveDirection.x = -1;
        }

        if(Window.isKeyPressed(GLFW_KEY_S)) {
            moveDirection.z = -1;
        }

        if(Window.isKeyPressed(GLFW_KEY_R)) {
            if(!reset) {
                reset = true;
            }
        }

        if(Window.isKeyPressed(GLFW_KEY_SPACE) && grounded) {
            jump = true;
            grounded = false;
        }

        if(Window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            running = true;
        }

        if(Window.isKeyReleased(GLFW_KEY_LEFT_SHIFT)) {
            running = false;
        }
        

    }

    
    /** 
     * Get the view matrix of the player
     * @return Matrix4f
     */
    public Matrix4f getViewMatrix() {
        viewMatrix.identity();
        viewMatrix.rotate((float)Math.toRadians(transform.rotation.x), new Vector3f(1f,0,0));
        viewMatrix.rotate((float)Math.toRadians(transform.rotation.y), new Vector3f(0,1f,0));
        viewMatrix.translate(-transform.position.x, -transform.position.y - 0.5f, -transform.position.z);
        return viewMatrix;
    }


    

    
    /** 
     * Move the player given a direction
     * @param direction The normalized direction of the input
     * @param delta Delta time
     */
    public void move(Vector3f direction, float delta) {

        //Get the player speed setting
        float speed = PLAYER_SPEED;
        //If the player is running multiply speed by sprint multiplier
        if(running) {
            speed *= PLAYER_RUN_MULTIPLIER;
        }

        //If the player is grounded, move player based on ground speed and apply drag/friction of ground
        if(grounded) {
            velocity.x += (float)Math.cos(Math.toRadians(transform.rotation.y)) * direction.x * speed;
            velocity.z += (float)Math.sin(Math.toRadians(transform.rotation.y)) * direction.x * speed;
            velocity.x += (float)Math.cos(Math.toRadians(transform.rotation.y - 90)) * direction.z * speed;
            velocity.z += (float)Math.sin(Math.toRadians(transform.rotation.y - 90)) * direction.z * speed;
            
            velocity.x -= velocity.x * PLAYER_DRAG * delta;
            velocity.z -= velocity.z * PLAYER_DRAG * delta;
            
        //Otherwise player is in the air, apply air speed for air control and apply air drag.
        } else {
            velocity.x += (float)Math.cos(Math.toRadians(transform.rotation.y)) * direction.x * PLAYER_AIR_SPEED;
            velocity.z += (float)Math.sin(Math.toRadians(transform.rotation.y)) * direction.x * PLAYER_AIR_SPEED;
            velocity.x += (float)Math.cos(Math.toRadians(transform.rotation.y - 90)) * direction.z * PLAYER_AIR_SPEED;
            velocity.z += (float)Math.sin(Math.toRadians(transform.rotation.y - 90)) * direction.z * PLAYER_AIR_SPEED;
            velocity.x -= velocity.x * PLAYER_AIR_DRAG * delta;
            velocity.z -= velocity.z * PLAYER_AIR_DRAG * delta;
        }

        //Move the players position based on velocity
        transform.position.x += velocity.x * delta;
        transform.position.y += velocity.y * delta;
        transform.position.z += velocity.z * delta;

        //Apply gravity to the player
        velocity.y += -PLAYER_GRAVITY * delta;

    }
    

    /**
     * Check if the player is colliding with any block
     */
    public void checkCollisions() {
        //For all visible colliders around the player
        for(BoxCollider collider : TerrainGenerator.instance.colliders) {
            //If the current collider is colliding with the player
            if(this.collider.isColliding(collider)) {
                //If the player is colliding with a block on its sides
                if(!(this.collider.origin.y - (this.collider.size.y / 2) > collider.origin.y + collider.size.y)) {
                    //Stop movement on x axis
                    if(this.collider.origin.x <= collider.origin.x + collider.size.x &&
                        this.collider.origin.x >= collider.origin.x - collider.size.x) {
                        if(this.collider.origin.z < collider.origin.z) {
                            transform.position.z = collider.origin.z - collider.size.z - this.collider.size.z;
                        } else {
                            transform.position.z = collider.origin.z + collider.size.z + this.collider.size.z;
                        }
                    }
                    //Stop movement on z axis
                    if(this.collider.origin.z <= collider.origin.z + collider.size.z &&
                        this.collider.origin.z >= collider.origin.z - collider.size.z) {
                        if(this.collider.origin.x < collider.origin.x) {
                            transform.position.x = collider.origin.x - collider.size.x - this.collider.size.x;
                        } else {
                            transform.position.x = collider.origin.x + collider.size.x + this.collider.size.x;
                        }
                    
                    }

                    
                //If the collider is underneath the player
                } else {
                    transform.position.y = collider.origin.y + collider.size.y + this.collider.size.y;
                    velocity.y = 0; //Set velocity to 0 cause player is on ground
                    grounded = true; //Set grounded to true aswell
                    //And if player was jumping, they are no longer.
                    if(jumping) {
                        jumping = false;
                    }
                }               
            }
        }
    }
}