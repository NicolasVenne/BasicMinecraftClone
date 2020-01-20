package computergraphics.graphics;

import static org.lwjgl.opengl.GL30.*;

import computergraphics.entities.Entity;
import computergraphics.math.Matrix4x4;
import computergraphics.math.Transform;
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
		glClearColor(1f, 0f,0f,1f);
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

	public void initialize() {
	}

	public void dispose() {
	}

    
}