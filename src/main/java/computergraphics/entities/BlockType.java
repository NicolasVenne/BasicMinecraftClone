package computergraphics.entities;

import computergraphics.graphics.Loader;
import computergraphics.graphics.Texture2D;
import computergraphics.models.TexturedModel;

/**
 * BlockType
 */
public enum BlockType {

    

    AIR,
    GRASS,
    DIRT;
    
    private final TexturedModel model;

    private BlockType() {
        if(this.name() == "AIR") {
            model = null;
        } else {
            model = new TexturedModel(Block.blockModel, Loader.loadTexture(this.name()));

        }
    }

    public TexturedModel getModel() {
        return this.model;
    }
    
}