package org.mainPackage.state_management;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class GameState {
    protected GameStateManager gameStateManager;// Già presente per la comunicazione tra stati

    public GameState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    /* Metodi implemetati da ogni stato */
    public abstract void update();
    public abstract void draw(Graphics g);

    /* Metodi che possono essere implementati oppure no */
    public void keyPressed(KeyEvent e) {} // Implementazione vuota di default
    
    public void keyReleased(KeyEvent e) {} // Implementazione vuota di default
    
    public void mouseClicked(MouseEvent e) {} // Implementazione vuota di default
    
}