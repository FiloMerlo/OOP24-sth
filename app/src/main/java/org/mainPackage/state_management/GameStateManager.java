package org.mainPackage.state_management;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;



/**
 * Gestisce i diversi stati del gioco (es. menu, gioco, pausa).
 * Si occupa di delegare l'aggiornamento e il disegno dello stato corrente. Delega anche gli input
 */
public class GameStateManager {

    private GameState currentState; 
    
    //private GameLoop gameLoop /* funzione di pausa gameLoop inutile */
    private PlayingState playingState;
    private PausedState pausedState;

  
    public enum State {
        MENU,
        PLAYING,
        PAUSED
        
    }


    public GameStateManager() {

        playingState = new PlayingState(this);
        pausedState = new PausedState(this);
        setState(State.MENU);

    }

    
    public void setState(State state) {
        
        switch (state) {
            case MENU:
                
                currentState = new MenuState(this); 
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

    /*public void setGameLoop(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }

    
    public GameLoop getGameLoop() {
        return gameLoop;
    }
        metodi inutili al momento */

    
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

    
    public void mouseClicked(MouseEvent e) {
        if (currentState != null) {
            currentState.mouseClicked(e);
        }
    }


}
