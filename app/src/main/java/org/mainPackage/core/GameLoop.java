package org.mainPackage.core;

import org.mainPackage.state_management.GameStateManager;

public class GameLoop implements Runnable {

    private final int FPS_SET = 60;
    private final int UPS_SET = 120;

    private GamePanel gamePanel;
    private Thread thread;
    private volatile boolean running = false;
    //private volatile boolean paused = false;
    
    private GameStateManager gameStateManager;
  

    public GameLoop(GameStateManager gameStateManager, GamePanel gamePanel) {
        this.gameStateManager = gameStateManager;
        this.gamePanel = gamePanel;
    }


    public void startLoop() {
        if (thread == null || !thread.isAlive()) {
            running = true; 
            thread = new Thread(this);
            thread.start();
        }
    }
    
    public void stopLoop() {
        running = false;
        try {
            if (thread != null) {
                thread.join();
                System.out.println("Thread interroto");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /*public void pauseLoop(){
        System.out.println("gameloop stop");
        paused = true;
    }

    public void resumeLoop (){
        System.out.println("gameloop ripreso");
        paused = false;
    } */

    @Override
    public void run() {

        double timeForFrame = 1_000_000_000.0 / FPS_SET; // calcolo i tempi in nanosecondi
        double timeForUpdate = 1_000_000_000.0 / UPS_SET;
        
        long previousTime = System.nanoTime();
        double deltaUPS = 0;
        double deltaFPS = 0;
        
        /* etichette per debug */
        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();
        /* */
        
        while (running) {
            long currentTime = System.nanoTime();

            deltaUPS += (currentTime - previousTime) / timeForUpdate;
            deltaFPS += (currentTime - previousTime) / timeForFrame;
            previousTime = currentTime;

            //if (!paused){
                while (deltaUPS >= 1) {
                    update(); /* questo */
                    updates++;
                    deltaUPS--;
                }
            //}

            if (deltaFPS >= 1) {
                gamePanel.repaint(); //triggera il paint Component 
                frames++;
                deltaFPS--;
            }

            /* stampa per debug */
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    private void update(){ /* delego l'update */
        gameStateManager.update();
    }
}


