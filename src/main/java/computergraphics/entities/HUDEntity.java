package computergraphics.entities;

import computergraphics.models.Model;

import org.joml.Vector4f;

/**
 * HUDEntity
 */
public class HUDEntity {

    protected Model model;
    protected Vector4f colour;

    public HUDEntity(Model model, Vector4f colour) {
        this.model = model;
        this.colour = colour;
    }

    public Vector4f getColour() {
        return this.colour;
    }

    public void setColour(Vector4f colour) {
        this.colour = colour;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

}