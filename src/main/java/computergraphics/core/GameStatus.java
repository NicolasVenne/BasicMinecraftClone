package computergraphics.core;

public enum GameStatus {
    INIT(0),
    OK(10),
    EXIT(11),

    GLError(20);


    private int statusCode;

    GameStatus(int code) {
        statusCode = code;
    }

    public int getCode() {
        return statusCode;
    }
}