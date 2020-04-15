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

/**
 * ShaderProgram
 * Abstract class to create attributes and uniforms for a shader
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

    
    /** 
     * Override method to load all uniform locations
     */
    protected abstract void getAllUniformLocations();

    /**
     * Start using this shader
     */
    public void start() {
        glUseProgram(id);
    }

    /**
     * Stop using this shader
     */
    public void stop() {
        glUseProgram(0);
    }

    /**
     * Clean and remove this shader
     */
    public void dispose() {
        stop();
        detachShader(vertexShader);
        detachShader(fragmentShader);
        vertexShader.delete();
        fragmentShader.delete();
        delete();
    }

    
    /** 
     * Override to bind the attribute of extended class to shader
     */
    protected abstract void bindAttributes();

    
    /** 
     * Override to return the number of attributes binded
     */
    public abstract int getAttributeCount();

    
    /** 
     * Attach a shader
     * @param shader
     */
    public void attachShader(Shader shader) {
        glAttachShader(id, shader.getID());
    }

    
    /** 
     * Detach a shader
     * @param shader
     */
    public void detachShader(Shader shader) {
        glDetachShader(id, shader.getID());
    }

    
    /** 
     * Bind a attribute given an id and its name
     * @param attribute The attribute id
     * @param varName The attribute name
     */
    public void bindAttribute(int attribute, String varName) {
        glBindAttribLocation(id, attribute, varName);
    }

    

    public void bindFragmentDataLocation(int num, CharSequence name) {
        glBindFragDataLocation(id, num, name);
    }

    /**
     * Link the shader program
     */
    public void link() {
        glLinkProgram(id);

        checkStatus();
    }

    /**
     * Check if shader program is OK
     */
    public void checkStatus() {
        int status = glGetProgrami(id, GL_LINK_STATUS);
        if(status != GL_TRUE) {
            throw new RuntimeException(glGetProgramInfoLog(id));
        }
    }

    
    /** 
     * Get the ID of a attribute
     * @param name The name of the attribute
     * @return int
     */
    public int getAttributeLocation(CharSequence name) {
        return glGetAttribLocation(id, name);
    }

    
    /** 
     * Enable the attribute
     * @param index The id of the attribute
     */
    public void enableVertexAttribute(int index) {
        glEnableVertexAttribArray(index);
    }

    
    /** 
     * Disable the attribute
     * @param index The id of the attribute
     */
    public void disableVertexAttribute(int index) {
        glDisableVertexAttribArray(index);
    }

    
    /** 
     * Point the attribute to the shader
     * @param index index of the attribute
     * @param size size of the data
     * @param stride stride of the data
     * @param offset start offset of the data
     */
    public void pointVertexAttribute(int index, int size, int stride, int offset) {
        glVertexAttribPointer(index, size, GL_FLOAT, false, stride, offset);
    }

    
    /** 
     * Get a uniforms location
     * @param name The name of the uniform 
     * @return int
     */
    public int getUniformLocation(CharSequence name) {
        return glGetUniformLocation(id, name);
    }

    
    /** 
     * Set a uniform
     * @param location id of the uniform location
     * @param value int
     */
    public void setUniform(int location, int value) {
        glUniform1i(location, value);
    }

    
    /** 
     * Set a uniform
     * @param location id of the uniform location
     * @param value boolean
     */
    public void setUniform(int location, boolean value) {
        glUniform1i(location, value ? 1 : 0);
    }

    
    /** 
     * Set a uniform
     * @param location id of the uniform location
     * @param value float
     */
    public void setUniform(int location, float value) {
        glUniform1f(location, value);
    }

    
    /** 
     * Set a uniform
     * @param location id of the uniform location
     * @param value Vector2f
     */
    public void setUniform(int location, Vector2f value) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(2);
            value.get(buffer);
            glUniform2fv(location, buffer);
        }
    }

    
    /** 
     * Set a uniform
     * @param location id of the uniform location
     * @param value Vector3f
     */
    public void setUniform(int location, Vector3f value) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(3);
            value.get(buffer);
            glUniform3fv(location, buffer);
        }
    }

    
    /** 
     * Set a uniform
     * @param location id of the uniform location
     * @param value Vector4f
     */
    public void setUniform(int location, Vector4f value) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(4);
            value.get(buffer);
            glUniform4fv(location, buffer);
        }
    }

    
    /** 
     * Set a uniform
     * @param location id of the uniform location
     * @param value Matrix2f
     */
    public void setUniform(int location, Matrix2f value) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(4);
            value.get(buffer);
            glUniformMatrix2fv(location, false, buffer);
        }
    }

    
    /** 
     * Set a uniform
     * @param location id of the uniform location
     * @param value Matrix3f
     */
    public void setUniform(int location, Matrix3f value) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(9);
            value.get(buffer);
            glUniformMatrix3fv(location, false, buffer);
        }
    }

    
    /** 
     * Set a uniform
     * @param location id of the uniform location
     * @param value Matrix4f
     */
    public void setUniform(int location, Matrix4f value) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(16);
            value.get(buffer);
            glUniformMatrix4fv(location, false, buffer);
        }
    }


    /**
     * Delete this shader program
     */
    public void delete() {
        glDeleteProgram(id);
    }

}