package computergraphics.graphics;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL30.*;

import computergraphics.math.Utils;
import computergraphics.models.*;
import de.matthiasmann.twl.utils.PNGDecoder;

/**
 * Loader
 * Used to create VAO's for models
 */
public class  Loader {

    private static List<Integer> vaos = new ArrayList<Integer>();
    private static List<Integer> vbos = new ArrayList<Integer>();
    private static List<Integer> textures = new ArrayList<Integer>();

    
    /** 
     * Create a basic model given the vertexs and indicies
     * @param positions The vertices
     * @param indicies The indicies
     * @return Model
     */
    public static Model createModel(Vector3f[] positions, int[] indicies) {
        int vaoID = generateVAO();
        addIndicies(indicies);
        addDataToAttribute(0, Utils.convertDataToFloatArray(positions), 3);
        glBindVertexArray(0);
        return new Model(vaoID, indicies.length);
    }

    
    /** 
     * Create a textured model
     * @param positions The vertices
     * @param uv The uv coordinates
     * @param indicies The indicies
     * @return TexturedModel
     */
    public static TexturedModel createTexturedModel(Vector3f[] positions, Vector2f[] uv, int[] indicies) {
        int vaoID = generateVAO();
        addIndicies(indicies);
        addDataToAttribute(0, Utils.convertDataToFloatArray(positions), 3);
        addDataToAttribute(1, Utils.convertDataToFloatArray(uv), 2);
        glBindVertexArray(0);
        return new TexturedModel(vaoID, indicies.length);
    }


    
    /** 
     * Create a textured model using float arrays
     * @param positions The vertices as float array
     * @param uv The uv coordinates as float array
     * @param indicies The indicies 
     * @return TexturedModel
     */
    public static TexturedModel createTexturedModel(float[] positions, float[] uv, int[] indicies) {
        int vaoID = generateVAO();
        addIndicies(indicies);
        addDataToAttribute(0, positions, 3);
        addDataToAttribute(1, uv, 2);
        glBindVertexArray(0);
        return new TexturedModel(vaoID, indicies.length);
    }


    
    /** 
     * Create a material model for block faces
     * @param positions The vertices
     * @param uv THe uv coordinates
     * @param normals the normals
     * @param faceLength Number of verticies in a block face
     * @return MaterialModel
     */
    public static MaterialModel createMaterialModel(Vector3f[] positions, Vector2f[] uv, Vector3f[] normals, int faceLength) {

        int vaoID = generateVAO();
        addDataToAttribute(0, Utils.convertDataToFloatArray(positions), 3);
        addDataToAttribute(1, Utils.convertDataToFloatArray(uv), 2);
        addDataToAttribute(2, Utils.convertDataToFloatArray(normals), 3);
        glBindVertexArray(0);
        return new MaterialModel(vaoID, faceLength);
    }

    
    /** 
     * Create a material model
     * @param positions The vertices
     * @param uv the uv coordinates
     * @param normals the normals
     * @param indicies the indicies
     * @return MaterialModel
     */
    public static MaterialModel createMaterialModel(float[] positions, float[] uv, float[] normals, int[] indicies) {

        int vaoID = generateVAO();
        addIndicies(indicies);
        addDataToAttribute(0, positions, 3);
        addDataToAttribute(1, uv, 2);
        addDataToAttribute(2, normals, 3);
        glBindVertexArray(0);
        return new MaterialModel(vaoID, indicies.length);
    }


    
    /** 
     * Load a texture given a image file path
     * @param file Image file path
     * @return Texture2D
     */
    public static Texture2D loadTexture(String file) {
        PNGDecoder decoder = null;
        ByteBuffer buffer = null;
        try {
            decoder = new PNGDecoder(new FileInputStream("res/" + file + ".png"));
            buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        buffer.flip();

        int id = glGenTextures();
        textures.add(id);


        glBindTexture(GL_TEXTURE_2D, id);

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        glGenerateMipmap(GL_TEXTURE_2D);

        return new Texture2D(id);
    }

    /**
     * Clear all VAO's and VBO's
     */
    public static void dispose() {
        for (int vao : vaos) {
			glDeleteVertexArrays(vao);
		}
		for (int vbo : vbos) {
			glDeleteBuffers(vbo);
        }
        for(int texture : textures) {
            glDeleteTextures(texture);
        }
    }

    
    /** 
     * Load data to a VBO
     * @param index index of VBO
     * @param data data to load
     * @param size size of data
     */
    private static void addDataToAttribute(int index, float[] data, int size) {
        int vbo = glGenBuffers();
        vbos.add(vbo);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        FloatBuffer buffer = convertToFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    
    /** 
     * Add the indicies to the current VAO
     * @param indicies
     */
    private static void addIndicies(int[] indicies) {
        int vbo = glGenBuffers();
        vbos.add(vbo);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo);
        IntBuffer buffer = convertToIntBuffer(indicies);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    }

    
    /** 
     * Convert int array to buffer
     * @param data THe int array
     * @return IntBuffer
     */
    public static IntBuffer convertToIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    
    /** 
     * Conver float array to buffer
     * @param data THe float array
     * @return FloatBuffer
     */
    private static FloatBuffer convertToFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    
    /** 
     * Create a VAO
     * @return int
     */
    private static int generateVAO() {
        int vaoID = glGenVertexArrays();
        vaos.add(vaoID);
        glBindVertexArray(vaoID);
        return vaoID;
    }

	

    
}