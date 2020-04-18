package computergraphics.entities;

import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;

import computergraphics.core.BlockVisibilityChange;
import computergraphics.core.Chunk;
import computergraphics.math.BoxCollider;
import computergraphics.math.Transform;

/**
 * Block
 * Data about a visible block.
 */
public class Block {
    
    public static final float BoundingRadius = 1f;
    public BlockType type;
    public int[] faces;
    public Vector2i currentChunkCoordinates;
    public Vector3i blockChunkCoordinates;
    public Transform worldTransform;
    public Boolean isInsideFrustrum = false;
    public BlockVisibilityChange blockChange;
    public boolean wasVisible = false;
    public BoxCollider collider;
    public boolean selected;

    public Block(BlockType type, Vector3i blockChunkCoordinates, Vector2i currentChunkCoordinates, int[] faces) {
        this.type = type;
        this.faces = faces;
        this.blockChunkCoordinates = blockChunkCoordinates;
        this.currentChunkCoordinates = currentChunkCoordinates;
        this.worldTransform = new Transform(
            new Vector3f(
                currentChunkCoordinates.x * (Chunk.CHUNK_WIDTH) + blockChunkCoordinates.x,
                blockChunkCoordinates.y,
                currentChunkCoordinates.y * (Chunk.CHUNK_WIDTH) + blockChunkCoordinates.z
            ),
            new Vector3f(0,0,0),
            new Vector3f(1,1,1)
            );
        this.collider = new BoxCollider(worldTransform.position, this);
        this.selected = false;
    }

    
    /** 
     * Set the selected value
     * @param b
     */
    public void setSelected(boolean b) {
        this.selected = b;
    }

	
    /** 
     * Set the insideFrustrum value
     * @param insideFrustum
     */
    public void setInsideFrustum(boolean insideFrustum) {
        this.isInsideFrustrum = insideFrustum;
	}
    
}
