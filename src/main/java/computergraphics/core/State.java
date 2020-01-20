package computergraphics.core;

/**
 * State
 */
public interface State {

    public void input(float delta);

    public default void fixedUpdate() {}

    public void update(float delta);

    public void render(float alpha);

    public void initialize();

    public void dispose();

    public static State empty() {
        return new VoidState();
    }
}

