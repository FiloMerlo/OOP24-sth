package org.mainPackage.core;

import org.mainPackage.engine.systems.GameStateManager;
import org.mainPackage.util.SizeView;


/**
 * Main application class for the Sonic game.
 *
 * This class acts as the entry point and is responsible for initializing
 * the core components of the game, such as the {@link GameWindow},
 * {@link GamePanel}, and {@link GameStateManager}.
 *
 * A callback is registered during initialization to handle specific events.
 * For example, the following line:
 *
 * <pre>
 * this.gamestatemanager.initState(sizeView, this::stop);
 * </pre>
 *
 * registers {@code this::stop} as a method reference. This is equivalent to 
 * a lambda expression {@code () -> this.stop()} and allows the 
 * {@link GameStateManager} to invoke {@link #stop()} whenever necessary,
 * without executing it immediately at initialization time.
 */


public class Game {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private GameStateManager gamestatemanager;
    private GameLoop gameLoop;
    private SizeView sizeView;
    
    /**
     * Creates a new Game instance, setting up the panel, window,
     * state manager, and starting the main game loop.
     */
    
     public Game() {
        this.gamestatemanager = GameStateManager.getInstance();
        this.gamePanel = new GamePanel(GameStateManager.getInstance());
        this.sizeView = gamePanel;
        this.gamestatemanager.initState(sizeView, this ::stop);
        this.gameWindow = new GameWindow("Sonic Game", gamePanel, this);
        this.gameLoop = new GameLoop(GameStateManager.getInstance(), gamePanel);
        gameLoop.startLoop();

    }

    /**
    * Manually start the game loop.
    *
    * This method is not currently used because the game loop is
    * automatically started by the {@code Game} class constructor.
    * However, it is retained to make the code more readable and extensible.
    *
    * For example, in future scenarios, you might decide to initialize the game 
    * without starting it immediately, leaving the caller with explicit control over when it starts.
    */
    public void start() {
        gameLoop.startLoop();
    }

    /**
     * Stops the game loop and disposes the window,
     * ensuring resources are released properly.
     */

    public void stop(){
        gameLoop.stopLoop();
        gameWindow.dispose();
    }
    
}