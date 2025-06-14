package org.mainPackage.state_management;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import org.mainPackage.util.SizeView;

import org.mainPackage.engine.entities.api.Entity;

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

    /* Campi per il playingState */
    private Entity sonicEntity;
    private int[][] levelGrid;
    private int tileWorldSize;

  
    public enum State {
        MENU,
        PLAYING,
        PAUSED
        
    }


    public GameStateManager(SizeView sizeView, Runnable shutdowGame) {
        this.sizeView = sizeView;
        this.shutdownGame = shutdowGame;

        
        setState(State.MENU);

    }

    public void initState(Entity sonicEntity, int[][] levelGrid, int tileWorldSize) {
        this.sonicEntity = sonicEntity;
        this.levelGrid = levelGrid;
        this.tileWorldSize = tileWorldSize;

        this.playingState = new PlayingState(this, sizeView, sonicEntity, levelGrid, tileWorldSize);
        this.pausedState = new PausedState(this, sizeView);

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

    
    /* Metodi per playing state */
    public void setSonicEntity(Entity Sonic) {
        this.sonicEntity = Sonic;
    }

    public void setLevelGrid(int[][] grid) {
        this.levelGrid = grid;
    }

    public void setTileWorldSize(int size) {
        this.tileWorldSize = size;
    }

    
    public Entity getSonicEntity() {
        return this.sonicEntity;
    }

    public int[][] getLevelGrid() {
        return this.levelGrid;
    }

    public int getTileWorldSize() {
        return this.tileWorldSize;
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