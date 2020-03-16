package computergraphics.math;

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Transform
 */
public class Transform {

    public Vector3f position;
    public Vector3f rotation;
    public Vector3f scale;

    public Transform(Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = new Vector3f(position);
        this.rotation = new Vector3f(rotation);
        this.scale = new Vector3f(scale);
    }

    

    public Transform(Transform transform) {
        this.position = new Vector3f(transform.position);
        this.rotation = new Vector3f(transform.rotation);
        this.scale = new Vector3f(transform.scale);
	}



	public void translate(Vector3f to) {
        this.position.x += to.x;
        this.position.y += to.y;
        this.position.z += to.z;
    }

    public void rotate(Vector3f by) {
        this.rotation.x += by.x;
        this.rotation.y += by.y;
        this.rotation.z += by.z;
    }

    public void scale(Vector3f to) {
        this.scale.x = to.x;
        this.scale.y = to.y;
        this.scale.z = to.z;
    }

    public static Matrix4f createWorldMatrix(Transform transform) {
        Matrix4f result = new Matrix4f();
        result.translate(transform.position);
        result.rotate(transform.rotation.x, new Vector3f(1f,0f,0f));
        result.rotate(transform.rotation.y, new Vector3f(0f,1f,0f));
        result.rotate(transform.rotation.z, new Vector3f(0f,0f,1f));
        result.scale(transform.scale);
        return result;
    }

	public static Transform zero() {
		return new Transform(new Vector3f(0,0,0), new Vector3f(0,0,0), new Vector3f(0,0,0));
	}


}