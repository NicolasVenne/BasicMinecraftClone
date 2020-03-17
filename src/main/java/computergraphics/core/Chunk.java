package computergraphics.core;

import java.beans.Visibility;
import java.util.ArrayList;
import java.util.HashMap;

import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.system.CallbackI.P;
import org.lwjgl.system.CallbackI.Z;

import computergraphics.entities.Block;
import computergraphics.entities.BlockType;
import computergraphics.math.NoiseGen;
import computergraphics.math.Transform;
import computergraphics.math.VisibilityChange;

/**
 * Chunk
 */
public class Chunk {

    public static final int CHUNK_WIDTH = 16;
    public static final int CHUNK_HEIGHT = 64;


    


    public Vector2i coordinates;
    public float[][] heightMap;
    public boolean initiated;
    public Block[][][] chunk;
    private VisibilityChange visibilityChange;
    private boolean isVisible;
    private boolean chunkReceived = false;
    public boolean genereated = false;
    
    public Chunk(Vector2i coordinates) {
        chunk = null;
        this.coordinates = coordinates;      
        isVisible = false;  
    }

    private void generateChunk() {
        chunk = new Block[CHUNK_WIDTH][CHUNK_HEIGHT][CHUNK_WIDTH];
        for(int y = 0; y < CHUNK_HEIGHT; y++) {
            for(int z = 0; z < CHUNK_WIDTH; z++) {
                for(int x = 0; x < CHUNK_WIDTH; x++) {
                    if(y <= heightMap[x][z]) {
                        chunk[x][y][z] = new Block(BlockType.DIRT, new Vector3i(x,y,z), new Vector2i(coordinates));
                    } else {
                        chunk[x][y][z] = new Block(BlockType.AIR, new Vector3i(x,y,z), new Vector2i(coordinates));
                    }
                }
            }
        }

 
    }

    public void initiate()  {
        if(this.initiated) return;
        this.initiated = true;
        edgeFaceCheck();

    }

    private void innerFaceCheck() {
        for(int x = 1; x < CHUNK_WIDTH - 1; x++) {
            for(int y = 0; y < CHUNK_HEIGHT; y++) {
                for(int z = 1; z < CHUNK_WIDTH - 1; z++) {
                    chunk[x][y][z].checkInnerFaces();
                }
            }
        }
    }

    private void edgeFaceCheck() {
        for(int y = 0; y < CHUNK_HEIGHT; y++) {
            for(int z = 0; z < CHUNK_WIDTH; z++) {
                chunk[0][y][z].checkEdgeFaces();
                chunk[CHUNK_WIDTH - 1][y][z].checkEdgeFaces();
            }
        }
        for(int y = 0; y < CHUNK_HEIGHT; y++) {
            for(int x = 0; x < CHUNK_WIDTH; x++) {
                chunk[x][y][0].checkEdgeFaces();
                chunk[x][y][CHUNK_WIDTH - 1].checkEdgeFaces();
            }
        }
    }

	public void UpdateChunk() {
        if(chunkReceived) {
            boolean couldSee = isVisible;
            Vector2i calc = new Vector2i(0,0);
            coordinates.sub(TerrainGenerator.instance.playerInChunkCoordinates, calc);
            boolean canSee = calc.length() <= TerrainGenerator.instance.viewDistance;
    
            
    
            if(couldSee != canSee) {
                isVisible = canSee;
                if(canSee) {
                    edgeFaceCheck();
                }
                if(visibilityChange != null) {
                    visibilityChange.OnChunkVisibilityChange(this, canSee);
                }
            }
        }
	}

	public void register(VisibilityChange callback) {
        visibilityChange = callback;
    }
    
    public void OnHeightMapReceived(Object heightMap) {
        this.heightMap = (float[][])heightMap;
        ThreadDataRequester.GenerateData(() -> Chunk.GenerateBlocks(this.heightMap, coordinates), this::OnChunkReceived);

    }

    public void OnChunkReceived(Object chunk) {
        this.chunk = (Block[][][])chunk;
        innerFaceCheck();
        chunkReceived = true;
        UpdateChunk();

    }

	public void load() {
        ThreadDataRequester.GenerateData(() -> NoiseGen.getNoiseMap(CHUNK_WIDTH, CHUNK_WIDTH, 4, 0.5f, 1.4f, 100, coordinates), this::OnHeightMapReceived);
    }
    
    public static Block[][][] GenerateBlocks(float[][] heightMap, Vector2i coordinates) {
        Block[][][] blocks = new Block[CHUNK_WIDTH][CHUNK_HEIGHT][CHUNK_WIDTH];
        for(int y = 0; y < CHUNK_HEIGHT; y++) {
            for(int z = 0; z < CHUNK_WIDTH; z++) {
                for(int x = 0; x < CHUNK_WIDTH; x++) {
                    if(y <= heightMap[x][z]) {
                        blocks[x][y][z] = new Block(BlockType.DIRT, new Vector3i(x,y,z), new Vector2i(coordinates));
                    } else {
                        blocks[x][y][z] = new Block(BlockType.AIR, new Vector3i(x,y,z), new Vector2i(coordinates));
                    }
                }
            }
        }
        return blocks;

    }

    
}