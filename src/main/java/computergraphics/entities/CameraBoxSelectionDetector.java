package computergraphics.entities;

import org.joml.Intersectionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import computergraphics.core.Chunk;

public class CameraBoxSelectionDetector {

    private final Vector3f max;

    private final Vector3f min;

    private final Vector2f nearFar;

    private Vector3f dir;

    public CameraBoxSelectionDetector() {
        dir = new Vector3f();
        min = new Vector3f();
        max = new Vector3f();
        nearFar = new Vector2f();
    }

    public void selectBlock(Chunk chunk, Camera camera) {   
        if(chunk.isVisible) {
            dir = camera.getViewMatrix().positiveZ(dir).negate();
            selectBlock(chunk, camera.getPosition(), dir);
        }
    }
    
    protected void selectBlock(Chunk chunk, Vector3f center, Vector3f dir) {
        Block selectedBlock = null;
        float closestDistance = 5f;
        
        for(int y = 0; y < chunk.CHUNK_HEIGHT; y++) {
            for(int z = 0; z < chunk.CHUNK_WIDTH; z++) {
                for(int x = 0; x < chunk.CHUNK_WIDTH; x++) {
                    if(chunk.chunk[x][y][z].type == BlockType.DIRT) {
                        chunk.chunk[x][y][z].setSelected(false);
                        min.set(chunk.chunk[x][y][z].worldTransform.position);
                        max.set(chunk.chunk[x][y][z].worldTransform.position);
                        min.add(-chunk.chunk[x][y][z].worldTransform.scale.x, -chunk.chunk[x][y][z].worldTransform.scale.y, -chunk.chunk[x][y][z].worldTransform.scale.z);
                        max.add(chunk.chunk[x][y][z].worldTransform.scale.x, chunk.chunk[x][y][z].worldTransform.scale.y, chunk.chunk[x][y][z].worldTransform.scale.z);
                        if (Intersectionf.intersectRayAab(center, dir, min, max, nearFar) && nearFar.x < closestDistance) {
                            closestDistance = nearFar.x;
                            selectedBlock = chunk.chunk[x][y][z];
                        }
                    }
                }
            }
        }
        if (selectedBlock != null) {
            selectedBlock.setSelected(true);
            System.out.println("Selected " + selectedBlock.worldTransform.position);
        }
    }
}