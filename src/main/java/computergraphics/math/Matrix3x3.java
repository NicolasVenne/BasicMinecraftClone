package computergraphics.math;

import java.nio.FloatBuffer;

/**
 * Matrix3x3
 */
public class Matrix3x3 {

    public static final int FLOAT_SIZE = 9;


    private float m00, m01, m02;
    private float m10, m11, m12;
    private float m20, m21, m22;

    public Matrix3x3(Vector3 col1, Vector3 col2, Vector3 col3) {
        m00 = col1.x;
        m10 = col1.y;
        m20 = col1.z;
        
        m01 = col2.x;
        m11 = col2.y;
        m21 = col2.z;

        m02 = col3.x;
        m12 = col3.y;
        m22 = col3.z;

    }

    public Matrix3x3() {
        m00 = 1f;
        m10 = 0f;
        m20 = 0f;

        m01 = 0f;
        m11 = 1f;
        m21 = 0f;

        m02 = 0f;
        m12 = 0f;
        m22 = 1f;
    }

    public Matrix3x3 add(Matrix3x3 other) {
        Matrix3x3 temp = Matrix3x3.identity();
        temp.m00 = this.m00 + other.m00;
        temp.m10 = this.m10 + other.m10;
        temp.m20 = this.m20 + other.m20;

        temp.m01 = this.m01 + other.m01;
        temp.m11 = this.m11 + other.m11;
        temp.m21 = this.m21 + other.m21;

        temp.m02 = this.m02 + other.m02;
        temp.m12 = this.m12 + other.m12;
        temp.m22 = this.m22 + other.m22;
        
        return temp;
    }

    public Matrix3x3 multiply(float scalar) { 
        Matrix3x3 temp = new Matrix3x3();

        temp.m00 = this.m00 * scalar;
        temp.m10 = this.m10 * scalar;
        temp.m20 = this.m20 * scalar;

        temp.m01 = this.m01 * scalar;
        temp.m11 = this.m11 * scalar;
        temp.m21 = this.m21 * scalar;

        temp.m02 = this.m02 * scalar;
        temp.m12 = this.m12 * scalar;
        temp.m22 = this.m22 * scalar;

        return temp;
    }

    public Vector3 multiply(Vector3 vector) {
        float x = this.m00 * vector.x + this.m01 * vector.y + this.m02 * vector.z;
        float y = this.m10 * vector.x + this.m11 * vector.y + this.m12 * vector.z;
        float z = this.m20 * vector.x + this.m21 * vector.y + this.m22 * vector.z;

        return new Vector3(x, y, z);
    }

    public Matrix3x3 multiply(Matrix3x3 other) {
        Matrix3x3 temp = new Matrix3x3();

        temp.m00 = this.m00 * other.m00 + this.m01 * other.m10 + this.m02 * other.m20;
        temp.m10 = this.m10 * other.m00 + this.m11 * other.m10 + this.m12 * other.m20;
        temp.m20 = this.m20 * other.m00 + this.m21 * other.m10 + this.m22 * other.m20;

        temp.m01 = this.m00 * other.m01 + this.m01 * other.m11 + this.m02 * other.m21;
        temp.m11 = this.m10 * other.m01 + this.m11 * other.m11 + this.m12 * other.m21;
        temp.m21 = this.m20 * other.m01 + this.m21 * other.m11 + this.m22 * other.m21;

        temp.m02 = this.m00 * other.m02 + this.m01 * other.m12 + this.m02 * other.m22;
        temp.m12 = this.m10 * other.m02 + this.m11 * other.m12 + this.m12 * other.m22;
        temp.m22 = this.m20 * other.m02 + this.m21 * other.m12 + this.m22 * other.m22;

        return temp;
    }

    public Matrix3x3 negate() {
        return multiply(-1f);
    }

    public Matrix3x3 subtract(Matrix3x3 other) {
        return this.add(other.negate());
    }

    public Matrix3x3 transpose() {
        Matrix3x3 temp = new Matrix3x3();
        
        temp.m00 = this.m00;
        temp.m10 = this.m01;
        temp.m20 = this.m02;

        temp.m01 = this.m10;
        temp.m11 = this.m11;
        temp.m21 = this.m12;

        temp.m02 = this.m20;
        temp.m12 = this.m21;
        temp.m22 = this.m22;

        return temp;
    }

    public void addToBuffer(FloatBuffer buffer) {
        buffer.put(m00).put(m10).put(m20);
        buffer.put(m01).put(m11).put(m21);
        buffer.put(m02).put(m12).put(m22);
        buffer.flip();
    }

    public boolean equals(Object other) {
        if(other == null) return false;
        if(!(other instanceof Matrix3x3)) return false;
        Matrix3x3 otherMatrix = (Matrix3x3)other;

        if(otherMatrix.m00 != this.m00) return false; 
        if(otherMatrix.m10 != this.m10) return false;
        if(otherMatrix.m20 != this.m20) return false;

        if(otherMatrix.m01 != this.m01) return false;
        if(otherMatrix.m11 != this.m11) return false;
        if(otherMatrix.m21 != this.m21) return false;

        if(otherMatrix.m02 != this.m02) return false;
        if(otherMatrix.m12 != this.m12) return false;
        if(otherMatrix.m22 != this.m22) return false;
         
        return true;
    }

    private static Matrix3x3 identity() {
        return new Matrix3x3();
    }

    
    

    
    
}