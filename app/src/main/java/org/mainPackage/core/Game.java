package org.mainPackage.core;


import org.mainPackage.engine.systems.GameStateManager;
import org.mainPackage.util.SizeView;

public class Game {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private GameStateManager gamestatemanager;
    private GameLoop gameLoop;
    
    private SizeView sizeView;

    public Game() {

        this.gamestatemanager = GameStateManager.getInstance();
        this.gamePanel = new GamePanel(GameStateManager.getInstance());
        this.sizeView = gamePanel;
        this.gamestatemanager.initState(sizeView, this ::stop); // Inizializza lo stato di gioco con SizeView e shutdownGame
        this.gameWindow = new GameWindow("Sonic Game", gamePanel, this);
        System.out.println("Game: Inizializzazione del Game con GamePanel e GameWindow.");
        this.gameLoop = new GameLoop(GameStateManager.getInstance(), gamePanel);
        
        gameLoop.startLoop();

    }
    //questo non servirebbe a nulla, ma lo metto per completezza
    
    public void start() {
        gameLoop.startLoop();
    }

    public void stop(){
        gameLoop.stopLoop();
        gameWindow.dispose();
    }
    
}