package computergraphics.states;

import static org.lwjgl.opengl.GL30.*;

import java.nio.IntBuffer;
import java.util.Vector;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

import computergraphics.core.Chunk;
import computergraphics.core.State;
import computergraphics.core.TerrainGenerator;
import computergraphics.core.ThreadDataRequester;
import computergraphics.entities.Block;
import computergraphics.entities.BlockType;
import computergraphics.entities.Camera;
import computergraphics.entities.Entity;

import computergraphics.entities.Skybox;

import computergraphics.graphics.Attenuation;
import computergraphics.graphics.DirectionalLight;
import computergraphics.graphics.FrustumCullingFilter;

import computergraphics.graphics.Loader;
import computergraphics.graphics.PointLight;
import computergraphics.models.Model;
import computergraphics.models.TexturedModel;
import computergraphics.graphics.Renderer;
import computergraphics.graphics.SkyboxShader;
import computergraphics.graphics.StaticShader;
import computergraphics.graphics.Texture2D;
import org.joml.Matrix4f;

import computergraphics.math.NoiseGen;
import computergraphics.math.Transform;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector2f;
import org.joml.Vector2i;

/**
 * TriangleDisplayState
 */
public class TriangleDisplayState implements State {

    


    private StaticShader program;
    private SkyboxShader skyboxProgram;
    private Renderer renderer;


    private Block block;
    private Skybox skybox;

    private Block blockTest;
    private Chunk chunkTest;

    private Camera camera;
    private TerrainGenerator terrainGenerator;
    private ThreadDataRequester threadDataRequester;
    private FrustumCullingFilter frustumFilter;


    private Vector3f ambientLight;

    private PointLight pointLight;
    private DirectionalLight directionalLight;

    private int uniModel;
    private float previousAngle = 0f;
    private float angle = 0f;
    private final float angelPerSecond = 60f;

    private float lightAngle = -90f;

    @Override
    public void input(float delta) {
        // TODO Auto-generated method stub
        camera.move(delta);
    }

    @Override
    public void update(final float delta) {
        
        previousAngle = angle;
        angle += delta * angelPerSecond;
        terrainGenerator.Update();
        threadDataRequester.Update();

        lightAngle += 1.1f * delta;
        if (lightAngle > 90) {
            directionalLight.setIntensity(0);
            if (lightAngle >= 360) {
                lightAngle = -90;
            }
        } else if (lightAngle <= -80 || lightAngle >= 80) {
            float factor = 1 - (float) (Math.abs(lightAngle) - 80) / 10.0f;
            directionalLight.setIntensity(factor);
            directionalLight.getColor().y = Math.max(factor, 0.9f);
            directionalLight.getColor().z = Math.max(factor, 0.5f);
        } else {
            directionalLight.setIntensity(1);
            directionalLight.getColor().x = 1;
            directionalLight.getColor().y = 1;
            directionalLight.getColor().z = 1;
        }
        double angRad = Math.toRadians(lightAngle);
        directionalLight.getDirection().x = (float) Math.sin(angRad);
        directionalLight.getDirection().y = (float) Math.cos(angRad);
        
    }

    @Override
    public void render(final float alpha) {

        renderer.reset();  
        
        
        program.start();
        

        Matrix4f view = camera.getViewMatrix();
        program.loadViewMatrix(view);

        program.loadSpecularPower(10f);
        program.loadAmbientLight(ambientLight);
        program.loadDirectionLight(directionalLight);
        frustumFilter.updateFrustum(renderer.projectionMatrix, view);

        for(Chunk c : terrainGenerator.visibleChunks) {
            frustumFilter.filter(c);

            if(c.isInsideFrustrum) {
                renderer.render(c, program);
            }
        }
        program.stop();
        skyboxProgram.start();
        Matrix4f skyboxView = camera.getViewMatrix();
        skyboxView.m30(0);
		    skyboxView.m31(0);
		    skyboxView.m32(0);
        skyboxProgram.loadViewMatrix(skyboxView);
        renderer.render(skybox, skyboxProgram);

        skyboxProgram.stop();

    }

    @Override
    public void initialize() {

        blockTest = new Block(BlockType.DIRT, new Vector3i(0,0,0), new Vector2i(0,0), null);
        
        frustumFilter = new FrustumCullingFilter();
        ambientLight = new Vector3f(0.5f, 0.5f, 0.5f);
        Vector3f lightColour = new Vector3f(1, 0.5f, 1);
        Vector3f lightPosition = new Vector3f(0, 0f, -1f);
        float lightIntensity = 1.0f;
        pointLight = new PointLight(lightColour, lightPosition, lightIntensity);
        Attenuation att = new Attenuation(0f, 0f, 1.0f);
        pointLight.setAttenuation(att);

        lightPosition = new Vector3f(-1, 0, 0);
        lightColour = new Vector3f(1, 1, 0.5f);
        directionalLight = new DirectionalLight(lightColour, lightPosition, lightIntensity);

 
        threadDataRequester = new ThreadDataRequester();
        program = new StaticShader();
        skyboxProgram = new SkyboxShader();

        renderer = new Renderer();
        camera = new Camera();
        terrainGenerator = new TerrainGenerator(camera.transform());
        skybox = new Skybox();


        renderer.initialize(program);
        renderer.initialize(skyboxProgram);



        // uniModel = program.getUniformLocation("model");
        
        // Matrix4x4 view = new Matrix4x4();
        // int uniView = program.getUniformLocation("view");
        // program.setUniform(uniView, view);

        // float ratio;
        // try(MemoryStack stack = MemoryStack.stackPush()) {
        //     long window = GLFW.glfwGetCurrentContext();
        //     IntBuffer width = stack.mallocInt(1);
        //     IntBuffer height = stack.mallocInt(1);
        //     GLFW.glfwGetFramebufferSize(window, width, height);
        //     ratio = width.get() / (float) height.get();
        // }

        // Matrix4x4 projection = Matrix4x4.orthographic(-ratio, ratio, -1f, 1f, -1f, 1f);
        // int uniProjection = program.getUniformLocation("projection");
        // program.setUniform(uniProjection, projection);


    }

    

    @Override
    public void dispose() {

        program.dispose();
        Loader.dispose();

    }

    
}