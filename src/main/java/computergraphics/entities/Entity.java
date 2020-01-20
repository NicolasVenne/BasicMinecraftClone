package computergraphics.entities;

import computergraphics.models.Model;
import computergraphics.models.TexturedModel;
import computergraphics.math.Transform;

/**
 * Entity
 */
public class Entity {

    protected TexturedModel model;
    protected Transform transform;

    public Entity(TexturedModel model, Transform transform) {
        this.model = model;
        this.transform = transform;
    }

    public Transform getTransform() {
        return this.transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public TexturedModel getModel() {
        return model;
    }

    public void setModel(TexturedModel model) {
        this.model = model;
    }



}