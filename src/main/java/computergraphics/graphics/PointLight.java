package computergraphics.graphics;

import org.joml.Vector3f;

/**
 * PointLight
 * Point light data
 */
public class PointLight {

    private Vector3f color;
    private Vector3f position;
    private float intensity;
    private Attenuation attenuation;

    public PointLight(Vector3f color, Vector3f position, float intensity, Attenuation att) {
        this.setColor(color);
        this.setPosition(position);
        this.setIntensity(intensity);
        this.setAttenuation(att);
    }

    public PointLight(Vector3f color, Vector3f position, float intensity) {
        attenuation = new Attenuation(1, 0, 0);
        this.color = color;
        this.position = position;
        this.intensity = intensity;
    }

    public PointLight(PointLight pointLight) {
        this(new Vector3f(pointLight.getColor()), new Vector3f(pointLight.getPosition()),
                pointLight.getIntensity(), pointLight.getAttenuation());
    }

    public Attenuation getAttenuation() {
        return attenuation;
    }

    public void setAttenuation(Attenuation attenuation) {
        this.attenuation = attenuation;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
    

    
}