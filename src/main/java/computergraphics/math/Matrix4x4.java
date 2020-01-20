package computergraphics.math;

import java.nio.FloatBuffer;

import computergraphics.entities.Camera;
import computergraphics.graphics.Window;

/**
 * Matrix4x4
 */
public class Matrix4x4 {

    public static final int FLOAT_SIZE = 16;


    private float m00, m01, m02, m03;
    private float m10, m11, m12, m13;
    private float m20, m21, m22, m23;
    private float m30, m31, m32, m33;

    public Matrix4x4(Vector4 col1, Vector4 col2, Vector4 col3, Vector4 col4) {
        m00 = col1.x;
        m10 = col1.y;
        m20 = col1.z;
        m30 = col1.w;
        
        m01 = col2.x;
        m11 = col2.y;
        m21 = col2.z;
        m31 = col2.w;

        m02 = col3.x;
        m12 = col3.y;
        m22 = col3.z;
        m32 = col3.w;

        m03 = col4.x;
        m13 = col4.y;
        m23 = col4.z;
        m33 = col4.w;

    }

    public Matrix4x4() {
        m00 = 1f;
        m10 = 0f;
        m20 = 0f;
        m30 = 0f;

        m01 = 0f;
        m11 = 1f;
        m21 = 0f;
        m31 = 0f;

        m02 = 0f;
        m12 = 0f;
        m22 = 1f;
        m32 = 0f;

        m03 = 0f;
        m13 = 0f;
        m23 = 0f;
        m33 = 1f;
    }

    public void set(Matrix4x4 other) {
        this.m00 = other.m00;
        this.m01 = other.m01;
        this.m02 = other.m02;
        this.m03 = other.m03;

        this.m10 = other.m10;
        this.m11 = other.m11;
        this.m12 = other.m12;
        this.m13 = other.m13;

        this.m20 = other.m20;
        this.m21 = other.m21;
        this.m22 = other.m22;
        this.m23 = other.m23;

        this.m30 = other.m30;
        this.m31 = other.m31;
        this.m32 = other.m32;
        this.m33 = other.m33;
    }

    public Matrix4x4 add(Matrix4x4 other) {
        Matrix4x4 temp = Matrix4x4.identity();
        temp.m00 = this.m00 + other.m00;
        temp.m10 = this.m10 + other.m10;
        temp.m20 = this.m20 + other.m20;
        temp.m30 = this.m30 + other.m30;

        temp.m01 = this.m01 + other.m01;
        temp.m11 = this.m11 + other.m11;
        temp.m21 = this.m21 + other.m21;
        temp.m31 = this.m31 + other.m31;

        temp.m02 = this.m02 + other.m02;
        temp.m12 = this.m12 + other.m12;
        temp.m22 = this.m22 + other.m22;
        temp.m32 = this.m32 + other.m32;

        temp.m03 = this.m03 + other.m03;
        temp.m13 = this.m13 + other.m13;
        temp.m23 = this.m23 + other.m23;
        temp.m33 = this.m33 + other.m33;
        
        return temp;
    }

    public Matrix4x4 multiply(float scalar) { 
        Matrix4x4 temp = new Matrix4x4();

        temp.m00 = this.m00 * scalar;
        temp.m10 = this.m10 * scalar;
        temp.m20 = this.m20 * scalar;
        temp.m30 = this.m30 * scalar;

        temp.m01 = this.m01 * scalar;
        temp.m11 = this.m11 * scalar;
        temp.m21 = this.m21 * scalar;
        temp.m31 = this.m31 * scalar;

        temp.m02 = this.m02 * scalar;
        temp.m12 = this.m12 * scalar;
        temp.m22 = this.m22 * scalar;
        temp.m32 = this.m32 * scalar;

        temp.m03 = this.m03 * scalar;
        temp.m13 = this.m13 * scalar;
        temp.m23 = this.m23 * scalar;
        temp.m33 = this.m33 * scalar;

        return temp;
    }

    public Vector4 multiply(Vector4 vector) {
        float x = this.m00 * vector.x + this.m01 * vector.y + this.m02 * vector.z + this.m03 * vector.w;
        float y = this.m10 * vector.x + this.m11 * vector.y + this.m12 * vector.z + this.m13 * vector.w;
        float z = this.m20 * vector.x + this.m21 * vector.y + this.m22 * vector.z + this.m23 * vector.w;
        float w = this.m30 * vector.x + this.m31 * vector.y + this.m32 * vector.z + this.m33 * vector.w;

        return new Vector4(x, y, z, w);
    }

    public Matrix4x4 multiply(Matrix4x4 other) {
        Matrix4x4 result = new Matrix4x4();
        result.m00 = this.m00 * other.m00 + this.m01 * other.m10 + this.m02 * other.m20 + this.m03 * other.m30;
        result.m10 = this.m10 * other.m00 + this.m11 * other.m10 + this.m12 * other.m20 + this.m13 * other.m30;
        result.m20 = this.m20 * other.m00 + this.m21 * other.m10 + this.m22 * other.m20 + this.m23 * other.m30;
        result.m30 = this.m30 * other.m00 + this.m31 * other.m10 + this.m32 * other.m20 + this.m33 * other.m30;

        result.m01 = this.m00 * other.m01 + this.m01 * other.m11 + this.m02 * other.m21 + this.m03 * other.m31;
        result.m11 = this.m10 * other.m01 + this.m11 * other.m11 + this.m12 * other.m21 + this.m13 * other.m31;
        result.m21 = this.m20 * other.m01 + this.m21 * other.m11 + this.m22 * other.m21 + this.m23 * other.m31;
        result.m31 = this.m30 * other.m01 + this.m31 * other.m11 + this.m32 * other.m21 + this.m33 * other.m31;

        result.m02 = this.m00 * other.m02 + this.m01 * other.m12 + this.m02 * other.m22 + this.m03 * other.m32;
        result.m12 = this.m10 * other.m02 + this.m11 * other.m12 + this.m12 * other.m22 + this.m13 * other.m32;
        result.m22 = this.m20 * other.m02 + this.m21 * other.m12 + this.m22 * other.m22 + this.m23 * other.m32;
        result.m32 = this.m30 * other.m02 + this.m31 * other.m12 + this.m32 * other.m22 + this.m33 * other.m32;

        result.m03 = this.m00 * other.m03 + this.m01 * other.m13 + this.m02 * other.m23 + this.m03 * other.m33;
        result.m13 = this.m10 * other.m03 + this.m11 * other.m13 + this.m12 * other.m23 + this.m13 * other.m33;
        result.m23 = this.m20 * other.m03 + this.m21 * other.m13 + this.m22 * other.m23 + this.m23 * other.m33;
        result.m33 = this.m30 * other.m03 + this.m31 * other.m13 + this.m32 * other.m23 + this.m33 * other.m33;

        return result;
    }

    public Matrix4x4 negate() {
        return multiply(-1f);
    }

    public Matrix4x4 subtract(Matrix4x4 other) {
        return this.add(other.negate());
    }

    public Matrix4x4 transpose() {
        Matrix4x4 temp = new Matrix4x4();
        
        temp.m00 = this.m00;
        temp.m10 = this.m01;
        temp.m20 = this.m02;
        temp.m30 = this.m03;

        temp.m01 = this.m10;
        temp.m11 = this.m11;
        temp.m21 = this.m12;
        temp.m31 = this.m13;

        temp.m02 = this.m20;
        temp.m12 = this.m21;
        temp.m22 = this.m22;
        temp.m23 = this.m23;

        temp.m03 = this.m30;
        temp.m13 = this.m31;
        temp.m23 = this.m32;
        temp.m33 = this.m33;

        return temp;
    }

    public void addToBuffer(FloatBuffer buffer) {
        buffer.put(m00).put(m10).put(m20).put(m30);
        buffer.put(m01).put(m11).put(m21).put(m31);
        buffer.put(m02).put(m12).put(m22).put(m32);
        buffer.put(m03).put(m13).put(m23).put(m33);
        buffer.flip();
    }

    public void rotate(float angle, Vector3 axis) {
        Matrix4x4 deltaMatrix = Matrix4x4.rotate(angle, axis.x, axis.y, axis.z);
        set(multiply(deltaMatrix));
    }     

    public void scale(Vector3 by) {
        Matrix4x4 deltaMatrix = Matrix4x4.scale(by.x, by.y, by.z);
        set(multiply(deltaMatrix));
    }

    public void translate(Vector3 to) {
        Matrix4x4 deltaMatrix = new Matrix4x4();
        deltaMatrix.m03 = to.x;
        deltaMatrix.m13 = to.y;
        deltaMatrix.m23 = to.z;
        set(multiply(deltaMatrix));
    }

    public static Matrix4x4 view(Camera camera) {
        Transform transform = camera.transform();
        Matrix4x4 view = new Matrix4x4();
        view.rotate(transform.rotation().x, new Vector3(1f,0,0));
        view.rotate(transform.rotation().y, new Vector3(0,1f,0));
        view.rotate(transform.rotation().z, new Vector3(0,0,1f));
        view.translate(transform.position().negate());
        return view;

    }

    public static Matrix4x4 orthographic(float left, float right, float bottom, float top, float back, float forward) {
        Matrix4x4 orthographic = new Matrix4x4();

        float tx = -(right + left) / (right - left);
        float ty = -(top + bottom) / (top - bottom);
        float tz = -(forward + back) / (forward - back);

        orthographic.m00 = 2f / (right - left);
        orthographic.m11 = 2f / (top - bottom);
        orthographic.m22 = -2f / (forward - back);
        orthographic.m03 = tx;
        orthographic.m13 = ty;
        orthographic.m23 = tz;

        return orthographic;
    }

    public static Matrix4x4 frustum(float left, float right, float bottom, float top, float near, float forward, float back) {
        Matrix4x4 frustum = new Matrix4x4();

        float x = (right + left) / (right - left);
        float y = (top + bottom) / (top - bottom);
        float z = -(forward + back) / (forward - back);
        float w = -(2f * forward * back) / (forward - back);

        frustum.m00 = (2f * back) / (right - left);
        frustum.m11 = (2f * back) / (top - bottom);
        frustum.m02 = x;
        frustum.m12 = y;
        frustum.m22 = z;
        frustum.m32 = -1f;
        frustum.m23 = w;
        frustum.m33 = 0f;

        return frustum;
    }

    public static Matrix4x4 perspective(float fovy, float aspect, float near, float far) {
        Matrix4x4 perspective = new Matrix4x4();

        float f = (float) (1f / Math.tan(Math.toRadians(fovy) / 2f));

        perspective.m00 = f / aspect;
        perspective.m11 = f;
        perspective.m22 = (far + near) / (near - far);
        perspective.m32 = -1f;
        perspective.m23 = (2f * far * near) / (near - far);
        perspective.m33 = 0f;

        return perspective;
    }

    public static Matrix4x4 rotate(float angle, float x, float y, float z) {
        Matrix4x4 rotation = new Matrix4x4();

        float c = (float) Math.cos(Math.toRadians(angle));
        float s = (float) Math.sin(Math.toRadians(angle));

        Vector3 vec = new Vector3(x, y, z);

        if(vec.magnitude() != 1f) {
            vec = vec.normalize();
            x = vec.x;
            y = vec.y;
            z = vec.z;
        }

        rotation.m00 = x * x * (1f - c) + c;
        rotation.m10 = y * x * (1f - c) + z * s;
        rotation.m20 = x * z * (1f - c) - y * s;
        rotation.m01 = x * y * (1f - c) - z * s;
        rotation.m11 = y * y * (1f - c) + c;
        rotation.m21 = y * z * (1f - c) + x * s;
        rotation.m02 = x * z * (1f - c) + y * s;
        rotation.m12 = y * z * (1f - c) - x * s;
        rotation.m22 = z * z * (1f - c) + c;
        
        return rotation;
    }

    public static Matrix4x4 scale(float x, float y, float z) {
        Matrix4x4 scaling = new Matrix4x4();

        scaling.m00 = x;
        scaling.m11 = y;
        scaling.m22 = z;

        return scaling;
    }

    public boolean equals(Object other) {
        if(other == null) return false;
        if(!(other instanceof Matrix4x4)) return false;
        Matrix4x4 otherMatrix = (Matrix4x4)other;

        if(otherMatrix.m00 != this.m00) return false; 
        if(otherMatrix.m10 != this.m10) return false;
        if(otherMatrix.m20 != this.m20) return false;
        if(otherMatrix.m30 != this.m30) return false;

        if(otherMatrix.m01 != this.m01) return false;
        if(otherMatrix.m11 != this.m11) return false;
        if(otherMatrix.m21 != this.m21) return false;
        if(otherMatrix.m31 != this.m31) return false;

        if(otherMatrix.m02 != this.m02) return false;
        if(otherMatrix.m12 != this.m12) return false;
        if(otherMatrix.m22 != this.m22) return false;
        if(otherMatrix.m32 != this.m32) return false;

        if(otherMatrix.m03 != this.m03) return false;
        if(otherMatrix.m13 != this.m13) return false;
        if(otherMatrix.m23 != this.m23) return false;
        if(otherMatrix.m33 != this.m33) return false;
         
        return true;
    }

    private static Matrix4x4 identity() {
        return new Matrix4x4();
    }

    
    

    
    
}