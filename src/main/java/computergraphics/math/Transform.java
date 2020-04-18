package computergraphics.math;

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Transform
 * Handles the position, rotation and scale of an object
 */
public class Transform {

    public Vector3f position;
    public Vector3f rotation;
    public Vector3f scale;

    /**
     * Creates a transform
     * @param position Vector3f initial position
     * @param rotation Vector3f inital rotation
     * @param scale Vector3f inital scale
     */
    public Transform(Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = new Vector3f(position);
        this.rotation = new Vector3f(rotation);
        this.scale = new Vector3f(scale);
    }

    

    /**
     * Copy constructor
     */
    public Transform(Transform transform) {
        this.position = new Vector3f(transform.position);
        this.rotation = new Vector3f(transform.rotation);
        this.scale = new Vector3f(transform.scale);
	}



	
    /** 
     * Translate position given a vector
     * @param to The vector to translate by
     */
    public void translate(Vector3f to) {
        this.position.x += to.x;
        this.position.y += to.y;
        this.position.z += to.z;
    }

    
    /** 
     * Rotate the transform by a vector
     * @param by the vector to rotate by
     */
    public void rotate(Vector3f by) {
        this.rotation.x += by.x;
        this.rotation.y += by.y;
        this.rotation.z += by.z;
    }

    
    /** 
     * Rotate the transform by values
     * @param x float value
     * @param y float value
     * @param z float vlaue
     */
    public void rotate(float x, float y, float z) {
        this.rotation.x += x;
        this.rotation.y += y;
        this.rotation.z += z;
    }

    
    /** 
     * Scale the transform by a vector
     * @param to The vector to scale by
     */
    public void scale(Vector3f to) {
        this.scale.x = to.x;
        this.scale.y = to.y;
        this.scale.z = to.z;
    }

    
    /** 
     * Static method to create a world matrix from a transform
     * @param transform The transform to create the matrix from
     * @return Matrix4f the world matrix
     */
    public static Matrix4f createWorldMatrix(Transform transform) {
        Matrix4f result = new Matrix4f();
        result.translate(transform.position);
        result.rotate(transform.rotation.x, new Vector3f(1f,0f,0f));
        result.rotate(transform.rotation.y, new Vector3f(0f,1f,0f));
        result.rotate(transform.rotation.z, new Vector3f(0f,0f,1f));
        result.scale(transform.scale);
        return result;
    }

	
    /** 
     * Return a zero transform with a scale of 1
     * @return Transform
     */
    public static Transform zero() {
		return new Transform(new Vector3f(0,0,0), new Vector3f(0,0,0), new Vector3f(1,1,1));
	}


}