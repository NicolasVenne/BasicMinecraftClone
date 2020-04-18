package computergraphics.states;

import computergraphics.core.Chunk;
import computergraphics.core.MouseInput;
import computergraphics.core.State;
import computergraphics.core.TerrainGenerator;
import computergraphics.core.ThreadDataRequester;
import computergraphics.entities.HUDEntity;
import computergraphics.entities.Player;
import computergraphics.entities.Skybox;

import computergraphics.graphics.Attenuation;
import computergraphics.graphics.DirectionalLight;
import computergraphics.graphics.FrustumCullingFilter;
import computergraphics.graphics.HUDShader;
import computergraphics.graphics.Loader;
import computergraphics.graphics.PointLight;
import computergraphics.graphics.Renderer;
import computergraphics.graphics.SkyboxShader;
import computergraphics.graphics.StaticShader;
import org.joml.Matrix4f;

import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * GameState
 * The main game state
 */
public class GameState implements State {

    private StaticShader program;
    private SkyboxShader skyboxProgram;
    private HUDShader hudProgram;
    private HUDEntity crossHair;
    private Renderer renderer;
    private Skybox skybox;
    private Player player;
    private TerrainGenerator terrainGenerator;
    private ThreadDataRequester threadDataRequester;
    private FrustumCullingFilter frustumFilter;
    private Vector3f ambientLight;
    private PointLight pointLight;
    private DirectionalLight directionalLight;
    private float lightAngle = -90f;

    

    @Override
    public void input(float delta, MouseInput input) {
        player.input(delta, input);
    }

    @Override
    public void fixedUpdate() {
        terrainGenerator.Update();
        threadDataRequester.Update();

    }

    @Override
    public void update(final float delta) {
        
        //Update the player
        player.update(delta);

        //Update the directional light to rotate like the sun
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

        hudProgram.start();
        renderer.renderHUD(crossHair, hudProgram);
        hudProgram.stop(); 
        
        program.start();
        Matrix4f view = player.getViewMatrix();
        program.loadViewMatrix(view);

        //Load lighting
        program.loadSpecularPower(10f);
        program.loadAmbientLight(ambientLight);
        program.loadDirectionLight(directionalLight);

        //Update the view frustum for culling
        frustumFilter.updateFrustum(renderer.projectionMatrix, view);

        //Render all visible chunks arround the player
        for(Chunk c : terrainGenerator.visibleChunks) {
            frustumFilter.filter(c);

            if(c.isInsideFrustrum) {
                renderer.render(c, program);
            }
        }

        program.stop();
        
        //Render the skybox
        skyboxProgram.start();
        Matrix4f skyboxView = player.getViewMatrix();
        skyboxView.m30(0);
        skyboxView.m31(0);
        skyboxView.m32(0);
        skyboxProgram.loadViewMatrix(skyboxView);
        renderer.render(skybox, skyboxProgram);
        skyboxProgram.stop();
    }

    @Override
    public void initialize() {

        //Create the player
        player = new Player();
        frustumFilter = new FrustumCullingFilter();

        //Create the crosshair model
        float inc = 0.05f;

        Vector3f[] vertices = {
			// Horizontal line
			new Vector3f(-inc, 0.0f, 0.0f),
			new Vector3f(+inc, 0.0f, 0.0f),

			// Vertical line
			new Vector3f(0.0f, -inc, 0.0f),
			new Vector3f(0.0f, +inc, 0.0f)
		};
		int[] indices = {0,1,2,3};
        Vector4f color = new Vector4f(1f,1f,1f,1f);

        crossHair = new HUDEntity(Loader.createModel(vertices, indices), color);


        //Lighting
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

        //Threaded setup
        threadDataRequester = new ThreadDataRequester();

        //Create the shader programs
        program = new StaticShader();
        skyboxProgram = new SkyboxShader();
        hudProgram = new HUDShader();

        //Create renderer
        renderer = new Renderer();

        //Create terrain generator
        terrainGenerator = new TerrainGenerator(player.transform);

        //Create skybox
        skybox = new Skybox();

        //Initialize the shader programs
        renderer.initialize(program);
        renderer.initialize(skyboxProgram);
        renderer.initialize(hudProgram);
    }

    @Override
    public void dispose() {
        program.dispose();
        Loader.dispose();
    }

    
}