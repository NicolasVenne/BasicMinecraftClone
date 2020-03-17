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
import computergraphics.graphics.Loader;
import computergraphics.models.Model;
import computergraphics.models.TexturedModel;
import computergraphics.graphics.Renderer;
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
    private Renderer renderer;

    private Block block;
    private Camera camera;
    private TerrainGenerator terrainGenerator;
    private ThreadDataRequester threadDataRequester;


    private int uniModel;
    private float previousAngle = 0f;
    private float angle = 0f;
    private final float angelPerSecond = 60f;

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
        
    }

    @Override
    public void render(final float alpha) {

        renderer.reset();        

        program.start();
        Matrix4f view = camera.getViewMatrix();
        program.loadViewMatrix(view);

        for(Chunk c : terrainGenerator.visibleChunks) {
            renderer.render(c, program);
        }
        program.stop();

    }

    @Override
    public void initialize() {

        new Block(BlockType.DIRT, new Vector3i(0,0,0), new Vector2i(0,0));
        threadDataRequester = new ThreadDataRequester();
        program = new StaticShader();

        renderer = new Renderer();
        camera = new Camera();
        terrainGenerator = new TerrainGenerator(camera.transform());


        renderer.initialize(program);



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