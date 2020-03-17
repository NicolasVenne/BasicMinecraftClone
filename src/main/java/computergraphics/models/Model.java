package computergraphics.models;



/**
 * Model
 */
public class Model {



    private int vaoID;
    private int vertexCount;

    public Model(int vaoID, int vertexCount) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }

    public Model(Model model) {
        this.vaoID = model.vaoID;
        this.vertexCount = model.vertexCount;
    } 

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }

}