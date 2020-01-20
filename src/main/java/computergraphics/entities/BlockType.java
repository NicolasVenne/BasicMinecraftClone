package computergraphics.entities;

import computergraphics.graphics.Loader;
import computergraphics.graphics.Texture2D;

/**
 * BlockType
 */
public enum BlockType {

    

    
    GRASS,
    DIRT;
    
    private final Texture2D texture;

    private BlockType() {
        this.texture = Loader.loadTexture(this.name());
    }

    public Texture2D getTexture() {
        return this.texture;
    }
    
}