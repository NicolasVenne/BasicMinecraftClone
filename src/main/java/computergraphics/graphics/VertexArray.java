package computergraphics.graphics;

import static org.lwjgl.opengl.GL30.*;

/**
 * VertexArray
 */
public class VertexArray {

    private static int currentBoundedArray = 0;

    private final int id;

    public VertexArray() {
        id = glGenVertexArrays();
    }

    public void bind() {
        glBindVertexArray(id);
        currentBoundedArray = id;
    }

    public boolean checkBound() {
        if(currentBoundedArray == id) {
            return true;
        } else {
            throw new IllegalStateException("Accessing array when its not bound");
        }
    }

    public void attachIndices(VertexBuffer indices) {
        if(checkBound()) {
            indices.bind();
        }
    }

    public void delete() {
        glDeleteVertexArrays(id);
    }

    public int getID() {
        return id;
    }

    public static void unbind() {
        currentBoundedArray = 0;
        glBindVertexArray(0);
    }
    
}