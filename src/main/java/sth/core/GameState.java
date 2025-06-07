package sth.core;

public class GameState {
    enum GameStateEnum {
        PLAYING,
        PAUSED,
        GAME_OVER
    }
    private static GameStateEnum State;
    public GameState() {
        State = GameStateEnum.PLAYING;
    }

    public boolean isOn() {
        return State == GameStateEnum.PLAYING ? true : false;
    }

    public void turnOnOff() {
        if (State == GameStateEnum.PLAYING) {
            State = GameStateEnum.PAUSED;
        } else if (State == GameStateEnum.PAUSED) {
            State = GameStateEnum.PLAYING;
        }
    }
    public void setGameOver() {
        State = GameStateEnum.GAME_OVER;
    }
}
