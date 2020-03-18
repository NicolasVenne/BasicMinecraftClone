package computergraphics.core;

import computergraphics.entities.Block;

public interface BlockVisibilityChange {
    void OnBlockVisibilityChange(Block b, boolean visible);
}
