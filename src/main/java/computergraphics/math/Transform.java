package computergraphics.math;


/**
 * Transform
 */
public class Transform {

    private Vector3 position;
    private Vector3 rotation;
    private Vector3 scale;

    public Transform(Vector3 position, Vector3 rotation, Vector3 scale) {
        this.position = new Vector3(position);
        this.rotation = new Vector3(rotation);
        this.scale = new Vector3(scale);
    }

    public Vector3 position() {
        return position;
    }

    public Vector3 rotation() {
        return new Vector3(rotation);
    }

    public Vector3 scale() {
        return new Vector3(scale);
    }

    public void translate(Vector3 to) {
        this.position.x += to.x;
        this.position.y += to.y;
        this.position.z += to.z;
    }

    public void rotate(Vector3 by) {
        this.rotation.x += by.x;
        this.rotation.y += by.y;
        this.rotation.z += by.z;
    }

    public void scale(Vector3 to) {
        this.scale.x = to.x;
        this.scale.y = to.y;
        this.scale.z = to.z;
    }

    public static Matrix4x4 createTransformationMatrix(Transform transform) {
        Matrix4x4 result = new Matrix4x4();
        result.translate(transform.position());
        result.rotate(transform.rotation().x, new Vector3(1f,0f,0f));
        result.rotate(transform.rotation().y, new Vector3(0f,1f,0f));
        result.rotate(transform.rotation().z, new Vector3(0f,0f,1f));
        result.scale(transform.scale());
        return result;
    }


}