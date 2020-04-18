package computergraphics.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * StaticShader
 * The main shader for the game
 */
public class StaticShader extends ShaderProgram{

    private static final String VERTEX_SHADER_FILE = "shaders/vertexShader.glsl";
    private static final String FRAGMENT_SHADER_FILE = "shaders/fragmentShader.glsl";

    private int transformMatrixLocation;
    private int projectionMatrixLocation;
    private int viewMatrixLocation;
    private int pointLightColor;
    private int pointLightPosition;
    private int pointLightIntensity;
    private int pointLightAttConstant;
    private int pointLightAttLinear;
    private int pointLightAttExponent;
    private int matAmbient;
    private int matDiffuse;
    private int matSpecular;
    private int matHasTexture;
    private int matReflectance;
    private int specularPower;
    private int ambientLight;
    private int directLightColor;
    private int directLightDirection;
    private int directLightIntensity;
    private int selectedBlock;


    public StaticShader() {
        super(VERTEX_SHADER_FILE, FRAGMENT_SHADER_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "uv");
        super.bindAttribute(2, "vertexNormal");
    }

    
    /** 
     * Override to return the number of attributes
     * @return int 3 attributes
     */
    @Override
    public int getAttributeCount() {
        return 3;
    }

    /**
     * Create all uniform locations used in the shader
     */
    @Override
    protected void getAllUniformLocations() {
        transformMatrixLocation = super.getUniformLocation("transformationMatrix");
        projectionMatrixLocation = super.getUniformLocation("projectionMatrix");
        viewMatrixLocation = super.getUniformLocation("viewMatrix");
        pointLightColor = super.getUniformLocation("pointLight.colour");
        pointLightPosition = super.getUniformLocation("pointLight.position");
        pointLightIntensity= super.getUniformLocation("pointLight.intensity");
        pointLightAttConstant = super.getUniformLocation("pointLight.att.constant");
        pointLightAttLinear = super.getUniformLocation("pointLight.att.linear");
        pointLightAttExponent = super.getUniformLocation("pointLight.att.exponent");
        matAmbient = super.getUniformLocation("material.ambient");
        matDiffuse = super.getUniformLocation("material.diffuse");
        matSpecular = super.getUniformLocation("material.specular");
        matHasTexture = super.getUniformLocation("material.hasTexture");
        matReflectance = super.getUniformLocation("material.reflectance");
        specularPower = super.getUniformLocation("specularPower");
        ambientLight = super.getUniformLocation("ambientLight");
        directLightColor = super.getUniformLocation("directionalLight.colour");
        directLightDirection = super.getUniformLocation("directionalLight.direction");
        directLightIntensity = super.getUniformLocation("directionalLight.intensity");
        selectedBlock = super.getUniformLocation("selectedBlock");
        
    }

    
    /** 
     * Set if the shader should render the block with a blue hue
     * @param selected boolean 
     */
    public void setSelectedBlock(boolean selected) {
        super.setUniform(selectedBlock, selected ? 1.0f : 0.0f);
    }

    
    /** 
     * Load the transformation matrix to the shader
     * @param matrix The transformation matrix
     */
    public void loadTransformationMatrix(Matrix4f matrix) {
        super.setUniform(transformMatrixLocation, matrix);
    }

    
    /** 
     * Load the projection matrix to the shader
     * @param matrix the projection matrix
     */
    public void loadProjectionMatrix(Matrix4f matrix) {
        super.setUniform(projectionMatrixLocation, matrix);
    } 

    
    /** 
     * Load the view matrix to the shader
     * @param matrix the view matrix
     */
    public void loadViewMatrix(Matrix4f matrix) {
        super.setUniform(viewMatrixLocation, matrix);
    }

    
    /**
     * Load the specular power to the shader 
     * @param power the specular power
     */
    public void loadSpecularPower(float power) {
        super.setUniform(specularPower, power);
    }

    
    /** 
     * Load an ambient light to the shader
     * @param value the color of the ambient light
     */
    public void loadAmbientLight(Vector3f value) {
        super.setUniform(ambientLight, value);
    }

    
    /** 
     * Load a direction light to the shader
     * @param light Direction light
     */
    public void loadDirectionLight(DirectionalLight light) {
        super.setUniform(directLightColor, light.getColor());
        super.setUniform(directLightDirection, light.getDirection());
        super.setUniform(directLightIntensity, light.getIntensity());
    }

    
    /** 
     * Load a point light to the shader
     * @param light Point light
     */
    public void loadPointLight(PointLight light) {
        super.setUniform(pointLightColor, light.getColor());
        super.setUniform(pointLightPosition, light.getPosition());
        super.setUniform(pointLightIntensity, light.getIntensity());
        Attenuation att = light.getAttenuation();
        super.setUniform(pointLightAttConstant, att.getConstant());
        super.setUniform(pointLightAttLinear, att.getLinear());
        super.setUniform(pointLightAttExponent, att.getExponent());
    }

    
    /** 
     * Load a material to the shader
     * @param mat Material
     */
    public void loadMaterial(Material mat) {
        super.setUniform(matAmbient, mat.getAmbient());
        super.setUniform(matDiffuse, mat.getDiffuse());
        super.setUniform(matHasTexture, mat.hasTexture());
        super.setUniform(matSpecular,mat.getSpecular());
        super.setUniform(matReflectance, mat.getReflectance());
    }
	

    
    
}