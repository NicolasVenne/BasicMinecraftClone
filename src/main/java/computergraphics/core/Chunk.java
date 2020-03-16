package computergraphics.core;

import computergraphics.entities.BlockType;

/**
 * Chunk
 */
public class Chunk {

    public static final int CHUNK_WIDTH = 16;
    public static final int CHUNK_HEIGHT = 64;

    public BlockType[][][] chunk;
    
    public Chunk() {
        chunk = new BlockType[CHUNK_WIDTH][CHUNK_HEIGHT][CHUNK_WIDTH];
        generateChunk();
    }

    private void generateChunk() {
        for(int y = 0; y < CHUNK_HEIGHT; y++) {
            for(int z = 0; z < CHUNK_WIDTH; z++) {
                for(int x = 0; x < CHUNK_WIDTH; x++) {
                    if(y < 30) {
                        chunk[x][y][z] = BlockType.DIRT;
                    } else {
                        chunk[x][y][z] = BlockType.AIR;
                    }
                }
            }
        }
    }

	public boolean isNextToAir(int x, int y, int z) {
        if(x + 1 < CHUNK_WIDTH && chunk[x + 1][y][z] == BlockType.AIR) {
            return true;
        }
        if(x - 1 > CHUNK_WIDTH && chunk[x - 1][y][z] == BlockType.AIR) {
            return true;
        }
        if(y + 1 < CHUNK_HEIGHT && chunk[x][y + 1][z] == BlockType.AIR) {
            return true;
        }
        if(y - 1 > CHUNK_HEIGHT && chunk[x][y - 1][z] == BlockType.AIR) {
            return true;
        }
        if(z + 1 < CHUNK_WIDTH && chunk[x][y][z + 1] == BlockType.AIR) {
            return true;
        }
        if(z - 1 > CHUNK_WIDTH && chunk[x][y][z - 1] == BlockType.AIR) {
            return true;
        }
		return false;
	}




    




    
}