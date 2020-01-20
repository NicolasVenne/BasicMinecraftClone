package computergraphics.math;

import java.nio.FloatBuffer;

/**
 * Matrix2x2
 */
public class Matrix2x2 {
    
    public static final int FLOAT_SIZE = 4 ;


    private float m00, m01;
    private float m10, m11;

    public Matrix2x2(Vector2 col1, Vector2 col2) {
        m00 = col1.x;
        m10 = col1.y;
        
        m01 = col2.x;
        m11 = col2.y;
    }

    public Matrix2x2() {
        m00 = 1f;
        m10 = 0f;

        m01 = 0f;
        m11 = 1f;
    }

    public Matrix2x2 add(Matrix2x2 other) {
        Matrix2x2 temp = Matrix2x2.identity();
        temp.m00 = this.m00 + other.m00;
        temp.m10 = this.m10 + other.m10;

        temp.m01 = this.m01 + other.m01;
        temp.m11 = this.m11 + other.m11;
        
        return temp;
    }

    public Matrix2x2 multiply(float scalar) {
        Matrix2x2 temp = new Matrix2x2();

        temp.m00 = this.m00 * scalar;
        temp.m10 = this.m10 * scalar;

        temp.m01 = this.m01 * scalar;
        temp.m11 = this.m11 * scalar;

        return temp;
    }

    public Vector2 multiply(Vector2 vector) {
        float x = this.m00 * vector.x + this.m01 * vector.y;
        float y = this.m10 * vector.x + this.m11 * vector.y;

        return new Vector2(x, y);
    }

    public Matrix2x2 multiply(Matrix2x2 other) {
        Matrix2x2 temp = new Matrix2x2();

        temp.m00 = this.m00 * other.m00 + this.m01 * other.m10;
        temp.m10 = this.m10 * other.m00 + this.m11 * other.m10;

        temp.m01 = this.m00 * other.m01 + this.m01 * other.m11;
        temp.m11 = this.m10 * other.m01 + this.m11 * other.m11;

        return temp;
    }

    public Matrix2x2 negate() {
        return multiply(-1f);
    }

    public Matrix2x2 subtract(Matrix2x2 other) {
        return this.add(other.negate());
    }

    public Matrix2x2 transpose() {
        Matrix2x2 temp = new Matrix2x2();
        
        temp.m00 = this.m00;
        temp.m10 = this.m01;

        temp.m01 = this.m10;
        temp.m11 = this.m11;

        return temp;
    }

    public void addToBuffer(FloatBuffer buffer) {
        buffer.put(m00).put(m10);
        buffer.put(m01).put(m11);
        buffer.flip();
    }

    public boolean equals(Object other) {
        if(other == null) return false;
        if(!(other instanceof Matrix2x2)) return false;
        Matrix2x2 otherMatrix = (Matrix2x2)other;

        if(otherMatrix.m00 != this.m00) return false; 
        if(otherMatrix.m10 != this.m10) return false;

        if(otherMatrix.m01 != this.m01) return false;
        if(otherMatrix.m11 != this.m11) return false;

        return true;
    }

    private static Matrix2x2 identity() {
        return new Matrix2x2();
    }

    
    

    
    
}