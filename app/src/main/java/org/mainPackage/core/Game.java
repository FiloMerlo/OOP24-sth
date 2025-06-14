package org.mainPackage.core;


import org.mainPackage.state_management.GameStateManager;
import org.mainPackage.util.SizeView;

public class Game {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private GameLoop gameLoop;
    
    private GameStateManager gameStateManager;
    private SizeView sizeView;

    public Game() {
        
        gamePanel = new GamePanel();
        this.sizeView = gamePanel;
        gameStateManager = new GameStateManager(sizeView, this ::stop); /* game panel vuole state manager */

        gamePanel.setGameStateManager(gameStateManager);
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
    
    public GameStateManager getGameStateManager(){
        return gameStateManager;
    }
    
}