package computergraphics.graphics;

import static org.lwjgl.opengl.GL30.*;

import computergraphics.core.Chunk;
import computergraphics.entities.Block;
import computergraphics.entities.BlockType;
import computergraphics.entities.Entity;
import computergraphics.math.Matrix4x4;
import computergraphics.math.Transform;
import computergraphics.math.Vector3;
import computergraphics.models.Model;
import computergraphics.models.TexturedModel;
/**
 * Renderer
 */
public class Renderer {

	private static final float FOV = 70f;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000f;

	private Matrix4x4 projectionMatrix;

	public void initialize(StaticShader shader) {
		float ratio = (float) Window.getWidth() / (float) Window.getHeight();
		projectionMatrix = Matrix4x4.perspective(FOV, ratio, NEAR_PLANE, FAR_PLANE);
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	public void reset() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	
	}

	public void render(Entity entity, StaticShader shader) {
		Model model = entity.getModel();
		glBindVertexArray(model.getVaoID());
		for(int i = 0; i < shader.getAttributeCount(); i++) {
			glEnableVertexAttribArray(i);
		}
		if(model instanceof TexturedModel) {
			TexturedModel texturedModel = (TexturedModel)model;
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, texturedModel.getTexture().getId());
		}
		Matrix4x4 matrix = Transform.createTransformationMatrix(entity.getTransform());
		shader.loadTransformationMatrix(matrix);
		glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
		for(int i = 0; i < shader.getAttributeCount(); i++) {
			glDisableVertexAttribArray(i);
		}
		glBindVertexArray(0);
	}

	public void render(Chunk chunk, StaticShader shader) {
		for(int y = 0; y < Chunk.CHUNK_HEIGHT; y++) {
            for(int z = 0; z < Chunk.CHUNK_WIDTH; z++) {
                for(int x = 0; x < Chunk.CHUNK_WIDTH; x++) {
					if(chunk.chunk[x][y][z] != BlockType.AIR) {
						if(chunk.isNextToAir(x,y,z)) {
							render(new Block(chunk.chunk[x][y][z], new Transform(new Vector3(x,y,z), Vector3.zero(), Vector3.one())), shader);
						}
					}
                }
            }
        }
	}

	public void initialize() {
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
    	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDepthFunc(GL_LESS);
		glClearColor(0.25f, 0.75f, 1.0f, 1.0f);

	}

	public void dispose() {
	}

    
}