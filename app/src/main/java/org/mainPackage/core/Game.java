package org.mainPackage.core;


import org.mainPackage.state_management.GameStateManager;
import org.mainPackage.util.SizeView;

public class Game {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private GameLoop gameLoop;
    
    private SizeView sizeView;

    public Game() {
        
        gamePanel = new GamePanel();
        this.sizeView = gamePanel;
        GameStateManager.getInstance().setGameState(sizeView, this ::stop); /* game panel vuole state manager */

        gamePanel.setGameStateManager(GameStateManager.getInstance());
        gameWindow = new GameWindow("Sonic Game", gamePanel, this);
        
        gameLoop = new GameLoop(GameStateManager.getInstance(), gamePanel);
    }

    public void start() {
        gameLoop.startLoop();
    }

    public void stop(){
        gameLoop.stopLoop();
        gameWindow.dispose();
    }
    
}