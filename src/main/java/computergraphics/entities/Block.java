package computergraphics.entities;

import computergraphics.graphics.Loader;
import computergraphics.math.Transform;
import computergraphics.math.Vector2;
import computergraphics.math.Vector3;
import computergraphics.models.TexturedModel;

/**
 * Block
 */
public class Block extends Entity{
    
    private static Vector3[] verticies = {
                
        new Vector3(-0.5f,0.5f,-0.5f),
		new Vector3(-0.5f,-0.5f,-0.5f),
		new Vector3(0.5f,-0.5f,-0.5f),	
		new Vector3(0.5f,0.5f,-0.5f),		
				
		new Vector3(-0.5f,0.5f,0.5f),
		new Vector3(-0.5f,-0.5f,0.5f),	
		new Vector3(0.5f,-0.5f,0.5f),	
		new Vector3(0.5f,0.5f,0.5f),
				
        new Vector3(0.5f,0.5f,-0.5f),	
	    new Vector3(0.5f,-0.5f,-0.5f),	
		new Vector3(0.5f,-0.5f,0.5f),	
		new Vector3(0.5f,0.5f,0.5f),
				
		new Vector3(-0.5f,0.5f,-0.5f),	
		new Vector3(-0.5f,-0.5f,-0.5f),	
		new Vector3(-0.5f,-0.5f,0.5f),	
		new Vector3(-0.5f,0.5f,0.5f),
				
		new Vector3(-0.5f,0.5f,0.5f),
		new Vector3(-0.5f,0.5f,-0.5f),
		new Vector3(0.5f,0.5f,-0.5f),
		new Vector3(0.5f,0.5f,0.5f),
				
		new Vector3(-0.5f,-0.5f,0.5f),
		new Vector3(-0.5f,-0.5f,-0.5f),
		new Vector3(0.5f,-0.5f,-0.5f),
        new Vector3(0.5f,-0.5f,0.5f)
    };

    private static Vector2[] uv = {
        new Vector2(0,0),
        new Vector2(0,1),
        new Vector2(1,1),
        new Vector2(1,0),

        new Vector2(0,0),
        new Vector2(0,1),
        new Vector2(1,1),
        new Vector2(1,0),

        new Vector2(0,0),
        new Vector2(0,1),
        new Vector2(1,1),
        new Vector2(1,0),

        new Vector2(0,0),
        new Vector2(0,1),
        new Vector2(1,1),
        new Vector2(1,0),

        new Vector2(0,0),
        new Vector2(0,1),
        new Vector2(1,1),
        new Vector2(1,0),

        new Vector2(0,0),
        new Vector2(0,1),
        new Vector2(1,1),
        new Vector2(1,0)
    };

    private static int[] indicies = {0,1,3,	
        3,1,2,	
        4,5,7,
        7,5,6,
        8,9,11,
        11,9,10,
        12,13,15,
        15,13,14,	
        16,17,19,
        19,17,18,
        20,21,23,
        23,21,22};

    public static TexturedModel blockModel = Loader.createTexturedModel(verticies, uv, indicies);

    public Block(BlockType type, Transform initTransform) {
        super(type.getModel(), initTransform);
        
    }

    
}
