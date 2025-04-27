package sth.core;

public class Game {
    public static void main(String[] args) {
        // Initialize the game
        GameStateManager gameStateManager = new GameStateManager();
        Window window = new Window(gameStateManager);

        double deltaTime = 0.016;
        // Start the game loop
        while (true) {
            // Update game state
            gameStateManager.update();
            
            // Render the window
            // window.render();
        }
    }
}
