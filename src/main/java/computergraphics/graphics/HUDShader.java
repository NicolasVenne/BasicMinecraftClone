package computergraphics.graphics;

import org.joml.Matrix4f;
import org.joml.Vector4f;

/**
 * StaticShader
 */
public class HUDShader extends ShaderProgram{

    private static final String VERTEX_SHADER_FILE = "shaders/vertexHUDShader.glsl";
    private static final String FRAGMENT_SHADER_FILE = "shaders/fragmentHUDShader.glsl";

    private int transformMatrixLocation;
    private int projectionMatrixLocation;
    private int viewMatrixLocation;
    private int colorLocation;

    public HUDShader() {
        super(VERTEX_SHADER_FILE, FRAGMENT_SHADER_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

    @Override
    public int getAttributeCount() {
        return 1;
    }

    @Override
    protected void getAllUniformLocations() {
        transformMatrixLocation = super.getUniformLocation("transformationMatrix");
        projectionMatrixLocation = super.getUniformLocation("projectionMatrix");
        viewMatrixLocation = super.getUniformLocation("viewMatrix");
        colorLocation = super.getUniformLocation("color");
    }

    public void setColor(Vector4f color) {
        super.setUniform(colorLocation, color);
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.setUniform(transformMatrixLocation, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.setUniform(projectionMatrixLocation, matrix);
    } 

    public void loadViewMatrix(Matrix4f matrix) {
        super.setUniform(viewMatrixLocation, matrix);
    }
	

    
    
}