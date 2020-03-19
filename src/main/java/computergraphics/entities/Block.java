package computergraphics.entities;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;

import computergraphics.core.BlockVisibilityChange;
import computergraphics.core.Chunk;
import computergraphics.core.TerrainGenerator;
import computergraphics.graphics.Loader;
import computergraphics.math.Transform;
import computergraphics.models.MaterialModel;
import computergraphics.models.TexturedModel;

/**
 * Block
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

    // public void checkEdgeFaces() {
    //     Chunk c = TerrainGenerator.instance.world.get(currentChunkCoordinates);
    //     int x = blockChunkCoordinates.x;
    //     int y = blockChunkCoordinates.y;
    //     int z = blockChunkCoordinates.z;
    //     boolean isVisible = false;

    //     if(x + 1 >= Chunk.CHUNK_WIDTH && TerrainGenerator.instance.world.containsKey(new Vector2i(currentChunkCoordinates.x + 1, currentChunkCoordinates.y))) {
    //         Chunk nextChunk = TerrainGenerator.instance.world.get(new Vector2i(currentChunkCoordinates.x + 1, currentChunkCoordinates.y));
    //         if(nextChunk.chunk != null && nextChunk.chunk[0][y][z].type == BlockType.AIR) {
    //             faces[FaceSide.RIGHT.index] = 1;
    //             isVisible = true;
    //         } else {
    //             faces[FaceSide.RIGHT.index] = 0;
    //         }
    //     }else if(x + 1 < Chunk.CHUNK_WIDTH && c.chunk[x + 1][y][z].type == BlockType.AIR) {
    //         faces[FaceSide.RIGHT.index] = 1;
    //         isVisible = true;
    //     } else {
    //         faces[FaceSide.RIGHT.index] = 0;
    //     }
    //     if(x - 1 < 0 && TerrainGenerator.instance.world.containsKey(new Vector2i(currentChunkCoordinates.x - 1, currentChunkCoordinates.y))) {

    //         Chunk nextChunk = TerrainGenerator.instance.world.get(new Vector2i(currentChunkCoordinates.x - 1, currentChunkCoordinates.y));
    //         if(nextChunk.chunk != null && nextChunk.chunk[15][y][z].type == BlockType.AIR) {
    //             faces[FaceSide.LEFT.index] = 1;
    //             isVisible = true;
    //         } else {
    //             faces[FaceSide.LEFT.index] = 0;
    //         }
    //     } else if(x - 1 >= 0 && c.chunk[x - 1][y][z].type == BlockType.AIR) {
    //         faces[FaceSide.LEFT.index] = 1;
    //         isVisible = true;
    //     } else {
    //         faces[FaceSide.LEFT.index] = 0;
    //     }
    //     if(y + 1 >= Chunk.CHUNK_HEIGHT || c.chunk[x][y + 1][z].type == BlockType.AIR) {
    //         faces[FaceSide.TOP.index] = 1;
    //         isVisible = true;
    //     } else {
    //         faces[FaceSide.TOP.index] = 0;
    //     }
    //     if(y - 1 >= 0 && c.chunk[x][y - 1][z].type == BlockType.AIR) {
    //         faces[FaceSide.BOTTOM.index] = 1;
    //         isVisible = true;
    //     } else {
    //         faces[FaceSide.BOTTOM.index] = 0;
    //     }
    //     if(z + 1 >= Chunk.CHUNK_WIDTH && TerrainGenerator.instance.world.containsKey(new Vector2i(currentChunkCoordinates.x, currentChunkCoordinates.y + 1))) {
    //         Chunk nextChunk = TerrainGenerator.instance.world.get(new Vector2i(currentChunkCoordinates.x, currentChunkCoordinates.y + 1));
    //         if(nextChunk.chunk != null && nextChunk.chunk[x][y][0].type == BlockType.AIR) {
    //             faces[FaceSide.FRONT.index] = 1;
    //             isVisible = true;
    //         } else {
    //             faces[FaceSide.FRONT.index] = 0;
    //         }
    //     } else if(z + 1 < Chunk.CHUNK_WIDTH && c.chunk[x][y][z + 1].type == BlockType.AIR) {
    //         faces[FaceSide.FRONT.index] = 1;
    //         isVisible = true;
    //     } else {
    //         faces[FaceSide.FRONT.index] = 0;
    //     }
    //     if(z - 1 < 0 && TerrainGenerator.instance.world.containsKey(new Vector2i(currentChunkCoordinates.x, currentChunkCoordinates.y - 1))) {
    //         Chunk nextChunk = TerrainGenerator.instance.world.get(new Vector2i(currentChunkCoordinates.x, currentChunkCoordinates.y - 1));
    //         if(nextChunk.chunk != null && nextChunk.chunk[x][y][15].type == BlockType.AIR) {
    //             faces[FaceSide.BACK.index] = 1;
    //             isVisible = true;
    //         } else {
    //             faces[FaceSide.BACK.index] = 0;
    //         }
    //     } else if(z - 1 >= 0 && c.chunk[x][y][z - 1].type == BlockType.AIR) {
    //         faces[FaceSide.BACK.index] = 1;
    //         isVisible = true;
    //     } else {
    //         faces[FaceSide.BACK.index] = 0;
    //     }

    //     if(wasVisible != isVisible) {
    //         wasVisible = isVisible;
    //         blockChange.OnBlockVisibilityChange(this, isVisible);
    //     }
        

    // }
 



    public Block(BlockType type, Vector3i blockChunkCoordinates, Vector2i currentChunkCoordinates, int[] faces) {
        this.type = type;
        this.faces = faces;
        this.blockChunkCoordinates = blockChunkCoordinates;
        this.currentChunkCoordinates = currentChunkCoordinates;
        this.worldTransform = new Transform(
            new Vector3f(
                currentChunkCoordinates.x * Chunk.CHUNK_WIDTH + blockChunkCoordinates.x,
                blockChunkCoordinates.y,
                currentChunkCoordinates.y * Chunk.CHUNK_WIDTH + blockChunkCoordinates.z
            ),
            new Vector3f(0,0,0),
            new Vector3f(1,1,1)
            );
        // this.blockChange = change;
    }

	// public void checkInnerFaces() {

    //     Chunk c = TerrainGenerator.instance.world.get(currentChunkCoordinates);
    //     int x = blockChunkCoordinates.x;
    //     int y = blockChunkCoordinates.y;
    //     int z = blockChunkCoordinates.z;
    //     boolean isVisible = false;


    //     if(c.chunk[x + 1][y][z].type == BlockType.AIR) {
    //         faces[FaceSide.RIGHT.index] = 1;
    //         isVisible = true;

    //     } else {
    //         faces[FaceSide.RIGHT.index] = 0;
    //     }
    //     if(c.chunk[x - 1][y][z].type == BlockType.AIR) {
    //         faces[FaceSide.LEFT.index] = 1;
    //         isVisible = true;

    //     } else {
    //         faces[FaceSide.LEFT.index] = 0;
    //     }
    //     if(y + 1 >= Chunk.CHUNK_HEIGHT || c.chunk[x][y + 1][z].type == BlockType.AIR) {
    //         faces[FaceSide.TOP.index] = 1;
    //         isVisible = true;

    //     } else {
    //         faces[FaceSide.TOP.index] = 0;
    //     }
    //     if(y - 1 >= 0 && c.chunk[x][y - 1][z].type == BlockType.AIR) {
    //         faces[FaceSide.BOTTOM.index] = 1;
    //         isVisible = true;

    //     } else {
    //         faces[FaceSide.BOTTOM.index] = 0;
    //     }
    //     if(c.chunk[x][y][z + 1].type == BlockType.AIR) {
    //         faces[FaceSide.FRONT.index] = 1;
    //         isVisible = true;

    //     } else {
    //         faces[FaceSide.FRONT.index] = 0;
    //     }
    //     if(c.chunk[x][y][z - 1].type == BlockType.AIR) {
    //         faces[FaceSide.BACK.index] = 1;
    //         isVisible = true;

    //     } else {
    //         faces[FaceSide.BACK.index] = 0;
    //     }

    //     if(wasVisible != isVisible) {
    //         wasVisible = isVisible;
    //         blockChange.OnBlockVisibilityChange(this, isVisible);
    //     }
	// }

	public void setInsideFrustum(boolean insideFrustum) {
        this.isInsideFrustrum = insideFrustum;
	}




    
}
