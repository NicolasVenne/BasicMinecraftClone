package computergraphics.math;

import org.joml.Vector2f;
import org.joml.Vector3f;
/**
 * Utils
 * Utility class for convertion
 */
public class Utils {

    /**
     * Convert a Vector3f array to a float array
     * @param data the vector3f array
     * @return float array
     */
    public static float[] convertDataToFloatArray(Vector3f[] data) {
        float[] result = new float[data.length * 3];
        for(int i = 0; i < data.length; i++) {
            result[i * 3] = data[i].x;
            result[i * 3 + 1] = data[i].y;
            result[i * 3 + 2] = data[i].z;
        }
        return result;
    }

    /**
     * Convert a Vector2f array to a float array
     * @param data the vector2f array
     * @return float array
     */
	public static float[] convertDataToFloatArray(Vector2f[] data) {
		float[] result = new float[data.length * 2];
        for(int i = 0; i < data.length; i++) {
            result[i * 2] = data[i].x;
            result[i * 2 + 1] = data[i].y;
        }
        return result;
	} 
}