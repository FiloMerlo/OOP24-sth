package org.mainPackage.state_management;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import org.mainPackage.util.SizeView;

/**
 * Gestisce i diversi stati del gioco (es. menu, gioco, pausa).
 * Si occupa di delegare l'aggiornamento e il disegno dello stato corrente. Delega anche gli input
 */
public class GameStateManager {

    private GameState currentState; 
    
    //private GameLoop gameLoop /* funzione di pausa gameLoop inutile */
    private PlayingState playingState;
    private PausedState pausedState;
    private SizeView sizeView;
    private Runnable shutdownGame;

  
    public enum State {
        MENU,
        PLAYING,
        PAUSED
        
    }


    public GameStateManager(SizeView sizeView, Runnable shutdowGame) {
        this.sizeView = sizeView;
        this.shutdownGame = shutdowGame;

        playingState = new PlayingState(this, sizeView);
        pausedState = new PausedState(this, sizeView); // da passare il sizeview
        setState(State.MENU);

    }

    
    public void setState(State state) {
        
        switch (state) {
            case MENU:
                currentState = new MenuState(this,sizeView); 
                break;
            case PLAYING:
                currentState = playingState; 
                break;
            case PAUSED:
                currentState = pausedState;
                break;
        }
        System.out.println("Stato cambiato in: " + state);
    }

    
    public GameState getCurrentState() {
        return currentState;
    }

    
    public void update() {
        if (currentState != null) 
            currentState.update();
    }

    
    public void draw(Graphics g) {
        if (currentState != null) {
            currentState.draw(g);
        } else {
            System.out.println("GameStateManager: nessuno stato da disegnare.");
        }
    }


    public void gameShutdown() {
        if (shutdownGame != null) {
            shutdownGame.run(); 
        }
    }


    public void keyPressed(KeyEvent e) {
        if (currentState != null) {
            currentState.keyPressed(e);
        }
    }


    public void keyReleased(KeyEvent e) {
        if (currentState != null) {
            currentState.keyReleased(e);
        }
    }

    
    public void mousePressed(MouseEvent e) {
        if (currentState != null) {
            currentState.mousePressed(e);
        }
    }
    
    public void mouseMoved (MouseEvent e) {
        if (currentState != null) {
            currentState.mouseMoved(e);
        }
    }

}