package computergraphics.core;

import java.util.HashMap;


/**
 * FiniteStateMachine
 */
public class FiniteStateMachine implements State {

    private HashMap<String, State> stateMap;
    private State currentState;

    public FiniteStateMachine() {
        stateMap = new HashMap<String, State>();
        currentState = State.empty();
        stateMap.put(null, currentState);
    }

    public void add(String name, State state) {
        stateMap.put(name, state);
    }

    public void remove(String name) {
        stateMap.remove(name);
    }

    public void swap(String name) {
        currentState.dispose();
        currentState = stateMap.get(name);
        currentState.initialize();
    }

    @Override
    public void input(float delta, MouseInput mouseInput) {
        currentState.input(delta, mouseInput);
    }

    @Override
    public void update(float delta) {
        currentState.update(delta);
    }

    @Override
    public void fixedUpdate() {
        currentState.fixedUpdate();
    }

    @Override
    public void render(float alpha) {
        currentState.render(alpha);
    }

    @Override
    public void initialize() {
        currentState.initialize();
    }

    @Override
    public void dispose() {
        currentState.dispose();
    }
}