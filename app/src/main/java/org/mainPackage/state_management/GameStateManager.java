package org.mainPackage.state_management;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import org.mainPackage.util.SizeView;
import org.mainPackage.engine.components.GoalComponent;
import org.mainPackage.engine.entities.api.Entity;
import org.mainPackage.engine.entities.impl.EntityImpl;
import org.mainPackage.engine.events.api.Event;
import org.mainPackage.engine.events.api.Observer;
import org.mainPackage.engine.events.impl.SubjectImpl;

/**
 * Gestisce i diversi stati del gioco (es. menu, gioco, pausa).
 * Si occupa di delegare l'aggiornamento e il disegno dello stato corrente. Delega anche gli input
 */
public class GameStateManager implements Observer {

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

        playingState = new PlayingState(this, sizeView, sonicEntity, levelGrid, tileWorldSize);
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


    @Override
    public void onNotify(Event e) {
        // TODO Auto-generated method stub
        switch(e.getType()){
            case GAME_OVER:
                break;
            case LEVEL_COMPLETED:
                break;
            case LEVEL_STARTED:
                break;
            case PAUSE:
                break;
            case RESUME:
                break;
            default:
                break;
            
        }
    }

}