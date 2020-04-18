package computergraphics.models;

import computergraphics.graphics.Material;
import computergraphics.graphics.Texture2D;

/**
 * MaterialModel
 * Holds a model and a material
 */
public class MaterialModel extends Model {

    private Material material;

    public MaterialModel(int vaoID, int vertexCount) {
        super(vaoID, vertexCount);
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public MaterialModel(Model model, Texture2D texture) {
        super(model);
        setMaterial(new Material(texture, 1.0f));
    }

    
}