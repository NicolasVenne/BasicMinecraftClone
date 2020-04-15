package computergraphics.graphics;

import static org.lwjgl.opengl.GL30.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * Shader
 * Used to load shader files and create opengl shader sources
 */
public class Shader {

    private final int id;

    /**
     * Shader contructor
     * @param type The type of shader, vertex / fragment
     */
    public Shader(int type) {
        id = glCreateShader(type);
    }
    
    
    /** 
     * Load a shader straight from source
     * @param source
     */
    public void loadSource(CharSequence source) {
        glShaderSource(id, source);
    }


    /**
     * Attemp to compile the shader
     */
    public void compile() {
        glCompileShader(id);

        checkStatus();
    }

    /**
     * Check if the source loaded properly
     */
    public void checkStatus() {
        int status = glGetShaderi(id, GL_COMPILE_STATUS);
        if(status != GL_TRUE) {
            throw new RuntimeException(glGetShaderInfoLog(id));
        }
    }

    
    /** 
     * Get the id of the shader
     * @return int
     */
    public int getID() {
        return id;
    }

    
    /** 
     * Static method to create a shader given a source
     * @param type
     * @param source
     * @return Shader
     */
    public static Shader createShader(int type, CharSequence source) {
        Shader shader = new Shader(type);
        shader.loadSource(source);
        shader.compile();

        return shader;
    }

    
    /** 
     * Static method to Create a shader from a file
     * @param type The type of shader, fragment / vertex
     * @param path Path to the file source
     * @return Shader
     */
    public static Shader createShaderFromFile(int type, String path) {
        StringBuilder builder = new StringBuilder();

        try (InputStream file = new FileInputStream(path);
                BufferedReader reader = new BufferedReader(new InputStreamReader(file))) {
            String line;
            while((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch(IOException e) {
            throw new RuntimeException("Failed to load shader file: " + System.lineSeparator() + e.getMessage());
        }
        
        CharSequence source = builder.toString();

        return createShader(type, source);
    }

    
    /**
     * Delete the shader
     */
	public void delete() {
        glDeleteShader(id);
	}
}