package computergraphics.core;

import java.beans.Visibility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;

import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.system.CallbackI.P;
import org.lwjgl.system.CallbackI.Z;

import computergraphics.entities.Block;
import computergraphics.entities.BlockType;
import computergraphics.entities.FaceSide;
import computergraphics.math.NoiseGen;
import computergraphics.math.Transform;
import computergraphics.math.VisibilityChange;

/**
 * Chunk
 */
public class Chunk  {

    public static final int CHUNK_WIDTH = 16;
    public static final int CHUNK_HEIGHT = 64;
    
    

    public Vector2i coordinates;
    public float[][] heightMap;
    public boolean initiated;
    public int[][][] chunk;
    private VisibilityChange visibilityChange;
    private boolean isVisible;
    private boolean chunkReceived = false;
    public boolean genereated = false;
    public boolean isInsideFrustrum = false;
    public static float borderRadius = 32f;
    public HashSet<Block> visibleInnerBlocks;
    public HashSet<Block> visibleEdgeBlocks;
    public boolean hasCollisions;
    
    public Chunk(Vector2i coordinates) {
        chunk = null;

     
        this.coordinates = coordinates;      
        isVisible = false;  
        hasCollisions = false;
    }

    

    

    


    private static void calculateVisibleBlocks(int[][][] chunk, HashSet<Block> visibleEdgeBlocks, HashSet<Block> visibleInnerBlocks, Vector2i coordinates) {
        for(int x = 1; x < CHUNK_WIDTH - 1; x++) {
            for(int y = 0; y < CHUNK_HEIGHT; y++) {
                for(int z = 1; z < CHUNK_WIDTH - 1; z++) {
                    if(chunk[x][y][z] != 0) {
                        int[] faces = {0,0,0,0,0,0};
                        boolean isVisible = false;
                        if(chunk[x + 1][y][z] == BlockType.AIR.getID()) {
                            faces[FaceSide.RIGHT.index] = 1;
                            isVisible = true;
                        }
                        if(chunk[x - 1][y][z] == BlockType.AIR.getID()) {
                            faces[FaceSide.LEFT.index] = 1;
                            isVisible = true;
                        }
                        if(y + 1 >= Chunk.CHUNK_HEIGHT || chunk[x][y + 1][z] == BlockType.AIR.getID()) {
                            faces[FaceSide.TOP.index] = 1;
                            isVisible = true;
                        }
                        if(y - 1 >= 0 && chunk[x][y - 1][z] == BlockType.AIR.getID()) {
                            faces[FaceSide.BOTTOM.index] = 1;
                            isVisible = true;
                        } 
                        if(chunk[x][y][z + 1] == BlockType.AIR.getID()) {
                            faces[FaceSide.FRONT.index] = 1;
                            isVisible = true;
                        } 
                        if(chunk[x][y][z - 1] == BlockType.AIR.getID()) {
                            faces[FaceSide.BACK.index] = 1;
                            isVisible = true;
                        } 
                        if(isVisible) {
                            visibleInnerBlocks.add(new Block(BlockType.getTypeByID(chunk[x][y][z]), new Vector3i(x,y,z), coordinates, faces));
                        }
                    }
                }
            }
        }


        for(int y = 0; y < CHUNK_HEIGHT; y++) {
            for(int z = 0; z < CHUNK_WIDTH; z++) {
                if(chunk[0][y][z] != 0) {
                    int[] faces = {0,0,0,0,0,0};
                    boolean isVisible = false;
                    if(chunk[1][y][z] == BlockType.AIR.getID()) {
                        faces[FaceSide.RIGHT.index] = 1;
                        isVisible = true;
                    }
                    if(y + 1 >= Chunk.CHUNK_HEIGHT || chunk[0][y + 1][z] == BlockType.AIR.getID()) {
                        faces[FaceSide.TOP.index] = 1;
                        isVisible = true;
                    }
                    if(y - 1 >= 0 && chunk[0][y - 1][z] == BlockType.AIR.getID()) {
                        faces[FaceSide.BOTTOM.index] = 1;
                        isVisible = true;
                    } 
                    if(z + 1 < CHUNK_WIDTH && chunk[0][y][z + 1] == BlockType.AIR.getID()) {
                        faces[FaceSide.FRONT.index] = 1;
                        isVisible = true;
                    } 
                    if(z - 1 >= 0 && chunk[0][y][z - 1] == BlockType.AIR.getID()) {
                        faces[FaceSide.BACK.index] = 1;
                        isVisible = true;
                    } 
                    if(isVisible) {
                        visibleEdgeBlocks.add(new Block(BlockType.getTypeByID(chunk[0][y][z]), new Vector3i(0,y,z), coordinates, faces));
                    }
                }
                    
                if(chunk[CHUNK_WIDTH - 1][y][z] != BlockType.AIR.getID()) {
                    int[] faces = {0,0,0,0,0,0};
                    boolean isVisible = false;
                    if(chunk[CHUNK_WIDTH - 2][y][z] == BlockType.AIR.getID()) {
                        faces[FaceSide.LEFT.index] = 1;
                        isVisible = true;
                    }
                    if(y + 1 >= Chunk.CHUNK_HEIGHT || chunk[CHUNK_WIDTH - 1][y + 1][z] == BlockType.AIR.getID()) {
                        faces[FaceSide.TOP.index] = 1;
                        isVisible = true;
                    }
                    if(y - 1 >= 0 && chunk[CHUNK_WIDTH - 1][y - 1][z] == BlockType.AIR.getID()) {
                        faces[FaceSide.BOTTOM.index] = 1;
                        isVisible = true;
                    } 
                    if(z + 1 < CHUNK_WIDTH && chunk[CHUNK_WIDTH - 1][y][z + 1] == BlockType.AIR.getID()) {
                        faces[FaceSide.FRONT.index] = 1;
                        isVisible = true;
                    } 
                    if(z - 1 >= 0 && chunk[CHUNK_WIDTH - 1][y][z - 1] == BlockType.AIR.getID()) {
                        faces[FaceSide.BACK.index] = 1;
                        isVisible = true;
                    } 
                    if(isVisible) {
                        visibleEdgeBlocks.add(new Block(BlockType.getTypeByID(chunk[CHUNK_WIDTH - 1][y][z]), new Vector3i(CHUNK_WIDTH - 1,y,z), coordinates, faces));
                    }
                }
            }
        }
        for(int y = 0; y < CHUNK_HEIGHT; y++) {
            for(int x = 0; x < CHUNK_WIDTH; x++) {
                if(chunk[x][y][0] != 0) {
                    int[] faces = {0,0,0,0,0,0};
                    boolean isVisible = false;
                    if(chunk[x][y][1] == BlockType.AIR.getID()) {
                        faces[FaceSide.FRONT.index] = 1;
                        isVisible = true;
                    }
                    if(y + 1 >= Chunk.CHUNK_HEIGHT || chunk[x][y + 1][0] == BlockType.AIR.getID()) {
                        faces[FaceSide.TOP.index] = 1;
                        isVisible = true;
                    }
                    if(y - 1 >= 0 && chunk[x][y - 1][0] == BlockType.AIR.getID()) {
                        faces[FaceSide.BOTTOM.index] = 1;
                        isVisible = true;
                    } 
                    if(x + 1 < CHUNK_WIDTH && chunk[x + 1][y][0] == BlockType.AIR.getID()) {
                        faces[FaceSide.RIGHT.index] = 1;
                        isVisible = true;
                    } 
                    if(x - 1 >= 0 && chunk[x - 1][y][0] == BlockType.AIR.getID()) {
                        faces[FaceSide.LEFT.index] = 1;
                        isVisible = true;
                    } 
                    if(isVisible) {
                        visibleEdgeBlocks.add(new Block(BlockType.getTypeByID(chunk[x][y][0]), new Vector3i(x,y,0), coordinates, faces));
                    }
                }
                    
                if(chunk[x][y][CHUNK_WIDTH - 1] != BlockType.AIR.getID()) {
                    int[] faces = {0,0,0,0,0,0};
                    boolean isVisible = false;
                    if(chunk[x][y][CHUNK_WIDTH - 2] == BlockType.AIR.getID()) {
                        faces[FaceSide.BACK.index] = 1;
                        isVisible = true;
                    }
                    if(y + 1 >= CHUNK_HEIGHT || chunk[x][y + 1][CHUNK_WIDTH - 1] == BlockType.AIR.getID()) {
                        faces[FaceSide.TOP.index] = 1;
                        isVisible = true;
                    }
                    if(y - 1 >= 0 && chunk[x][y - 1][CHUNK_WIDTH - 1] == BlockType.AIR.getID()) {
                        faces[FaceSide.BOTTOM.index] = 1;
                        isVisible = true;
                    } 
                    if(x + 1 < CHUNK_WIDTH && chunk[x + 1][y][CHUNK_WIDTH - 1] == BlockType.AIR.getID()) {
                        faces[FaceSide.RIGHT.index] = 1;
                        isVisible = true;
                    } 
                    if(x - 1 >= 0 && chunk[x - 1][y][CHUNK_WIDTH - 1] == BlockType.AIR.getID()) {
                        faces[FaceSide.LEFT.index] = 1;
                        isVisible = true;
                    } 
                    if(isVisible) {
                        visibleEdgeBlocks.add(new Block(BlockType.getTypeByID(chunk[x][y][CHUNK_WIDTH - 1]), new Vector3i(x,y,CHUNK_WIDTH - 1), coordinates, faces));
                    }
                }
            }
        }
    }

    private void updateMissingFaces() {
        
        Vector2i view = new Vector2i(0,0);
        for (Block block : visibleEdgeBlocks) {

            if(block.faces[FaceSide.FRONT.index] == 0) {
                if(block.blockChunkCoordinates.z + 1 >= Chunk.CHUNK_WIDTH) {
                    view.x = block.currentChunkCoordinates.x;
                    view.y = block.currentChunkCoordinates.y + 1;
                    Chunk c = TerrainGenerator.instance.world.get(view);
                    if(c != null) {
                        if(c.chunk[block.blockChunkCoordinates.x][block.blockChunkCoordinates.y][0] == BlockType.AIR.getID()) {
                            block.faces[FaceSide.FRONT.index] = 1;
                        }
                    }
                }
            }
            if(block.faces[FaceSide.BACK.index] == 0) {
                if(block.blockChunkCoordinates.z - 1 < 0) {
                    view.x = block.currentChunkCoordinates.x;
                    view.y = block.currentChunkCoordinates.y - 1;
                    Chunk c = TerrainGenerator.instance.world.get(view);
                    if(c != null) {
                        if(c.chunk[block.blockChunkCoordinates.x][block.blockChunkCoordinates.y][Chunk.CHUNK_WIDTH - 1] == BlockType.AIR.getID()) {
                            block.faces[FaceSide.BACK.index] = 1;
                        }
                    }
                }
            }
            if(block.faces[FaceSide.RIGHT.index] == 0) {
                if(block.blockChunkCoordinates.x + 1 >= Chunk.CHUNK_WIDTH) {
                    view.x = block.currentChunkCoordinates.x + 1;
                    view.y = block.currentChunkCoordinates.y;
                    Chunk c = TerrainGenerator.instance.world.get(view);
                    if(c != null) {
                        if(c.chunk[0][block.blockChunkCoordinates.y][block.blockChunkCoordinates.z] == BlockType.AIR.getID()) {
                            block.faces[FaceSide.RIGHT.index] = 1;
                        }
                    }
                }
            }
            if(block.faces[FaceSide.LEFT.index] == 0) {
                if(block.blockChunkCoordinates.x - 1 < 0) {
                    view.x = block.currentChunkCoordinates.x - 1;
                    view.y = block.currentChunkCoordinates.y;
                    Chunk c = TerrainGenerator.instance.world.get(view);
                    if(c != null) {
                        if(c.chunk[Chunk.CHUNK_WIDTH - 1][block.blockChunkCoordinates.y][block.blockChunkCoordinates.z] == BlockType.AIR.getID()) {
                            block.faces[FaceSide.LEFT.index] = 1;
                        }
                    }
                }
            }
        }
    }

	public void UpdateChunk() {
        if(chunkReceived) {
            boolean couldSee = isVisible;
            Vector2i calc = new Vector2i(0,0);
            coordinates.sub(TerrainGenerator.instance.playerInChunkCoordinates, calc);
            boolean canSee = calc.length() <= TerrainGenerator.instance.viewDistance;
            coordinates.sub(TerrainGenerator.instance.playerInChunkCoordinates, calc);
            boolean shouldUpdateCollisions = calc.length() <= 2;
    
            if(canSee) {
                updateMissingFaces();
                if(shouldUpdateCollisions) {
                    updateCollisions();
                } else if(hasCollisions) {
                    removeCollisions();
                }
            }
            
    
            if(couldSee != canSee) {
                isVisible = canSee;
                if(visibilityChange != null) {
                    visibilityChange.OnChunkVisibilityChange(this, canSee);
                }
            }
            
        }
    }
    
    public void updateCollisions() {
        if(!hasCollisions) {
            for (Block block : visibleEdgeBlocks) {
                TerrainGenerator.instance.colliders.add(block.collider);
            }
            for (Block block : visibleInnerBlocks) {
                TerrainGenerator.instance.colliders.add(block.collider);
            }
            hasCollisions = true;
        }
    }
    public void removeCollisions() {
        for (Block block : visibleEdgeBlocks) {
            TerrainGenerator.instance.colliders.remove(block.collider);
        }
        for (Block block : visibleInnerBlocks) {
            TerrainGenerator.instance.colliders.remove(block.collider);
        }
        hasCollisions = false;
    }

	public void register(VisibilityChange callback) {
        visibilityChange = callback;
    }
    
    public void OnHeightMapReceived(Object heightMap) {
        this.heightMap = (float[][])heightMap;
        ThreadDataRequester.GenerateData(() -> Chunk.GenerateBlocks(this.heightMap, coordinates), this::OnChunkReceived);

    }

    public void OnChunkReceived(Object chunk) {
        ChunkData data = (ChunkData)chunk;
        this.chunk = data.blocks;
        this.visibleEdgeBlocks = data.visibleEdgeBlocks;
        this.visibleInnerBlocks = data.visibleInnerBlocks;
        chunkReceived = true;
        UpdateChunk();

    }

	public void load() {                                                                        //4, 0.7f, 1.7f, 100
        ThreadDataRequester.GenerateData(() -> NoiseGen.getNoiseMap(CHUNK_WIDTH, CHUNK_WIDTH, 5, 0.8f, 2f, 400, coordinates), this::OnHeightMapReceived);
    }
    
    public static ChunkData GenerateBlocks(float[][] heightMap, Vector2i coordinates) {
        int[][][] blocks = new int[CHUNK_WIDTH][CHUNK_HEIGHT][CHUNK_WIDTH];
        HashSet<Block> visibleEdgeBlocks = new HashSet<Block>();
        HashSet<Block> visibleInnerBlocks = new HashSet<Block>();
        for(int y = 0; y < CHUNK_HEIGHT; y++) {
            for(int z = 0; z < CHUNK_WIDTH; z++) {
                for(int x = 0; x < CHUNK_WIDTH; x++) {
                    if(y <= heightMap[x][z]) {
                        blocks[x][y][z] = BlockType.DIRT.getID();
                    } else {
                        blocks[x][y][z] = BlockType.AIR.getID();
                    }
                }
            }
        }

        calculateVisibleBlocks(blocks, visibleEdgeBlocks, visibleInnerBlocks, coordinates);

        return new ChunkData(visibleEdgeBlocks, visibleInnerBlocks, blocks);

    }

    public static class ChunkData {
        public HashSet<Block> visibleEdgeBlocks;
        public HashSet<Block> visibleInnerBlocks;
        public int[][][] blocks;

        public ChunkData(HashSet<Block> visibleEdgeBlocks, HashSet<Block> visibleInnerBlocks, int[][][] blocks) {
            this.visibleEdgeBlocks = visibleEdgeBlocks;
            this.visibleInnerBlocks = visibleInnerBlocks;
            this.blocks = blocks;
        }
    }

    
}