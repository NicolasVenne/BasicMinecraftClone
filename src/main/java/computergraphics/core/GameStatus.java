package computergraphics.core;

/**
 * Enum to hold the status of the game
 */
public enum GameStatus {
    INIT(0), //Initializing status
    OK(10), //The game is good to run
    EXIT(11), //The game has exited

    GLError(20); //The game ran into a opengl error


    private int statusCode;

    GameStatus(int code) {
        statusCode = code;
    }

    public int getCode() {
        return statusCode;
    }
}