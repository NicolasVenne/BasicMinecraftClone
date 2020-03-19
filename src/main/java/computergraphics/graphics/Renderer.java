package computergraphics.graphics;

import static org.lwjgl.opengl.GL30.*;

import java.nio.IntBuffer;

import org.joml.Vector3f;

import computergraphics.core.Chunk;
import computergraphics.entities.Block;
import computergraphics.entities.BlockType;
import computergraphics.entities.Entity;
import computergraphics.entities.Face;
import computergraphics.entities.Skybox;

import org.joml.Matrix4f;
import computergraphics.math.Transform;
import computergraphics.models.MaterialModel;
import computergraphics.models.Model;
import computergraphics.models.TexturedModel;
/**
 * Renderer
 */
public class Renderer {

	private static final float FOV = 70f;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000f;

	public Matrix4f projectionMatrix;

	public void initialize(StaticShader shader) {
		float ratio = (float) Window.getWidth() / (float) Window.getHeight();
		projectionMatrix = new Matrix4f();
		projectionMatrix.setPerspective(FOV, ratio, NEAR_PLANE, FAR_PLANE);
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	public void initialize(SkyboxShader shader) {
		float ratio = (float) Window.getWidth() / (float) Window.getHeight();
		projectionMatrix = new Matrix4f();
		projectionMatrix.setPerspective(FOV, ratio, NEAR_PLANE, FAR_PLANE);
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
		Matrix4f matrix = Transform.createWorldMatrix(entity.getTransform());
		shader.loadTransformationMatrix(matrix);
		glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
		for(int i = 0; i < shader.getAttributeCount(); i++) {
			glDisableVertexAttribArray(i);
		}
		glBindVertexArray(0);
	}

	public void render(Block block, StaticShader shader) {
		if(!block.isInsideFrustrum) return;
		Matrix4f matrix = Transform.createWorldMatrix(block.worldTransform);
		shader.loadTransformationMatrix(matrix);
		for(int i = 0; i < block.faces.length; i++) {
			if(block.faces[i] == 1) {
				Model model = Face.getFaceModel(i);
				glBindVertexArray(model.getVaoID());
				for(int j = 0; j < shader.getAttributeCount(); j++) {
					glEnableVertexAttribArray(j);
				}
				glActiveTexture(GL_TEXTURE0);
				glBindTexture(GL_TEXTURE_2D, block.type.getMaterial().getTexture().getId());
				shader.loadMaterial(block.type.getMaterial());
				glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
				for(int j = 0; j < shader.getAttributeCount(); j++) {
					glDisableVertexAttribArray(j);
				}
				glBindVertexArray(0);
				glBindTexture(GL_TEXTURE_2D, 0);
			}
		}
		
	}

	public void render(Skybox skybox, SkyboxShader shader) {
		Model model = skybox.getTextureModel();
		glDisable(GL_CULL_FACE);
		glBindVertexArray(model.getVaoID());
		for(int i = 0; i < shader.getAttributeCount(); i++) {
			glEnableVertexAttribArray(i);
		}
		if(model instanceof TexturedModel) {
			TexturedModel texturedModel = (TexturedModel)model;
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, texturedModel.getTexture().getId());
		}
		Matrix4f matrix = Transform.createWorldMatrix(skybox.getPosition());
		shader.loadTransformationMatrix(matrix);
		glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
		for(int i = 0; i < shader.getAttributeCount(); i++) {
			glDisableVertexAttribArray(i);
		}
		glBindVertexArray(0);
		glEnable(GL_CULL_FACE);
	}

	public void render(Chunk chunk, StaticShader shader) {
		for (Block block : chunk.visibleInnerBlocks) {
			render(block, shader);
		}
		for (Block block : chunk.visibleEdgeBlocks) {
			render(block, shader);
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