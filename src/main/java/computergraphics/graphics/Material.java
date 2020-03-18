package computergraphics.graphics;

import org.joml.Vector4f;

/**
 * Material
 */
public class Material {

    private static final Vector4f DEFAULT_COLOUR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);

    private Vector4f ambient;
    private Vector4f diffuse;
    private Vector4f specular;
    private Texture2D texture;
    private float reflectance;

    public Material() {
        this.setAmbient(DEFAULT_COLOUR);
        this.setDiffuse(DEFAULT_COLOUR);
        this.setSpecular(DEFAULT_COLOUR);
        this.texture = null;
        this.reflectance = 0;
    }

    public Material(Vector4f colour, float reflectance) {
        this(colour, colour, colour, null, reflectance);
    }

    public Material(Texture2D texture) {
        this(DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, texture, 0);
    }

    public Material(Texture2D texture, float reflectance) {
        this(DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, texture, reflectance);
    }

    public Material(Vector4f ambient, Vector4f diffuse, Vector4f specular, Texture2D texture, float reflectance) {
        this.setAmbient(ambient);
        this.setDiffuse(diffuse);
        this.setSpecular(specular);
        this.setReflectance(reflectance);
        this.setTexture(texture);
    }

    public Texture2D getTexture() {
        return texture;
    }

    public void setTexture(Texture2D texture) {
        this.texture = texture;
    }

    public float getReflectance() {
        return reflectance;
    }

    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }

    

    public Vector4f getSpecular() {
        return specular;
    }

    public void setSpecular(Vector4f specular) {
        this.specular = specular;
    }

    public Vector4f getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(Vector4f diffuse) {
        this.diffuse = diffuse;
    }

    public Vector4f getAmbient() {
        return ambient;
    }

    public void setAmbient(Vector4f ambient) {
        this.ambient = ambient;
    }

    public boolean hasTexture() {
        return this.texture != null;
    }
}