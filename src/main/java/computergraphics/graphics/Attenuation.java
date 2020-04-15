package computergraphics.graphics;

/**
 * Attenuation
 * Attenuation data for a light
 */
public class Attenuation {

    private float constant;
    private float linear;
    private float exponent;

    public Attenuation(float constant, float linear, float exponent) {
        this.setConstant(constant);
        this.setLinear(linear);
        this.setExponent(exponent);
    }

    public float getExponent() {
        return exponent;
    }

    public void setExponent(float exponent) {
        this.exponent = exponent;
    }

    public float getLinear() {
        return linear;
    }

    public void setLinear(float linear) {
        this.linear = linear;
    }

    public float getConstant() {
        return constant;
    }

    public void setConstant(float constant) {
        this.constant = constant;
    }

}