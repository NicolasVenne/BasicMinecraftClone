package computergraphics.graphics;

import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryStack;

import org.joml.Matrix4f;
import org.joml.Matrix3f;
import org.joml.Matrix2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.joml.Vector2f;
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

    public void setUniform(int location, Vector2f value) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(2);
            value.get(buffer);
            glUniform2fv(location, buffer);
        }
    }

    public void setUniform(int location, Vector3f value) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(3);
            value.get(buffer);
            glUniform3fv(location, buffer);
        }
    }

    public void setUniform(int location, Vector4f value) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(4);
            value.get(buffer);
            glUniform4fv(location, buffer);
        }
    }

    public void setUniform(int location, Matrix2f value) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(4);
            value.get(buffer);
            glUniformMatrix2fv(location, false, buffer);
        }
    }

    public void setUniform(int location, Matrix3f value) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(9);
            value.get(buffer);
            glUniformMatrix3fv(location, false, buffer);
        }
    }

    public void setUniform(int location, Matrix4f value) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(16);
            value.get(buffer);
            glUniformMatrix4fv(location, false, buffer);
        }
    }



    public void delete() {
        glDeleteProgram(id);
    }

}