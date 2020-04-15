package computergraphics.math;

import java.util.Objects;

import org.joml.Vector3f;


/**
 * BoxCollider
 * Box collider for blocks
 */
public class BoxCollider {

    public Vector3f origin;
    public Vector3f size;
    public final Object parent;

    /**
     * Create a box collider
     * @param origin the center/origin of the collider
     * @param size The size of the collider in x,y,z direction
     * @param parent The parent that this collider belongs to
     */
    public BoxCollider(Vector3f origin, Vector3f size, Object parent) {
        this.origin = origin;
        this.size = size;
        this.parent = parent;

    }

    /**
     * Create a box collider of default block size
     * @param origin the center/origin of the collider
     * @param parent the parent that this collider belongs to
     */
    public BoxCollider(Vector3f origin, Object parent) {
        this(origin, new Vector3f(0.5f, 0.5f, 0.5f), parent);
    }

    /**
     * Check if this collider is colliding with another collider
     * @param other THe other box collider
     * @return boolean true if colliding
     */
    public boolean isColliding(BoxCollider other) {
        return 
         (origin.x - size.x <= other.origin.x + other.size.x && origin.x + size.x >= other.origin.x - other.size.x) &&
         (origin.y - size.y <= other.origin.y + other.size.y && origin.y + size.y >= other.origin.y - other.size.y) &&
         (origin.z - size.z <= other.origin.z + other.size.z && origin.z + size.z >= other.origin.z - other.size.z);
    }


    /**
     * Check if a given point is inside this collider
     * @param point Point in 3d space
     * @return boolean true if colliding
     */
    public boolean isPointInside(Vector3f point) {
        return (point.x >= origin.x - size.x && point.x <= origin.x + size.x) &&
               (point.y >= origin.y - size.y && point.y <= origin.y + size.y) &&
               (point.z >= origin.z - size.z && point.z <= origin.z + size.z);
    }

    /**
     * Check if a given point is inside this collider
     * @param x point x
     * @param y point y
     * @param z point z
     * @return boolean true if colliding
     */
    public boolean isPointInside(float x, float y, float z) {
        return (x >= origin.x - size.x && x <= origin.x + size.x) &&
               (y >= origin.y - size.y && y <= origin.y + size.y) &&
               (z >= origin.z - size.z && z <= origin.z + size.z);
    }

    /**
     * Override the equals to compare with other colliders
     */
    @Override
    public boolean equals(Object b) {
        if(b == this) return true;
        if(b == null) return false;
        if(!(b instanceof BoxCollider)) return false;
        BoxCollider col = (BoxCollider)b;
        if(!col.origin.equals(this.origin)) return false;
        if(!col.size.equals(this.size)) return false;
        return true;
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.origin, this.size);
    } 


}