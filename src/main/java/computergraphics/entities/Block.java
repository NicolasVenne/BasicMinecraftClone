package computergraphics.entities;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;

import computergraphics.core.Chunk;
import computergraphics.core.TerrainGenerator;
import computergraphics.graphics.Loader;
import computergraphics.math.Transform;
import computergraphics.models.TexturedModel;

/**
 * Block
 */
public class Block {
    
    private static Vector3f[] verticies = {
                
        // //V0
        // new Vector3f(-0.5f,  0.5f,  0.5f),
        // // V1
        // new Vector3f(-0.5f, -0.5f,  0.5f),
        // // V2
        // new Vector3f(0.5f, -0.5f,  0.5f),
        // // V3
        // new Vector3f(0.5f,  0.5f,  0.5f),
        // // V4
        // new Vector3f(-0.5f,  0.5f, -0.5f),
        // // V5
        // new Vector3f(0.5f,  0.5f, -0.5f),
        // // V6
        // new Vector3f(-0.5f, -0.5f, -0.5f),
        // // V7
        // new Vector3f(0.5f, -0.5f, -0.5f),

        //Front  0, 1, 3, 3, 1, 2, -> 0, 1, 3, 3, 1, 2,
        new Vector3f(-0.5f,  0.5f,  0.5f), //V0  -> 0
        new Vector3f(-0.5f, -0.5f,  0.5f), //V1  -> 1
        new Vector3f(0.5f, -0.5f,  0.5f),  //V2  -> 2
        new Vector3f(0.5f,  0.5f,  0.5f),  //V3  -> 3

        //Top 4, 0, 5, 5, 0, 3, -> 4, 5, 6, 6, 5, 7
        new Vector3f(-0.5f,  0.5f, -0.5f), //V4
        new Vector3f(-0.5f,  0.5f,  0.5f), //V5
        new Vector3f(0.5f,  0.5f, -0.5f),  //V6
        new Vector3f(0.5f,  0.5f,  0.5f),  //V7

        //Right 3, 2, 7, 5, 3, 7, -> 8, 9, 10, 11, 8, 10
        new Vector3f(0.5f,  0.5f,  0.5f),  //V8
        new Vector3f(0.5f, -0.5f,  0.5f),  //V9
        new Vector3f(0.5f, -0.5f, -0.5f),  //V10
        new Vector3f(0.5f,  0.5f, -0.5f),  //V11

        //Left 6, 1, 0, 6, 0, 4, -> 12, 13, 14, 12, 14, 15
        new Vector3f(-0.5f, -0.5f, -0.5f), //V12
        new Vector3f(-0.5f, -0.5f,  0.5f), //V13
        new Vector3f(-0.5f,  0.5f,  0.5f), //V14
        new Vector3f(-0.5f,  0.5f, -0.5f), //v15

        //Bottom 2, 1, 6, 2, 6, 7, -> 16, 17, 18, 16, 18, 19
        new Vector3f(0.5f, -0.5f,  0.5f),  //V16
        new Vector3f(-0.5f, -0.5f,  0.5f), //V17
        new Vector3f(-0.5f, -0.5f, -0.5f), //V18
        new Vector3f(0.5f, -0.5f, -0.5f),  //V19

        //Back 7, 6, 4, 7, 4, 5, -> 20, 21, 22, 20, 22, 23
        new Vector3f(0.5f, -0.5f, -0.5f),  //V20
        new Vector3f(-0.5f, -0.5f, -0.5f), //V21
        new Vector3f(-0.5f,  0.5f, -0.5f), //V22
        new Vector3f(0.5f,  0.5f, -0.5f),  //V23




         
    };

    private static Vector2f[] uv = {
        //Front
        
        new Vector2f(0.0f, 0.0f),
        new Vector2f(0.0f, 0.5f),
        new Vector2f(0.5f, 0.5f),
        new Vector2f(0.5f, 0.0f),

        

        //Top
        new Vector2f(0.0f, 0.5f),
        new Vector2f(0.5f, 0.5f),
        new Vector2f(0.0f, 1.0f),
        new Vector2f(0.5f, 1.0f),

        //Right
        new Vector2f(0.0f, 0.0f),
        new Vector2f(0.0f, 0.5f),
        new Vector2f(0.5f, 0.5f),
        new Vector2f(0.5f, 0.0f),

        //Left
        new Vector2f(0.0f, 0.5f),
        new Vector2f(0.5f, 0.5f),
        new Vector2f(0.5f, 0.0f),
        new Vector2f(0.0f, 0.0f),

        //Bottom
        new Vector2f(0.5f, 0f),
        new Vector2f(0.5f, 0.5f),
        new Vector2f(1f, 0.5f),
        new Vector2f(1f, 0f),

        new Vector2f(0.0f, 0.5f),
        new Vector2f(0.5f, 0.5f),
        new Vector2f(0.5f, 0.0f),
        new Vector2f(0.0f, 0.0f),
        
        


    };

    // private static int[] indicies = {
    //     //Front
    //     0, 1, 3, 3, 1, 2,
    //     //Top
    //     4, 5, 6, 6, 5, 7,
    //     //Right
    //     8, 9, 10, 11, 8, 10,
    //     //Left
    //     12, 13, 14, 12, 14, 15,
    //     //Bottom
    //     16, 17, 18, 16, 18, 19,
    //     //Back
    //     20, 21, 22, 20, 22, 23
    // };

    public static int[][] faceMapIndices = {
        { 0, 1, 3, 3, 1, 2,}, //front
        { 4, 5, 6, 6, 5, 7,}, //Top
        { 8, 9, 10, 11, 8, 10,}, //Right
        { 12, 13, 14, 12, 14, 15,}, //Left
        { 16, 17, 18, 16, 18, 19,}, //Bottom
        { 20, 21, 22, 20, 22, 23}, //Back
    };
    
    public enum Faces  {
        FRONT(0),
        TOP(1),
        RIGHT(2),
        LEFT(3),
        BOTTOM(4),
        BACK(5);

        public final int index;
        
        private Faces(int index) {
            this.index = index;
        }
    }

    public BlockType type;
    public int[] faces = {1,1,1,1,1,1};

    public Vector2i currentChunkCoordinates;
    public Vector3i blockChunkCoordinates;
    public Transform worldTransform;

    public void checkEdgeFaces() {
        Chunk c = TerrainGenerator.instance.world.get(currentChunkCoordinates);
        int x = blockChunkCoordinates.x;
        int y = blockChunkCoordinates.y;
        int z = blockChunkCoordinates.z;

        if(x + 1 >= Chunk.CHUNK_WIDTH && TerrainGenerator.instance.world.containsKey(new Vector2i(currentChunkCoordinates.x + 1, currentChunkCoordinates.y))) {
            Chunk nextChunk = TerrainGenerator.instance.world.get(new Vector2i(currentChunkCoordinates.x + 1, currentChunkCoordinates.y));
            if(nextChunk.chunk != null && nextChunk.chunk[0][y][z].type == BlockType.AIR) {
                faces[Faces.RIGHT.index] = 1;
            } else {
                faces[Faces.RIGHT.index] = 0;
            }
        }else if(x + 1 < Chunk.CHUNK_WIDTH && c.chunk[x + 1][y][z].type == BlockType.AIR) {
            faces[Faces.RIGHT.index] = 1;
        } else {
            faces[Faces.RIGHT.index] = 0;
        }
        if(x - 1 < 0 && TerrainGenerator.instance.world.containsKey(new Vector2i(currentChunkCoordinates.x - 1, currentChunkCoordinates.y))) {

            Chunk nextChunk = TerrainGenerator.instance.world.get(new Vector2i(currentChunkCoordinates.x - 1, currentChunkCoordinates.y));
            if(nextChunk.chunk != null && nextChunk.chunk[15][y][z].type == BlockType.AIR) {
                faces[Faces.LEFT.index] = 1;
            } else {
                faces[Faces.LEFT.index] = 0;
            }
        } else if(x - 1 >= 0 && c.chunk[x - 1][y][z].type == BlockType.AIR) {
            faces[Faces.LEFT.index] = 1;
        } else {
            faces[Faces.LEFT.index] = 0;
        }
        if(y + 1 >= Chunk.CHUNK_HEIGHT || c.chunk[x][y + 1][z].type == BlockType.AIR) {
            faces[Faces.TOP.index] = 1;
        } else {
            faces[Faces.TOP.index] = 0;
        }
        if(y - 1 >= 0 && c.chunk[x][y - 1][z].type == BlockType.AIR) {
            faces[Faces.BOTTOM.index] = 1;
        } else {
            faces[Faces.BOTTOM.index] = 0;
        }
        if(z + 1 >= Chunk.CHUNK_WIDTH && TerrainGenerator.instance.world.containsKey(new Vector2i(currentChunkCoordinates.x, currentChunkCoordinates.y + 1))) {
            Chunk nextChunk = TerrainGenerator.instance.world.get(new Vector2i(currentChunkCoordinates.x, currentChunkCoordinates.y + 1));
            if(nextChunk.chunk != null && nextChunk.chunk[x][y][0].type == BlockType.AIR) {
                faces[Faces.FRONT.index] = 1;
            } else {
                faces[Faces.FRONT.index] = 0;
            }
        } else if(z + 1 < Chunk.CHUNK_WIDTH && c.chunk[x][y][z + 1].type == BlockType.AIR) {
            faces[Faces.FRONT.index] = 1;
        } else {
            faces[Faces.FRONT.index] = 0;
        }
        if(z - 1 < 0 && TerrainGenerator.instance.world.containsKey(new Vector2i(currentChunkCoordinates.x, currentChunkCoordinates.y - 1))) {
            Chunk nextChunk = TerrainGenerator.instance.world.get(new Vector2i(currentChunkCoordinates.x, currentChunkCoordinates.y - 1));
            if(nextChunk.chunk != null && nextChunk.chunk[x][y][15].type == BlockType.AIR) {
                faces[Faces.BACK.index] = 1;
            } else {
                faces[Faces.BACK.index] = 0;
            }
        } else if(z - 1 >= 0 && c.chunk[x][y][z - 1].type == BlockType.AIR) {
            faces[Faces.BACK.index] = 1;
        } else {
            faces[Faces.BACK.index] = 0;
        }
        

    }
 


    public static TexturedModel blockModel = Loader.createTexturedBlock(verticies, uv, faceMapIndices[0].length);

    public Block(BlockType type, Vector3i blockChunkCoordinates, Vector2i currentChunkCoordinates) {
        this.type = type;
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
    }

	public void checkInnerFaces() {

        Chunk c = TerrainGenerator.instance.world.get(currentChunkCoordinates);
        int x = blockChunkCoordinates.x;
        int y = blockChunkCoordinates.y;
        int z = blockChunkCoordinates.z;

        if(c.chunk[x + 1][y][z].type == BlockType.AIR) {
            faces[Faces.RIGHT.index] = 1;
        } else {
            faces[Faces.RIGHT.index] = 0;
        }
        if(c.chunk[x - 1][y][z].type == BlockType.AIR) {
            faces[Faces.LEFT.index] = 1;
        } else {
            faces[Faces.LEFT.index] = 0;
        }
        if(y + 1 >= Chunk.CHUNK_HEIGHT || c.chunk[x][y + 1][z].type == BlockType.AIR) {
            faces[Faces.TOP.index] = 1;
        } else {
            faces[Faces.TOP.index] = 0;
        }
        if(y - 1 >= 0 && c.chunk[x][y - 1][z].type == BlockType.AIR) {
            faces[Faces.BOTTOM.index] = 1;
        } else {
            faces[Faces.BOTTOM.index] = 0;
        }
        if(c.chunk[x][y][z + 1].type == BlockType.AIR) {
            faces[Faces.FRONT.index] = 1;
        } else {
            faces[Faces.FRONT.index] = 0;
        }
        if(c.chunk[x][y][z - 1].type == BlockType.AIR) {
            faces[Faces.BACK.index] = 1;
        } else {
            faces[Faces.BACK.index] = 0;
        }
	}



    
}
