package computergraphics.entities;

import computergraphics.graphics.Loader;
import computergraphics.models.MaterialModel;

/**
 * Face
 * Face of a block
 */
public class Face {

    public static float[][] faceVertices = { 
        { //Front
            -0.5f,  0.5f,  0.5f, 
            -0.5f, -0.5f,  0.5f, 
             0.5f, -0.5f,  0.5f,  
             0.5f,  0.5f,  0.5f, 
        },
        { //Top
            -0.5f,  0.5f, -0.5f, 
            -0.5f,  0.5f,  0.5f,
             0.5f,  0.5f, -0.5f, 
             0.5f,  0.5f,  0.5f,
        },
        { //Right
            0.5f,  0.5f,  0.5f,  
            0.5f, -0.5f,  0.5f, 
            0.5f, -0.5f, -0.5f,  
            0.5f,  0.5f, -0.5f,
        },
        { //Left
            -0.5f, -0.5f, -0.5f, 
            -0.5f, -0.5f,  0.5f,
            -0.5f,  0.5f,  0.5f,
            -0.5f,  0.5f, -0.5f,
        },
        { //Bottom
             0.5f, -0.5f,  0.5f, 
            -0.5f, -0.5f,  0.5f,
            -0.5f, -0.5f, -0.5f, 
             0.5f, -0.5f, -0.5f, 
        },
        { //Back
             0.5f, -0.5f, -0.5f,  
            -0.5f, -0.5f, -0.5f,
            -0.5f,  0.5f, -0.5f, 
             0.5f,  0.5f, -0.5f, 
        }
    };

    public static float[][] faceNormals = {
        { //Front 
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
        },
        { //Top
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
        },
        { //Right
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
        },
        { //Left
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
        },
        { //Bottom
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
        },
        { //Back
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
        }
    };

    public static float[][] faceUVs = {
        { //Front
            0.0f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.0f,
        },
        { //Top
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.0f, 1.0f,
            0.5f, 1.0f,
        },
        { //Right
            0.0f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.0f,
        },
        { //Left
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.0f,
            0.0f, 0.0f,
        },
        { //Bottom
            0.5f, 0.0f,
            0.5f, 0.5f,
            1.0f, 0.5f,
            1.0f, 0.0f,
        },
        { //Back
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.0f,
            0.0f, 0.0f,
        }
    };

    public static int[][] faceIndices = {
        { 0, 1, 3, 3, 1, 2,}, //front
        { 0, 1, 2, 2, 1, 3,}, //Top
        { 0, 1, 2, 3, 0, 2,}, //Right
        { 0, 1, 2, 0, 2, 3,}, //Left
        { 0, 1, 2, 0, 2, 3,}, //Bottom
        { 0, 1, 2, 0, 2, 3}, //Back
    };
    
    public static final int faceIndiceCount = 6;

    public static MaterialModel[] faceModels = {
        Loader.createMaterialModel(faceVertices[FaceSide.FRONT.index], faceUVs[FaceSide.FRONT.index], faceNormals[FaceSide.FRONT.index], faceIndices[FaceSide.FRONT.index]),
        Loader.createMaterialModel(faceVertices[FaceSide.TOP.index], faceUVs[FaceSide.TOP.index], faceNormals[FaceSide.TOP.index], faceIndices[FaceSide.TOP.index]),
        Loader.createMaterialModel(faceVertices[FaceSide.RIGHT.index], faceUVs[FaceSide.RIGHT.index], faceNormals[FaceSide.RIGHT.index], faceIndices[FaceSide.RIGHT.index]),
        Loader.createMaterialModel(faceVertices[FaceSide.LEFT.index], faceUVs[FaceSide.LEFT.index], faceNormals[FaceSide.LEFT.index], faceIndices[FaceSide.LEFT.index]),
        Loader.createMaterialModel(faceVertices[FaceSide.BOTTOM.index], faceUVs[FaceSide.BOTTOM.index], faceNormals[FaceSide.BOTTOM.index], faceIndices[FaceSide.BOTTOM.index]),
        Loader.createMaterialModel(faceVertices[FaceSide.BACK.index], faceUVs[FaceSide.BACK.index], faceNormals[FaceSide.BACK.index], faceIndices[FaceSide.BACK.index]),

    };

    
    /** 
     * Get the VAO for a block face given the side
     * @param side The face side to return
     * @return MaterialModel The model of the face side asked for
     */
    public static MaterialModel getFaceModel(FaceSide side) {
        return faceModels[side.index];
    }

    
    /** 
     * Get the VAO for a block face given the side
     * @param side The face side to return
     * @return MaterialModel The model of the face side asked for
     */
    public static MaterialModel getFaceModel(int side) {
        return faceModels[side];
    }

    
}