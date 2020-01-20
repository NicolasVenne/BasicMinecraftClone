package computergraphics.graphics;

import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryStack;

import computergraphics.math.Matrix2x2;
import computergraphics.math.Matrix3x3;
import computergraphics.math.Matrix4x4;
import computergraphics.math.Vector2;
import computergraphics.math.Vector3;
import computergraphics.math.Vector4;

/**
 * ShaderProgram
 */
public abstract class ShaderProgram {

    private final int id;
    private Shader vertexShader;
    private Shader fragmentShader;

    public ShaderProgram(String vertexShaderFile, String fragmentShaderFile) {
        id = glCreateProgram();
        vertexShader = Shader.createShaderFromFile(GL_VERTEX_SHADER, vertexShaderFile);
        fragmentShader = Shader.createShaderFromFile(GL_FRAGMENT_SHADER, fragmentShaderFile);
        attachShader(vertexShader);
        attachShader(fragmentShader);
        bindFragmentDataLocation(0, "fragColor");
        bindAttributes();
        link();
        glValidateProgram(id);
        getAllUniformLocations();
    }

    protected abstract void getAllUniformLocations();

    public void start() {
        glUseProgram(id);
    }

    public void stop() {
        glUseProgram(0);
    }

    public void dispose() {
        stop();
        detachShader(vertexShader);
        detachShader(fragmentShader);
        vertexShader.delete();
        fragmentShader.delete();
        delete();
    }

    protected abstract void bindAttributes();

    public abstract int getAttributeCount();

    public void attachShader(Shader shader) {
        glAttachShader(id, shader.getID());
    }

    public void detachShader(Shader shader) {
        glDetachShader(id, shader.getID());
    }

    public void bindAttribute(int attribute, String varName) {
        glBindAttribLocation(id, attribute, varName);
    }

    public void bindFragmentDataLocation(int num, CharSequence name) {
        glBindFragDataLocation(id, num, name);
    }

    public void link() {
        glLinkProgram(id);

        checkStatus();
    }

    public void checkStatus() {
        int status = glGetProgrami(id, GL_LINK_STATUS);
        if(status != GL_TRUE) {
            throw new RuntimeException(glGetProgramInfoLog(id));
        }
    }

    public int getAttributeLocation(CharSequence name) {
        return glGetAttribLocation(id, name);
    }

    public void enableVertexAttribute(int index) {
        glEnableVertexAttribArray(index);
    }

    public void disableVertexAttribute(int index) {
        glDisableVertexAttribArray(index);
    }

    public void pointVertexAttribute(int index, int size, int stride, int offset) {
        glVertexAttribPointer(index, size, GL_FLOAT, false, stride, offset);
    }

    public int getUniformLocation(CharSequence name) {
        return glGetUniformLocation(id, name);
    }

    public void setUniform(int location, int value) {
        glUniform1i(location, value);
    }

    public void setUniform(int location, Vector2 value) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(Vector2.FLOAT_SIZE);
            value.addToBuffer(buffer);
            glUniform2fv(location, buffer);
        }
    }

    public void setUniform(int location, Vector3 value) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(Vector3.FLOAT_SIZE);
            value.addToBuffer(buffer);
            glUniform3fv(location, buffer);
        }
    }

    public void setUniform(int location, Vector4 value) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(Vector4.FLOAT_SIZE);
            value.addToBuffer(buffer);
            glUniform4fv(location, buffer);
        }
    }

    public void setUniform(int location, Matrix2x2 value) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(Matrix2x2.FLOAT_SIZE);
            value.addToBuffer(buffer);
            glUniformMatrix2fv(location, false, buffer);
        }
    }

    public void setUniform(int location, Matrix3x3 value) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(Matrix3x3.FLOAT_SIZE);
            value.addToBuffer(buffer);
            glUniformMatrix3fv(location, false, buffer);
        }
    }

    public void setUniform(int location, Matrix4x4 value) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(Matrix4x4.FLOAT_SIZE);
            value.addToBuffer(buffer);
            glUniformMatrix4fv(location, false, buffer);
        }
    }



    public void delete() {
        glDeleteProgram(id);
    }

}