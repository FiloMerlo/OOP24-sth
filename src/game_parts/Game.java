package game_parts;
public class Game {
    private GameWindow gameWindow;
    private GamePanel gamePanel;

    public Game() {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        initClasses();
        //Qui si fa partire il Gameloop
    }
}
