package computergraphics.math;

import java.nio.FloatBuffer;

/**
 * Vector4
 */
public class Vector4 {

    public static final int FLOAT_SIZE = 4;


    public float x;
    public float y;
    public float z;
    public float w;

    public Vector4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public float sqrMagnitude() {
        return x * x + y * y + z * z + w * w;
    }

    public float magnitude() {
        return (float)Math.sqrt(sqrMagnitude());
    }

    public Vector4 normalize() {
        float magnitude = magnitude();
        return divide(magnitude);
    }

    public Vector4 add(Vector4 b) {
        float x = this.x + b.x;
        float y = this.y + b.y;
        float z = this.z + b.z;
        float w = this.w + b.w;
        return new Vector4(x,y,z,w);
    }

    public Vector4 substract(Vector4 b) {
        float x = this.x - b.x;
        float y = this.y - b.y;
        float z = this.z - b.z;
        float w = this.w - b.w;
        return new Vector4(x,y,z,w);
    }

    public Vector4 negate() {
        return multiply(-1f);
    }

    public Vector4 multiply(float scalar) {
        float x = this.x * scalar;
        float y = this.y * scalar;
        float z = this.z * scalar;
        float w = this.w * scalar;
        return new Vector4(x,y,z,w);
    }

    public Vector4 divide(float scalar) {
        return multiply(1f / scalar);
    }

    public float dot(Vector4 b) {
        return this.x * b.x + this.y * b.y + this.z * b.z + this.w * b.w;
    }

    public Vector4 lerp(Vector4 b, float alpha) {
        return this.multiply(1f - alpha).add(b.multiply(alpha));
    }

    public void addToBuffer(FloatBuffer buffer) {
        buffer.put(x).put(y).put(z);
        buffer.flip();
    }

    public static Vector4 zero() {
        return new Vector4(0f, 0f, 0f, 0f);
    }

    public static Vector4 one() {
        return new Vector4(1f, 1f, 1f, 1f);
    }

    public float distance(Vector4 b) {
        return this.substract(b).magnitude();
    }

    public boolean equals(Object other) {
        if(other == null) return false;
        if(!(other instanceof Vector4)) return false;
        Vector4 otherVector = (Vector4)other; 
        if(this.x != otherVector.x) return false;
        if(this.y != otherVector.y) return false;
        if(this.z != otherVector.z) return false;
        if(this.w != otherVector.w) return false;
        return true;
    }
    
}