package computergraphics.core;


/**
 * State
 * Interface of methods for a state
 */
public interface State {

    public void input(float delta, MouseInput mouseInput);

    public default void fixedUpdate() {}

    public void update(float delta);

    public void render(float alpha);

    public void initialize();

    public void dispose();

    public static State empty() {
        return new VoidState();
    }
}

