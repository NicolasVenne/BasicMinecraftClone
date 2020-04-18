package computergraphics.core;

import java.util.HashMap;

import org.joml.Vector2i;
import org.joml.Vector3i;

import computergraphics.entities.Block;
import computergraphics.entities.BlockType;
import computergraphics.entities.FaceSide;
import computergraphics.math.NoiseGen;
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
    public boolean isVisible;
    private boolean chunkReceived = false;
    public boolean genereated = false;
    public boolean isInsideFrustrum = false;
    public static float borderRadius = 32f;
    public HashMap<Vector3i, Block> visibleInnerBlocks;
    public HashMap<Vector3i, Block> visibleEdgeBlocks;
    public boolean hasCollisions;
    
    public Chunk(Vector2i coordinates) {
        chunk = null;     
        this.coordinates = coordinates;      
        isVisible = false;  
        hasCollisions = false;
    }

    
    /** 
     * Break the block in the chunk at a given possition
     * //TODO: Only breaks the inside blocks
     * @param pos A Vector3i possition of the block in the chunk relative to the chunk (Not world space)
     */
    public void breakBlock(Vector3i pos) {
        //Try to remove the block from the vissible inner blocks of the chunk
        Block brokenInnerBlock = visibleInnerBlocks.remove(pos);
        //If there was a block that was removed, update the chunks arround that one.
        if(brokenInnerBlock != null) {
            TerrainGenerator.instance.colliders.remove(brokenInnerBlock.collider);
            chunk[pos.x][pos.y][pos.z] = 0;
            Vector3i lookUp = new Vector3i(pos.x + 1, pos.y, pos.z); //Right
            Block block;

            //Check all sides, then update the blocks.
            if(lookUp.x == 15) {
                block = visibleEdgeBlocks.get(lookUp);
                if(block != null) {
                    block.faces[FaceSide.LEFT.index] = 1;
                } else if(chunk[lookUp.x][lookUp.y][lookUp.z] != 0){
                    Vector3i temp = new Vector3i(lookUp);
                    Block tempBlock = new Block(BlockType.getTypeByID(chunk[lookUp.x][lookUp.y][lookUp.z]), temp, coordinates, new int[]{0,0,0,1,0,0});
                    visibleEdgeBlocks.put(temp, tempBlock);
                    TerrainGenerator.instance.colliders.add(tempBlock.collider);
                }
            } else if(lookUp.x == 16) {
                
            } else {
                block = visibleInnerBlocks.get(lookUp);
                if(block != null) {
                    block.faces[FaceSide.LEFT.index] = 1;
                } else if(chunk[lookUp.x][lookUp.y][lookUp.z] != 0){
                    Vector3i temp = new Vector3i(lookUp);
                    Block tempBlock = new Block(BlockType.getTypeByID(chunk[lookUp.x][lookUp.y][lookUp.z]), temp, coordinates, new int[]{0,0,0,1,0,0});
                    visibleInnerBlocks.put(temp, tempBlock);
                    TerrainGenerator.instance.colliders.add(tempBlock.collider);
                }
            }

            lookUp = new Vector3i(pos.x - 1, pos.y, pos.z); //Right
            if(lookUp.x == 0) {
                block = visibleEdgeBlocks.get(lookUp);
                if(block != null) {
                    block.faces[FaceSide.RIGHT.index] = 1;
                } else if(chunk[lookUp.x][lookUp.y][lookUp.z] != 0){
                    Vector3i temp = new Vector3i(lookUp);
                    Block tempBlock = new Block(BlockType.getTypeByID(chunk[lookUp.x][lookUp.y][lookUp.z]), temp, coordinates, new int[]{0,0,1,0,0,0});
                    visibleEdgeBlocks.put(temp, tempBlock);
                    TerrainGenerator.instance.colliders.add(tempBlock.collider);
                }
            } else if(lookUp.x == -1) {
                
            } else {
                block = visibleInnerBlocks.get(lookUp);
                if(block != null) {
                    block.faces[FaceSide.RIGHT.index] = 1;
                } else if(chunk[lookUp.x][lookUp.y][lookUp.z] != 0){
                    Vector3i temp = new Vector3i(lookUp);
                    Block tempBlock = new Block(BlockType.getTypeByID(chunk[lookUp.x][lookUp.y][lookUp.z]), temp, coordinates, new int[]{0,0,1,0,0,0});
                    visibleInnerBlocks.put(temp, tempBlock);
                    TerrainGenerator.instance.colliders.add(tempBlock.collider);
                }
            }

            lookUp = new Vector3i(pos.x, pos.y, pos.z + 1); //Front
            if(lookUp.z == 15) {
                block = visibleEdgeBlocks.get(lookUp);
                if(block != null) {
                    block.faces[FaceSide.BACK.index] = 1;
                } else if(chunk[lookUp.x][lookUp.y][lookUp.z] != 0){
                    Vector3i temp = new Vector3i(lookUp);
                    Block tempBlock = new Block(BlockType.getTypeByID(chunk[lookUp.x][lookUp.y][lookUp.z]), temp, coordinates, new int[]{0,0,0,0,0,1});
                    visibleEdgeBlocks.put(temp, tempBlock);
                    TerrainGenerator.instance.colliders.add(tempBlock.collider);
                }
            } else if(lookUp.z == 16) {
                
            } else {
                block = visibleInnerBlocks.get(lookUp);
                if(block != null) {
                    block.faces[FaceSide.BACK.index] = 1;
                } else if(chunk[lookUp.x][lookUp.y][lookUp.z] != 0){
                    Vector3i temp = new Vector3i(lookUp);
                    Block tempBlock = new Block(BlockType.getTypeByID(chunk[lookUp.x][lookUp.y][lookUp.z]), temp, coordinates, new int[]{0,0,0,0,0,1});
                    visibleInnerBlocks.put(temp, tempBlock);
                    TerrainGenerator.instance.colliders.add(tempBlock.collider);
                }
            }

            lookUp = new Vector3i(pos.x, pos.y, pos.z - 1); //Front
            if(lookUp.z == 0) {
                block = visibleEdgeBlocks.get(lookUp);
                if(block != null) {
                    block.faces[FaceSide.FRONT.index] = 1;
                } else if(chunk[lookUp.x][lookUp.y][lookUp.z] != 0){
                    Vector3i temp = new Vector3i(lookUp);
                    Block tempBlock = new Block(BlockType.getTypeByID(chunk[lookUp.x][lookUp.y][lookUp.z]), temp, coordinates, new int[]{1,0,0,0,0,0});
                    visibleEdgeBlocks.put(temp, tempBlock);
                    TerrainGenerator.instance.colliders.add(tempBlock.collider);
                }
            } else if(lookUp.z == -1) {
                
            } else {
                block = visibleInnerBlocks.get(lookUp);
                if(block != null) {
                    block.faces[FaceSide.FRONT.index] = 1;
                } else if(chunk[lookUp.x][lookUp.y][lookUp.z] != 0){
                    Vector3i temp = new Vector3i(lookUp);
                    Block tempBlock =  new Block(BlockType.getTypeByID(chunk[lookUp.x][lookUp.y][lookUp.z]), temp, coordinates, new int[]{1,0,0,0,0,0});
                    visibleInnerBlocks.put(temp,tempBlock);
                    TerrainGenerator.instance.colliders.add(tempBlock.collider);
                }
            }

            lookUp = new Vector3i(pos.x, pos.y + 1, pos.z); //Front
            if(lookUp.y < CHUNK_HEIGHT) {
                if(lookUp.z == 0 || lookUp.z == 15 || lookUp.x == 0 || lookUp.x == 15) {
                    block = visibleEdgeBlocks.get(lookUp);
                    if(block != null) {
                        block.faces[FaceSide.BOTTOM.index] = 1;
                    } else if(chunk[lookUp.x][lookUp.y][lookUp.z] != 0){
                        Vector3i temp = new Vector3i(lookUp);
                        Block tempBlock = new Block(BlockType.getTypeByID(chunk[lookUp.x][lookUp.y][lookUp.z]), temp, coordinates, new int[]{0,0,0,0,1,0});
                        visibleEdgeBlocks.put(temp, tempBlock);
                        TerrainGenerator.instance.colliders.add(tempBlock.collider);
                    }
                } else {
                    block = visibleInnerBlocks.get(lookUp);
                    if(block != null) {
                        block.faces[FaceSide.BOTTOM.index] = 1;
                    } else if(chunk[lookUp.x][lookUp.y][lookUp.z] != 0){
                        Vector3i temp = new Vector3i(lookUp);
                        Block tempBlock = new Block(BlockType.getTypeByID(chunk[lookUp.x][lookUp.y][lookUp.z]), temp, coordinates, new int[]{0,0,0,0,1,0});
                        visibleInnerBlocks.put(temp, tempBlock);
                        TerrainGenerator.instance.colliders.add(tempBlock.collider);
                    }
                }
            }

            lookUp = new Vector3i(pos.x, pos.y - 1, pos.z); //Front
            if(lookUp.y >= 0) {
                if(lookUp.z == 0 || lookUp.z == 15 || lookUp.x == 0 || lookUp.x == 15) {
                    block = visibleEdgeBlocks.get(lookUp);
                    if(block != null) {
                        block.faces[FaceSide.TOP.index] = 1;
                    } else if(chunk[lookUp.x][lookUp.y][lookUp.z] != 0){
                        Vector3i temp = new Vector3i(lookUp);
                        Block tempBlock = new Block(BlockType.getTypeByID(chunk[lookUp.x][lookUp.y][lookUp.z]), temp, coordinates, new int[]{0,1,0,0,0,0});
                        visibleEdgeBlocks.put(temp, tempBlock);
                        TerrainGenerator.instance.colliders.add(tempBlock.collider);
                    }
                } else {
                    block = visibleInnerBlocks.get(lookUp);
                    if(block != null) {
                        block.faces[FaceSide.TOP.index] = 1;
                    } else if(chunk[lookUp.x][lookUp.y][lookUp.z] != 0){
                        Vector3i temp = new Vector3i(lookUp);
                        Block tempBlock = new Block(BlockType.getTypeByID(chunk[lookUp.x][lookUp.y][lookUp.z]), temp, coordinates, new int[]{0,1,0,0,0,0});
                        visibleInnerBlocks.put(temp, tempBlock);
                        TerrainGenerator.instance.colliders.add(tempBlock.collider);
                    }
                }
            }
        }
    }


    
    /** 
     * Static method to find the vissible blocks in a chunk (Those who are ajacent to air)
     * @param chunk The chunk to check int[][][]
     * @param visibleEdgeBlocks A HashMap of vissible blocks on the edge and their possitions
     * @param visibleInnerBlocks A HashMap of vissible blocks on the inside and their possitions
     * @param coordinates The Coordinates of the chunk being passed in.
     */
    private static void calculateVisibleBlocks(int[][][] chunk, HashMap<Vector3i, Block> visibleEdgeBlocks, HashMap<Vector3i, Block> visibleInnerBlocks, Vector2i coordinates) {
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
                            Vector3i pos = new Vector3i(x,y,z);
                            visibleInnerBlocks.put(pos, new Block(BlockType.getTypeByID(chunk[x][y][z]), pos, coordinates, faces));
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
                        Vector3i pos = new Vector3i(0,y,z);
                        visibleEdgeBlocks.put(pos, new Block(BlockType.getTypeByID(chunk[0][y][z]), pos, coordinates, faces));
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
                        Vector3i pos = new Vector3i(CHUNK_WIDTH - 1,y,z);
                        visibleEdgeBlocks.put(pos, new Block(BlockType.getTypeByID(chunk[CHUNK_WIDTH - 1][y][z]), pos, coordinates, faces));
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
                        Vector3i pos = new Vector3i(x,y,0);
                        visibleEdgeBlocks.put(pos, new Block(BlockType.getTypeByID(chunk[x][y][0]), new Vector3i(x,y,0), coordinates, faces));
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
                        Vector3i pos = new Vector3i(x,y,CHUNK_WIDTH - 1);
                        visibleEdgeBlocks.put(pos, new Block(BlockType.getTypeByID(chunk[x][y][CHUNK_WIDTH - 1]), new Vector3i(x,y,CHUNK_WIDTH - 1), coordinates, faces));
                    }
                }
            }
        }
    }

    /**
     * Check the chunk if any faces have not been checked vissible because
     * of ajacent chunks not having been loaded.
     */
    private void updateMissingFaces() {
        
        Vector2i view = new Vector2i(0,0);
        for (Block block : visibleEdgeBlocks.values()) {

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

    /**
     * Update the chunk
     * If within the players view distance, set it vissible
     * Will also update the collisions in the chunk
     */
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
    
    /**
     * Add the collisions of this chunk in the global colliders to check.
     */
    public void updateCollisions() {
        if(!hasCollisions) {
            for (Block block : visibleEdgeBlocks.values()) {
                TerrainGenerator.instance.colliders.add(block.collider);
            }
            for (Block block : visibleInnerBlocks.values()) {
                TerrainGenerator.instance.colliders.add(block.collider);
            }
            hasCollisions = true;
        }
    }
    /**
     * Remove the collisions of this chunk in the global colliders
     */
    public void removeCollisions() {
        for (Block block : visibleEdgeBlocks.values()) {
            TerrainGenerator.instance.colliders.remove(block.collider);
        }
        for (Block block : visibleInnerBlocks.values()) {
            TerrainGenerator.instance.colliders.remove(block.collider);
        }
        hasCollisions = false;
    }

	
    /** 
     * Register this chunk's UpdateCallback to update on visibility change.
     * @param callback
     */
    public void register(VisibilityChange callback) {
        visibilityChange = callback;
    }
    
    
    /** 
     * Calls when the height map was received post thread
     * @param heightMap
     */
    public void OnHeightMapReceived(Object heightMap) {
        this.heightMap = (float[][])heightMap;
        ThreadDataRequester.GenerateData(() -> Chunk.GenerateBlocks(this.heightMap, coordinates), this::OnChunkReceived);

    }

    
    /** 
     * Calls when the chunk has been generated
     * @param chunk
     */
    public void OnChunkReceived(Object chunk) {
        ChunkData data = (ChunkData)chunk;
        this.chunk = data.blocks;
        this.visibleEdgeBlocks = data.visibleEdgeBlocks;
        this.visibleInnerBlocks = data.visibleInnerBlocks;
        chunkReceived = true;
        UpdateChunk();

    }

	
    /** 
     * Load this chunk
     */
    public void load() {                                                                        //4, 0.7f, 1.7f, 100
        ThreadDataRequester.GenerateData(() -> NoiseGen.getNoiseMap(CHUNK_WIDTH, CHUNK_WIDTH, 5, 0.8f, 2f, 400, coordinates), this::OnHeightMapReceived);
    }
    
    
    /** 
     * @param heightMap The height map to generate the chunk
     * @param coordinates The coordinates of this chunk in chunk coordinate space
     * @return ChunkData object that has the vissible blocks and block type array
     */
    public static ChunkData GenerateBlocks(float[][] heightMap, Vector2i coordinates) {
        int[][][] blocks = new int[CHUNK_WIDTH][CHUNK_HEIGHT][CHUNK_WIDTH];
        HashMap<Vector3i, Block> visibleEdgeBlocks = new HashMap<Vector3i, Block>();
        HashMap<Vector3i, Block> visibleInnerBlocks = new HashMap<Vector3i, Block>();


        for(int x = 0; x < CHUNK_WIDTH; x++) {
            for(int z = 0; z < CHUNK_WIDTH; z++) {
                for(int y = 0; y <= heightMap[x][z]; y++) {
                    if(y == 0) {
                        blocks[x][y][z] = BlockType.DIAMOND.getID();
                    } else if(y < heightMap[x][z] - 4) {
                        blocks[x][y][z] = BlockType.STONE.getID();
                    } else if(y <= heightMap[x][z] - 1) {
                        blocks[x][y][z] = BlockType.DIRT.getID();
                    } else if(y <= heightMap[x][z]) {
                        blocks[x][y][z] = BlockType.GRASS.getID();
                    }
                }
            }
        }

        calculateVisibleBlocks(blocks, visibleEdgeBlocks, visibleInnerBlocks, coordinates);

        return new ChunkData(visibleEdgeBlocks, visibleInnerBlocks, blocks);

    }
    /**
     * ChunkData
     * Holds the data for a given chunk.
     */
    public static class ChunkData {
        public HashMap<Vector3i, Block> visibleEdgeBlocks;
        public HashMap<Vector3i, Block> visibleInnerBlocks;
        public int[][][] blocks;

        public ChunkData(HashMap<Vector3i, Block> visibleEdgeBlocks, HashMap<Vector3i, Block> visibleInnerBlocks, int[][][] blocks) {
            this.visibleEdgeBlocks = visibleEdgeBlocks;
            this.visibleInnerBlocks = visibleInnerBlocks;
            this.blocks = blocks;
        }
    }

    
}