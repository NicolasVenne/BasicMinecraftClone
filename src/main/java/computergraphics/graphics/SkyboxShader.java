package computergraphics.graphics;

import org.joml.Matrix4f;

/**
 * SkyboxShader
 * The shader used for the skybox
 */
public class SkyboxShader extends ShaderProgram{

    private static final String VERTEX_SHADER_FILE = "shaders/skyboxVertexShader.glsl";
    private static final String FRAGMENT_SHADER_FILE = "shaders/skyboxFragmentShader.glsl";

    private int viewMatrixLocation;
    private int transformationMatrixLocation;
    private int projectionMatrixLocation;

    public SkyboxShader() {
        super(VERTEX_SHADER_FILE, FRAGMENT_SHADER_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "uv");
    }

    
    /** 
     * @return int
     */
    @Override
    public int getAttributeCount() {
        return 2;
    }

    @Override
    protected void getAllUniformLocations() {
        viewMatrixLocation = super.getUniformLocation("viewMatrix");
        transformationMatrixLocation = super.getUniformLocation("transformationMatrix");
        projectionMatrixLocation = super.getUniformLocation("projectionMatrix");
    }

    
    /** 
     * Load the view matrix into the shader
     * @param matrix the view matrix
     */
    public void loadViewMatrix(Matrix4f matrix) {
        super.setUniform(viewMatrixLocation, matrix);
    }

    
    /** 
     * Load the transformation matrix into the shader
     * @param matrix the transformation matrix
     */
    public void loadTransformationMatrix(Matrix4f matrix) {
        super.setUniform(transformationMatrixLocation, matrix);
    }

    
    /** 
     * load the projection matrix into the shader
     * @param matrix the projection matrix
     */
    public void loadProjectionMatrix(Matrix4f matrix) {
        super.setUniform(projectionMatrixLocation, matrix);
    } 


	

    
    
}