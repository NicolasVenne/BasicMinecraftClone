package computergraphics.graphics;

import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * VertexBuffer
 */
public class VertexBuffer {

    private final int id;
    private final int target;

    public VertexBuffer(int target) {
        id = glGenBuffers();
        this.target = target; 
    }

    public void bind() {
        glBindBuffer(target, id);
    }

    public void uploadData(long size, int usage) {
        glBufferData(target, size, usage);  
    }

    public void uploadData(FloatBuffer data, int usage) {
        glBufferData(target, data, usage);
    }

    public void uploadSubData(long offset, FloatBuffer data) {
        glBufferSubData(target, offset, data);
    }

    public void uploadSubData(IntBuffer data, int usage) {
        glBufferData(target, data, usage);
    }

    public void delete() {
        glDeleteBuffers(id);
    }

    public int getID() {
        return id;
    }

}