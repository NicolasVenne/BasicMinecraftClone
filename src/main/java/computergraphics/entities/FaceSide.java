package computergraphics.entities;

/**
 * FaceSide
 * Enum for all 6 faces
 */
public enum FaceSide {
    FRONT(0),
    TOP(1),
    RIGHT(2),
    LEFT(3),
    BOTTOM(4),
    BACK(5);

    public final int index;
    
    private FaceSide(int index) {
        this.index = index;
    }
}
