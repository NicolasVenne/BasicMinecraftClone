package computergraphics.graphics;

import static org.lwjgl.opengl.GL30.*;

import computergraphics.core.Chunk;
import computergraphics.entities.Block;
import computergraphics.entities.Face;
import computergraphics.entities.Skybox;
import computergraphics.entities.HUDEntity;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import computergraphics.math.Transform;
import computergraphics.models.Model;
import computergraphics.models.TexturedModel;
/**
 * Renderer
 * Handle the rendering of entities, blocks, chunks and skybox
 */
public class Renderer {

	private static final float FOV = 70f;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000f;

	public Matrix4f projectionMatrix;
	private Matrix4f orthoMatrix;

	
	/** 
	 * Initilize the static shader
	 * @param shader The static shader to load the projection matrix too.
	 */
	public void initialize(StaticShader shader) {
		float ratio = (float) Window.getWidth() / (float) Window.getHeight();
		projectionMatrix = new Matrix4f();
		projectionMatrix.setPerspective(FOV, ratio, NEAR_PLANE, FAR_PLANE);
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	public void initialize(HUDShader shader) {
		orthoMatrix = new Matrix4f();
		orthoMatrix.identity();
		orthoMatrix.setOrtho(-1, 1, -1, 1, 0, 1);
		shader.start();
		shader.loadProjectionMatrix(orthoMatrix);
		shader.stop();
	}

	
	/** 
	 * Intialize the skybox shader
	 * @param shader the skybox shader to load the projection matrix too
	 */
	public void initialize(SkyboxShader shader) {
		float ratio = (float) Window.getWidth() / (float) Window.getHeight();
		projectionMatrix = new Matrix4f();
		projectionMatrix.setPerspective(FOV, ratio, NEAR_PLANE, FAR_PLANE);
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	/**
	 * Run glClear for the next render
	 */
	public void reset() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	
	}

	public void renderHUD(HUDEntity entity, HUDShader shader) {
		Model model = entity.getModel();
		glBindVertexArray(model.getVaoID());
		for(int i = 0; i < shader.getAttributeCount(); i++) {
			glEnableVertexAttribArray(i);
		}
		shader.setColor(new Vector4f(1f,1f,1f,1f));
		glDrawElements(GL_LINES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
		for(int i = 0; i < shader.getAttributeCount(); i++) {
			glDisableVertexAttribArray(i);
		}
		glBindVertexArray(0);
	}

	
	/** 
	 * Render a block to the screen
	 * @param block The block to render
	 * @param shader The Static shader in use
	 */
	public void render(Block block, StaticShader shader) {
		//If the block isnt visible by the player, dont render
		if(!block.isInsideFrustrum) return;
		//Create the transformation matrix of the block
		Matrix4f matrix = Transform.createWorldMatrix(block.worldTransform);
		shader.loadTransformationMatrix(matrix);
		//Paint the block blue if its selected
		shader.setSelectedBlock(block.selected);

		//Render all the block faces that the block has enabled / visible
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

	
	/** 
	 * Render the skybox
	 * @param skybox The skybox to render
	 * @param shader The skybox shader to use
	 */
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

	
	/** 
	 * Render a chunk
	 * @param chunk The chunk to render
	 * @param shader The shader to use
	 */
	public void render(Chunk chunk, StaticShader shader) {
		//Render all visible chunks around the player
		for (Block block : chunk.visibleInnerBlocks.values()) {
			render(block, shader);
		}
		for (Block block : chunk.visibleEdgeBlocks.values()) {
			render(block, shader);
		}
	}

	/**
	 * Initialize the renderer
	 */
	public void initialize() {
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
    	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDepthFunc(GL_LESS);
		glClearColor(0.25f, 0.75f, 1.0f, 1.0f);

	}

    
}