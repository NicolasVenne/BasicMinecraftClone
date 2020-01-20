package computergraphics.states;

import static org.lwjgl.opengl.GL30.*;

import java.nio.IntBuffer;
import java.util.Vector;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

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
import computergraphics.math.Matrix4x4;
import computergraphics.math.Transform;
import computergraphics.math.Vector2;
import computergraphics.math.Vector3;

/**
 * TriangleDisplayState
 */
public class TriangleDisplayState implements State {

    


    private StaticShader program;
    private TexturedModel block;
    private Renderer renderer;
    private Entity test;
    private Entity test2;
    private Camera camera;

    private Block[] blocks;

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
        Matrix4x4 view = Matrix4x4.view(camera);
        program.loadViewMatrix(view);

        renderer.render(blocks[0], program);
        
        program.stop();

    }

    @Override
    public void initialize() {

        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS);
        blocks = new Block[1];
            
        

        Transform transform = new Transform(new Vector3(0f,0f,-1f), new Vector3(0f,0f,0f), Vector3.one());
        blocks[0] = new Block(BlockType.DIRT, transform);



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