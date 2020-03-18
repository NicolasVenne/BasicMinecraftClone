package computergraphics.graphics;

import org.joml.FrustumIntersection;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import computergraphics.core.Chunk;
import computergraphics.entities.Block;

/**
 * FrustumCullingFilter
 */
public class FrustumCullingFilter {

    private final Matrix4f prjViewMatrix;

    private FrustumIntersection frustumInt;

    public FrustumCullingFilter() {
        prjViewMatrix = new Matrix4f();
        frustumInt = new FrustumIntersection();
    }

    public void updateFrustum(Matrix4f projMatrix, Matrix4f viewMatrix) {
        // Calculate projection view matrix
        prjViewMatrix.set(projMatrix);
        prjViewMatrix.mul(viewMatrix);
        // Update frustum intersection class
        frustumInt.set(prjViewMatrix);
    }

    public void filter(Block block) {
        boolean isInside = insideFrustumSphere(block.worldTransform.position.x, block.worldTransform.position.y, block.worldTransform.position.z, Block.BoundingRadius);
        block.setInsideFrustum(isInside);
    }

    public void filter(Chunk chunk) {
        chunk.isInsideFrustrum = insideFrustumCube(
            chunk.coordinates.x * Chunk.CHUNK_WIDTH,
            0f,
            chunk.coordinates.y * Chunk.CHUNK_WIDTH,
            (chunk.coordinates.x * Chunk.CHUNK_WIDTH) + Chunk.CHUNK_WIDTH,
            60f,
            (chunk.coordinates.y * Chunk.CHUNK_WIDTH) + Chunk.CHUNK_WIDTH
        );
        if(chunk.isInsideFrustrum) {
            for(Block b : chunk.visibleBlocks) {
                filter(b);
            }
        }
    }

    

    public boolean insideFrustumSphere(float x0, float y0, float z0, float boundingRadius) {
        return frustumInt.testSphere(x0, y0, z0, boundingRadius);
    }

    public boolean insideFrustumCube(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        return frustumInt.testAab(minX, minY, minZ, maxX, maxY, maxZ);
    }
}