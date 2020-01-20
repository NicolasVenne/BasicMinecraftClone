package computergraphics.math;

import java.nio.FloatBuffer;

/**
 * Vector2
 */
public class Vector2 {

    public static final int FLOAT_SIZE = 2;

    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float sqrMagnitude() {
        return x * x + y * y;
    }

    public float magnitude() {
        return (float)Math.sqrt(sqrMagnitude());
    }

    public Vector2 normalize() {
        float magnitude = magnitude();
        return divide(magnitude);
    }

    public Vector2 add(Vector2 b) {
        float x = this.x + b.x;
        float y = this.y + b.y;
        return new Vector2(x,y);
    }

    public Vector2 substract(Vector2 b) {
        float x = this.x - b.x;
        float y = this.y - b.y;
        return new Vector2(x,y);
    }

    public Vector2 negate() {
        return multiply(-1f);
    }

    public Vector2 multiply(float scalar) {
        float x = this.x * scalar;
        float y = this.y * scalar;
        return new Vector2(x,y);
    }

    public Vector2 divide(float scalar) {
        return multiply(1f / scalar);
    }

    public float dot(Vector2 b) {
        return this.x * b.x + this.y + b.y;
    }

    public Vector2 lerp(Vector2 b, float alpha) {
        return this.multiply(1f - alpha).add(b.multiply(alpha));
    }

    public void addToBuffer(FloatBuffer buffer) {
        buffer.put(x).put(y);
        buffer.flip();
    }

    public static Vector2 zero() {
        return new Vector2(0f, 0f);
    }

    public static Vector2 up() {
        return new Vector2(0f, 1f);
    }
    
    public static Vector2 right() {
        return new Vector2(1f,0f);
    }

    public static Vector2 one() {
        return new Vector2(1f, 1f);
    }

    public static Vector2 left() {
        return new Vector2(-1f, 0f);
    }

    public static Vector2 down() {
        return new Vector2(0f, -1f);
    }

    public float angle(Vector2 to) {
        return (float) Math.acos((this.dot(to)) / (this.magnitude() * to.magnitude()));
    }

    public float distance(Vector2 b) {
        return this.substract(b).magnitude();
    }

    public boolean equals(Object other) {
        if(other == null) return false;
        if(!(other instanceof Vector2)) return false;
        Vector2 otherVector = (Vector2)other; 
        if(this.x != otherVector.x) return false;
        if(this.y != otherVector.y) return false;
        return true;
    }

    public static float[] convertToFloatArray(Vector2[] data) {
        float[] result = new float[data.length * 2];
        for(int i = 0; i < data.length; i++) {
            result[i * 2] = data[i].x;
            result[i * 2 + 1] = data[i].y;
        }
        return result;
    }
    
}