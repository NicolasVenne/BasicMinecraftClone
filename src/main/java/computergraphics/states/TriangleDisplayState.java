package computergraphics.states;

import static org.lwjgl.opengl.GL30.*;

import java.nio.IntBuffer;
import java.util.Vector;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

import computergraphics.core.Chunk;
import computergraphics.core.State;
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
import computergraphics.math.Transform;
import org.joml.Vector3f;
import org.joml.Vector2f;

/**
 * TriangleDisplayState
 */
public class TriangleDisplayState implements State {

    


    private StaticShader program;
    private Renderer renderer;
    private Chunk chunk;
    private Block block;
    private Chunk[] chunks;
    private Camera camera;


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
        
    }

    @Override
    public void render(final float alpha) {

        renderer.reset();        

        program.start();
        Matrix4f view = camera.getViewMatrix();
        program.loadViewMatrix(view);
        renderer.render(chunk, program);
        // for(int i = 0; i < chunks.length; i++) {
        //     renderer.render(chunks[i], program);
        // }        
        program.stop();

    }

    @Override
    public void initialize() {

        
        chunks = new Chunk[20 * 20];
        int index = 0;
        for(int x = 0; x < 20; x++){
            for(int y = 0; y < 20; y++) {
                chunks[index] = new Chunk();
                index++;
            }
        }         
        block = new Block(BlockType.DIRT, Transform.zero());
        chunk = new Chunk();
        



        program = new StaticShader();

        renderer = new Renderer();
        camera = new Camera();

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