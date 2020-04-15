package computergraphics.models;

import computergraphics.graphics.Texture2D;

/**
 * TexturedModel
 * Holds the texture and a model
 */
public class TexturedModel extends Model{

    private Texture2D texture;

    public TexturedModel(Model model, Texture2D texture) {
        super(model);
        this.texture = texture;
    }

    public TexturedModel(int vaoID, int vertexCount) {
        super(vaoID, vertexCount);
        texture = null;
    }

    public TexturedModel(TexturedModel copy) {
        super(copy);
        texture = copy.getTexture();
    }

    public void attachTexture(Texture2D texture) {
        this.texture = texture;
    }

    public Texture2D getTexture() {
        return texture;
    }

    public void setTexture(Texture2D texure) {
        this.texture = texure;
    }

    
    
}