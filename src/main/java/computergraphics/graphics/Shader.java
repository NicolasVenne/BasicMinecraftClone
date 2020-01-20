package computergraphics.graphics;

import static org.lwjgl.opengl.GL30.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * Shader
 */
public class Shader {

    private final int id;

    public Shader(int type) {
        id = glCreateShader(type);
    }
    
    public void loadSource(CharSequence source) {
        glShaderSource(id, source);
    }

    public void compile() {
        glCompileShader(id);

        checkStatus();
    }

    public void checkStatus() {
        int status = glGetShaderi(id, GL_COMPILE_STATUS);
        if(status != GL_TRUE) {
            throw new RuntimeException(glGetShaderInfoLog(id));
        }
    }

    public int getID() {
        return id;
    }

    public static Shader createShader(int type, CharSequence source) {
        Shader shader = new Shader(type);
        shader.loadSource(source);
        shader.compile();

        return shader;
    }

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

	public void delete() {
        glDeleteShader(id);
	}
}