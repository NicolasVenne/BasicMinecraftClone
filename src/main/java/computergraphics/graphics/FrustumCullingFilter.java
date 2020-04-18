package computergraphics.graphics;

import org.joml.FrustumIntersection;
import org.joml.Matrix4f;

import computergraphics.core.Chunk;
import computergraphics.entities.Block;

/**
 * FrustumCullingFilter
 * Will cull the faces that are outside the view frustum
 */
public class FrustumCullingFilter {

    private final Matrix4f prjViewMatrix;

    private FrustumIntersection frustumInt;

    public FrustumCullingFilter() {
        prjViewMatrix = new Matrix4f();
        frustumInt = new FrustumIntersection();
    }

    
    /** 
     * Update the frustum matrix
     * @param projMatrix The projection matrix used
     * @param viewMatrix The view matrix to cull from
     */
    public void updateFrustum(Matrix4f projMatrix, Matrix4f viewMatrix) {
        // Calculate projection view matrix
        prjViewMatrix.set(projMatrix);
        prjViewMatrix.mul(viewMatrix);
        // Update frustum intersection class
        frustumInt.set(prjViewMatrix);
    }

    
    /** 
     * Filter a block to see if it is within the frustum view
     * @param block
     */
    public void filter(Block block) {
        boolean isInside = insideFrustumSphere(block.worldTransform.position.x, block.worldTransform.position.y, block.worldTransform.position.z, Block.BoundingRadius);
        block.setInsideFrustum(isInside);
    }

    
    /** 
     * Filter a entire chunk to see if the a part of the chunk is within the view
     * @param chunk
     */
    public void filter(Chunk chunk) {
        chunk.isInsideFrustrum = insideFrustumCube(
            chunk.coordinates.x * Chunk.CHUNK_WIDTH,
            0f,
            chunk.coordinates.y * Chunk.CHUNK_WIDTH,
            (chunk.coordinates.x * Chunk.CHUNK_WIDTH) + Chunk.CHUNK_WIDTH,
            60f,
            (chunk.coordinates.y * Chunk.CHUNK_WIDTH) + Chunk.CHUNK_WIDTH
        );
        //If a part of the chunk is within then check all blocks inside that chunk.
        if(chunk.isInsideFrustrum) {
            for(Block b : chunk.visibleEdgeBlocks.values()) {
                filter(b);
            }
            for(Block b : chunk.visibleInnerBlocks.values()) {
                filter(b);
            }
        }
    }

    

    
    /** 
     * Check if inside frustum using a sphere
     * @param x0 center x
     * @param y0 center y
     * @param z0 center z
     * @param boundingRadius radius of sphere
     * @return boolean true if is inside frustum
     */
    public boolean insideFrustumSphere(float x0, float y0, float z0, float boundingRadius) {
        return frustumInt.testSphere(x0, y0, z0, boundingRadius);
    }

    
    /** 
     * Check if inside frustum using a cube
     * @param minX bottom left of cube x
     * @param minY bottom left of cube y
     * @param minZ bottom left of cube z
     * @param maxX Top right of cube x
     * @param maxY Top right of cube y
     * @param maxZ Top right of cube z
     * @return boolean true if inside frustum
     */
    public boolean insideFrustumCube(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        return frustumInt.testAab(minX, minY, minZ, maxX, maxY, maxZ);
    }
}