package computergraphics.entities;

import computergraphics.graphics.Loader;
import computergraphics.graphics.Material;
import computergraphics.graphics.Texture2D;
import computergraphics.models.MaterialModel;
import computergraphics.models.TexturedModel;

/**
 * BlockType
 */
public enum BlockType {

    

    AIR(0,0f),
    GRASS(1, 0.5f),
    DIRT(2, 0.5f);
    
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

    public Material getMaterial() {
        return this.mat;
    }

    public int getID() {
        return this.id;
    }

    public static BlockType getTypeByID(int id) {
        for (BlockType type : BlockType.values()) {
            if(id == type.id) {
                return type;
            }
        }
        return null;
    }
    
}