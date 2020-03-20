package computergraphics.math;

import java.util.Objects;

import org.joml.Vector3f;

/**
 * BoxCollider
 */
public class BoxCollider {

    public Vector3f origin;
    public Vector3f size;

    public BoxCollider(Vector3f origin, Vector3f size) {
        this.origin = origin;
        this.size = size;

    }

    public BoxCollider(Vector3f origin) {
        this(origin, new Vector3f(0.5f, 0.5f, 0.5f));
    }

    public boolean isColliding(BoxCollider other) {
        return 
         (origin.x - size.x <= other.origin.x + other.size.x && origin.x + size.x >= other.origin.x - other.size.x) &&
         (origin.y - size.y <= other.origin.y + other.size.y && origin.y + size.y >= other.origin.y - other.size.y) &&
         (origin.z - size.z <= other.origin.z + other.size.z && origin.z + size.z >= other.origin.z - other.size.z);
    }

    public boolean isPointInside(Vector3f point) {
        return (point.x >= origin.x - size.x && point.x <= origin.x + size.x) &&
               (point.y >= origin.y - size.y && point.y <= origin.y + size.y) &&
               (point.z >= origin.z - size.z && point.z <= origin.z + size.z);
    }

    public boolean isPointInside(float x, float y, float z) {
        return (x >= origin.x - size.x && x <= origin.x + size.x) &&
               (y >= origin.y - size.y && y <= origin.y + size.y) &&
               (z >= origin.z - size.z && z <= origin.z + size.z);
    }

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