package computergraphics.entities;

import computergraphics.graphics.Loader;
import computergraphics.graphics.Texture2D;
import computergraphics.models.MaterialModel;
import computergraphics.models.TexturedModel;

/**
 * BlockType
 */
public enum BlockType {

    

    AIR,
    GRASS,
    DIRT;
    
    private final MaterialModel model;

    private BlockType() {
        if(this.name() == "AIR") {
            model = null;
        } else {
            model = new MaterialModel(Block.blockModel, Loader.loadTexture(this.name()));

        }
    }

    public MaterialModel getModel() {
        return this.model;
    }
    
}