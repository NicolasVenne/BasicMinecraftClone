package computergraphics.core;

import java.util.ArrayList;
import java.util.HashMap;

import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.system.CallbackI.P;

import computergraphics.entities.Block;
import computergraphics.entities.BlockType;
import computergraphics.math.NoiseGen;
import computergraphics.math.Transform;

/**
 * Chunk
 */
public class Chunk {

    public static final int CHUNK_WIDTH = 16;
    public static final int CHUNK_HEIGHT = 64;
    public static HashMap<Vector2i, Chunk> world = new HashMap<Vector2i, Chunk>();
    public static ArrayList<Chunk> visibleChunks = new ArrayList<Chunk>();

    public static void addVisible(Chunk chunk) {
        visibleChunks.add(chunk);
    }

    public static void finished() {
        for(Chunk c : visibleChunks) {
            c.initiate();
        }
	}


    public Vector2i coordinates;
    public float[][] heightMap;
    public boolean initiated;
    public Block[][][] chunk;
    
    public Chunk(Vector2i coordinates) {
        chunk = new Block[CHUNK_WIDTH][CHUNK_HEIGHT][CHUNK_WIDTH];
        this.coordinates = coordinates;
        world.put(coordinates, this);
        heightMap = NoiseGen.getNoiseMap(CHUNK_WIDTH, CHUNK_WIDTH, 4, 0.5f, 1.4f, 100, coordinates);
        for(int x = 0; x < CHUNK_WIDTH; x++) {
            for(int y = 0; y < CHUNK_WIDTH; y++) {
                heightMap[x][y] *= CHUNK_HEIGHT / 2;
                System.out.println(Math.round(heightMap[x][y]));
            }
        }
        generateChunk();
        addVisible(this);
    }

    private void generateChunk() {
        for(int y = 0; y < CHUNK_HEIGHT; y++) {
            for(int z = 0; z < CHUNK_WIDTH; z++) {
                for(int x = 0; x < CHUNK_WIDTH; x++) {
                    if(y <= heightMap[x][z]) {
                        chunk[x][y][z] = new Block(BlockType.DIRT, new Vector3i(x,y,z), coordinates);
                    } else {
                        chunk[x][y][z] = new Block(BlockType.AIR, new Vector3i(x,y,z), coordinates);
                    }
                }
            }
        }

 
    }

    public void initiate()  {
        if(this.initiated) return;
        this.initiated = true;
        initialFaceCheck();

    }

    private void initialFaceCheck() {
        for(Block[][] x : chunk) {
            for(Block[] y : x) {
                for(Block z : y) {
                    z.checkFaces();
                }
            }
        }
    }

	
	




    




    
}