package computergraphics.entities;

import org.joml.Vector2f;
import org.joml.Vector3f;

import computergraphics.graphics.Loader;
import computergraphics.math.Transform;
import computergraphics.models.TexturedModel;

public class Skybox{
    private static Vector3f[] verticies = {
        //Front  0, 1, 3, 3, 1, 2, -> 0, 1, 3, 3, 1, 2,
        new Vector3f(-0.5f,  0.5f,  0.5f), //V0  -> 0
        new Vector3f(-0.5f, -0.5f,  0.5f), //V1  -> 1
        new Vector3f(0.5f, -0.5f,  0.5f),  //V2  -> 2
        new Vector3f(0.5f,  0.5f,  0.5f),  //V3  -> 3

        //Top 4, 0, 5, 5, 0, 3, -> 4, 5, 6, 6, 5, 7
        new Vector3f(-0.5f,  0.5f, -0.5f), //V4
        new Vector3f(-0.5f,  0.5f,  0.5f), //V5
        new Vector3f(0.5f,  0.5f, -0.5f),  //V6
        new Vector3f(0.5f,  0.5f,  0.5f),  //V7

        //Right 3, 2, 7, 5, 3, 7, -> 8, 9, 10, 11, 8, 10
        new Vector3f(0.5f,  0.5f,  0.5f),  //V8
        new Vector3f(0.5f, -0.5f,  0.5f),  //V9
        new Vector3f(0.5f, -0.5f, -0.5f),  //V10
        new Vector3f(0.5f,  0.5f, -0.5f),  //V11

        //Left 6, 1, 0, 6, 0, 4, -> 12, 13, 14, 12, 14, 15
        new Vector3f(-0.5f, -0.5f, -0.5f), //V12
        new Vector3f(-0.5f, -0.5f,  0.5f), //V13
        new Vector3f(-0.5f,  0.5f,  0.5f), //V14
        new Vector3f(-0.5f,  0.5f, -0.5f), //v15

        //Bottom 2, 1, 6, 2, 6, 7, -> 16, 17, 18, 16, 18, 19
        new Vector3f(0.5f, -0.5f,  0.5f),  //V16
        new Vector3f(-0.5f, -0.5f,  0.5f), //V17
        new Vector3f(-0.5f, -0.5f, -0.5f), //V18
        new Vector3f(0.5f, -0.5f, -0.5f),  //V19

        //Back 7, 6, 4, 7, 4, 5, -> 20, 21, 22, 20, 22, 23
        new Vector3f(0.5f, -0.5f, -0.5f),  //V20
        new Vector3f(-0.5f, -0.5f, -0.5f), //V21
        new Vector3f(-0.5f,  0.5f, -0.5f), //V22
        new Vector3f(0.5f,  0.5f, -0.5f),  //V23




         
    };

    private static Vector2f[] uv = {
        //Front
        new Vector2f(0.5f, 0.33343333333f),
        new Vector2f(0.5f, 0.666766666666f),
        new Vector2f(0.75f, 0.666766666666f),
        new Vector2f(0.75f, 0.33343333333f),

        //Top
        new Vector2f(0.255556f, 0.3333433333333f),
        new Vector2f(0.49999f, 0.3333433333333f),
        new Vector2f(0.255556f, 0.00001f),
        new Vector2f(0.49999f, 0.00001f),
        

        //Right
        new Vector2f(0.75f, 0.33343333333f),
        new Vector2f(0.75f, 0.666766666666f),
        new Vector2f(1f, 0.666766666666f),
        new Vector2f(1f, 0.33343333333f),

        //Left
        
        new Vector2f(0.25f, 0.66676666666f),
        new Vector2f(0.5f, 0.66676666666f),
        new Vector2f(0.5f, 0.33343333333f),
        new Vector2f(0.25f, 0.3334333333f),

        //Bottom
        new Vector2f(0.25f, 0.666766666666f),
        new Vector2f(0.25f, 1f),
        new Vector2f(0.5f, 1f),
        new Vector2f(0.5f, 0.666766666666f),

        //back
        
        new Vector2f(0.0f, 0.666766666666f),
        new Vector2f(0.25f, 0.66676666666f),
        new Vector2f(0.25f, 0.333433333333f),
        new Vector2f(0.0f, 0.333433333333f),
        


    };

    private static int[] indicies = {
        //Front
        0, 1, 3, 3, 1, 2,
        //Top
        4, 5, 6, 6, 5, 7,

        //Right
        8, 9, 10, 11, 8, 10,
        //Left
        12, 13, 14, 12, 14, 15,
        //Bottom
        16, 17, 18, 16, 18, 19,
        //Back
        20, 21, 22, 20, 22, 23
    };

    private Transform position;
    private TexturedModel blockModel;
    private TexturedModel textureModel;

    public Skybox(){
        this.blockModel = Loader.createTexturedModel(verticies, uv, indicies);
        this.position = new Transform(new Vector3f(0,30,-1f),new Vector3f(0,0,0),new Vector3f(500,500,500));
        this.textureModel = new TexturedModel(this.blockModel, Loader.loadTexture("skybox"));
    }

    public TexturedModel getTextureModel(){
        return this.textureModel;
    }

    public TexturedModel getBlockModel(){
        return this.blockModel;
    }

    public Transform getPosition(){
        return this.position;
    }

    // private static TexturedModel model;
    // private static Transform position;

    // public Skybox(String objModel, String textureFile) throws Exception{
    //     this.model = new TexturedModel(ObjectLoader.loadMesh(objModel), Loader.loadTexture(textureFile));
    //     this.position = new Transform(new Vector3f(0,30,-1f),new Vector3f(0,0,0),new Vector3f(1,1,1));
    // }


}