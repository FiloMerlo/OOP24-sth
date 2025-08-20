package org.mainPackage.core;

import org.mainPackage.engine.systems.GameStateManager;


/**
 * Manages the main game loop with separate update and rendering cycles.
 * Implements a fixed timestep game loop pattern with configurable FPS and UPS
 * 
 */
 
 public class GameLoop implements Runnable {
    
    // --- Target frames per second for rendering ---
    private static final int FPS_SET = 60;
    // --- Target updates per second for game logic ---
    private static final int UPS_SET = 120;
    // --- Conversion factor for nanoseconds to seconds ---
    private static final double NANOS_PER_SECOND = 1_000_000_000.0;
    // --- Interval for debug output in milliseconds ---
    private static final long DEBUG_INTERVAL = 1000;

    private GamePanel gamePanel;
    private Thread thread;
    private GameStateManager gameStateManager;
    private volatile boolean running = false;
    
    /**
     * Constructs a new GameLoop with the specified components.
     * 
     * @param gameStateManager The game state manager to update
     * @param gamePanel The game panel to repaint
     */

    public GameLoop(GameStateManager gameStateManager, GamePanel gamePanel) {
        this.gameStateManager = gameStateManager;
        this.gamePanel = gamePanel;
    }
    
    /** 
     * Starts the game loop thread if it is not already running.
     * This method initializes the thread and starts it.
     */
    
    public void startLoop() {
        if (thread == null || !thread.isAlive()) {
            running = true; 
            thread = new Thread(this);
            thread.start();
        }
    }
    
    /**
     * Stops the game loop and waits for the thread to terminate.
     */

    public void stopLoop() {
        running = false;
        try {
            if (thread != null) {
                thread.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
   
    /**
     * Main game loop implementation using fixed timestep pattern.
     * Separates update frequency from render frequency for consistent gameplay.
     */
    
    @Override
    public void run() {
        final double timeForFrame = NANOS_PER_SECOND/ FPS_SET;
        final double timeForUpdate = NANOS_PER_SECOND/ UPS_SET;
        
        long previousTime = System.nanoTime();
        double deltaUPS = 0;
        double deltaFPS = 0;
        
        // --- Counters for debug frames and updates  ---
        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();
        
        while (running) {
            long currentTime = System.nanoTime();
            // --- Calculate time deltas for updates and frames ---
            deltaUPS += (currentTime - previousTime) / timeForUpdate;
            deltaFPS += (currentTime - previousTime) / timeForFrame;
            previousTime = currentTime;

            // --- Process updates at fixed rate ---
            while (deltaUPS >= 1.0) {
                update(); 
                updates++;
                deltaUPS--;
            }
            
            // --- Render at target FPS ---
            if (deltaFPS >= 1.0) {
                gamePanel.repaint(); // Triggers a repaint of the game panel
                frames++;
                deltaFPS--;
            }

            // --- Outputs debug information about FPS and UPS at regular intervals ---
            if (System.currentTimeMillis() - lastCheck >= DEBUG_INTERVAL) {
                lastCheck = System.currentTimeMillis();
                //System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
            
        }
    }

    /**
     * Updates the game logic by delegating to the GameStateManager.
     */
    private void update(){
        gameStateManager.update();
    }
}


