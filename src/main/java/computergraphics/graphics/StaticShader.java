package computergraphics.graphics;

import org.joml.Matrix4f;

/**
 * StaticShader
 */
public class StaticShader extends ShaderProgram{

    private static final String VERTEX_SHADER_FILE = "src/main/java/computergraphics/graphics/shaders/vertexShader.glsl";
    private static final String FRAGMENT_SHADER_FILE = "src/main/java/computergraphics/graphics/shaders/fragmentShader.glsl";

    private int transformMatrixLocation;
    private int projectionMatrixLocation;
    private int viewMatrixLocation;

    public StaticShader() {
        super(VERTEX_SHADER_FILE, FRAGMENT_SHADER_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "uv");
    }

    @Override
    public int getAttributeCount() {
        return 2;
    }

    @Override
    protected void getAllUniformLocations() {
        transformMatrixLocation = super.getUniformLocation("transformationMatrix");
        projectionMatrixLocation = super.getUniformLocation("projectionMatrix");
        viewMatrixLocation = super.getUniformLocation("viewMatrix");
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