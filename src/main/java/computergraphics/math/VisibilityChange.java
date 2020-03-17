package computergraphics.math;

import computergraphics.core.Chunk;

/**
 * VisibilityChange
 */
public interface VisibilityChange {

    void OnChunkVisibilityChange(Chunk chunk, boolean visible);
}