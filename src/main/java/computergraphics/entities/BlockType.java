package computergraphics.entities;

import computergraphics.graphics.Loader;
import computergraphics.graphics.Material;

/**
 * BlockType
 * The different blocks in the game
 * Holds the material in a variable for all blocks of the same type
 */
public enum BlockType {

    

    AIR(0,0f),
    GRASS(1, 0.5f),
    DIRT(2, 0.5f),
    STONE(3, 0.5f),
    DIAMOND(4,0.5f);
    
    private final Material mat;
    private final int id;

    private BlockType(int id, float reflectance) {
        if(this.name() == "AIR") {
            mat = null;
        } else {
            mat = new Material(Loader.loadTexture(this.name()), reflectance);

        }
        this.id = id;
    }

    /**
     * Get the material for this block type
     * @return Material
     */
    public Material getMaterial() {
        return this.mat;
    }

    /**
     * Get the id for this block type
     * @return int
     */
    public int getID() {
        return this.id;
    }

    /**
     * Get the block type by a given id
     * @param id The id of the block type
     * @return Block type
     */
    public static BlockType getTypeByID(int id) {
        for (BlockType type : BlockType.values()) {
            if(id == type.id) {
                return type;
            }
        }
        return null;
    }
    
}