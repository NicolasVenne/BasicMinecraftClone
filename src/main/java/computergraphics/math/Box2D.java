package computergraphics.math;

/**
 * Box2D
 */
public class Box2D {

    public float x;

    public float y;

    public float z;

    public float width;


    public Box2D(float x, float y, float z, float width) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
    }

    public boolean contains(float x2, float y2, float z2) {
        return x2 >= x
                && y2 >= y
                && x2 < x + width
                && y2 < y + width
                && z2 >= z
                && z2 < z + width;
    }

    public String toString() {
        return "(" + x + " " + y + " " + z + " " + width + ")";
    }
}