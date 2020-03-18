package computergraphics.graphics;

import org.joml.Matrix4f;

/**
 * StaticShader
 */
public class SkyboxShader extends ShaderProgram{

    private static final String VERTEX_SHADER_FILE = "src/main/java/computergraphics/graphics/shaders/skyboxVertexShader.glsl";
    private static final String FRAGMENT_SHADER_FILE = "src/main/java/computergraphics/graphics/shaders/skyboxFragmentShader.glsl";

    private int viewMatrixLocation;
    private int transformationMatrixLocation;
    private int projectionMatrixLocation;
    //private int textureSamplerLocation;
    //private int ambientLightLocation;

    public SkyboxShader() {
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
        viewMatrixLocation = super.getUniformLocation("viewMatrix");
        transformationMatrixLocation = super.getUniformLocation("transformationMatrix");
        projectionMatrixLocation = super.getUniformLocation("projectionMatrix");
        //textureSamplerLocation = super.getUniformLocation("textureSampler");
        //ambientLightLocation = super.getUniformLocation("ambientLightLocation");
    }

    public void loadViewMatrix(Matrix4f matrix) {
        super.setUniform(viewMatrixLocation, matrix);
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.setUniform(transformationMatrixLocation, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.setUniform(projectionMatrixLocation, matrix);
    } 

    // public void loadTextureSampler() {
    //     super.setUniform(textureSamplerLocation, 0);
    // }

    public void loadAmbientLight(Matrix4f matrix) {
        //super.setUniform(ambientLightLocation, 0);
    }
	

    
    
}