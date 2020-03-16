package computergraphics.math;

import java.nio.FloatBuffer;

/**
 * Vector3
 */
public class Vector3 {

    public static final int FLOAT_SIZE = 3;


    public float x;
    public float y;
    public float z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 copy) {
        this.x = copy.x;
        this.y = copy.y;
        this.z = copy.z;
    }

    public float sqrMagnitude() {
        return x * x + y * y + z * z;
    }

    public float magnitude() {
        return (float)Math.sqrt(sqrMagnitude());
    }

    public Vector3 normalize() {
        float magnitude = magnitude();
        return divide(magnitude);
    }

    public Vector3 add(Vector3 b) {
        float x = this.x + b.x;
        float y = this.y + b.y;
        float z = this.z + b.z;
        return new Vector3(x,y,z);
    }

    public Vector3 substract(Vector3 b) {
        float x = this.x - b.x;
        float y = this.y - b.y;
        float z = this.z - b.z;
        return new Vector3(x,y,z);
    }

    public Vector3 negate() {
        return multiply(-1f);
    }

    public Vector3 multiply(float scalar) {
        float x = this.x * scalar;
        float y = this.y * scalar;
        float z = this.z * scalar;
        return new Vector3(x,y,z);
    }

    public Vector3 divide(float scalar) {
        return multiply(1f / scalar);
    }

    public float dot(Vector3 b) {
        return this.x * b.x + this.y * b.y + this.z * b.z;
    }

    public Vector3 cross(Vector3 b) {
        float x = this.y * b.z - this.z * b.y;
        float y = this.z * b.x - this.x * b.z;
        float z = this.x * b.y - this.y * b.x;
        return new Vector3(x,y,z);
    }

    public Vector3 lerp(Vector3 b, float alpha) {
        return this.multiply(1f - alpha).add(b.multiply(alpha));
    }

    public void addToBuffer(FloatBuffer buffer) {
        buffer.put(x).put(y).put(z);
        buffer.flip();
    }

    public static Vector3 zero() {
        return new Vector3(0f, 0f, 0f);
    }

    public static Vector3 up() {
        return new Vector3(0f, 1f, 0f);
    }
    
    public static Vector3 right() {
        return new Vector3(1f,0f, 0f);
    }

    public static Vector3 one() {
        return new Vector3(1f, 1f, 1f);
    }

    public static Vector3 left() {
        return new Vector3(-1f, 0f, 0f);
    }

    public static Vector3 down() {
        return new Vector3(0f, -1f, 0f);
    }

    public static Vector3 forward() {
        return new Vector3(0f, 0f, 1f);
    }

    public static Vector3 back() {
        return new Vector3(0f, 0f, -1f);
    }

    public float angle(Vector3 to) {
        return (float) Math.acos((this.dot(to)) / (this.magnitude() * to.magnitude()));
    }

    public float distance(Vector3 b) {
        return this.substract(b).magnitude();
    }

    public boolean equals(Object other) {
        if(other == null) return false;
        if(!(other instanceof Vector3)) return false;
        Vector3 otherVector = (Vector3)other; 
        if(this.x != otherVector.x) return false;
        if(this.y != otherVector.y) return false;
        if(this.z != otherVector.z) return false;
        return true;
    }

    public static float[] convertToFloatArray(Vector3[] data) {
        float[] result = new float[data.length * 3];
        for(int i = 0; i < data.length; i++) {
            result[i * 3] = data[i].x;
            result[i * 3 + 1] = data[i].y;
            result[i * 3 + 2] = data[i].z;
        }
        return result;
    }
    
}