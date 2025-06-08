package org.mainPackage.core;


import org.mainPackage.state_management.GameStateManager;

public class Game {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private GameLoop gameLoop;
    
    private GameStateManager gameStateManager;

    public Game() {
        gameStateManager = new GameStateManager();
        gamePanel = new GamePanel(gameStateManager);
        gameWindow = new GameWindow("Sonic Game", gamePanel, this);
        gameLoop = new GameLoop(gameStateManager,gamePanel);
        //gameStateManager.setGameLoop(gameLoop); /* funzionalità inule */
    }

    public void start() {
        gameLoop.startLoop();
    }

    public void stop(){
        gameLoop.stopLoop();
        gameWindow.dispose();
    }
    
    
    
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}