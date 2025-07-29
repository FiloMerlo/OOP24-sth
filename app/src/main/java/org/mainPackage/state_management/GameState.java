package org.mainPackage.state_management;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.mainPackage.engine.events.impl.SubjectImpl;
import org.mainPackage.engine.systems.GameStateManager;
import org.mainPackage.util.SizeView;

public abstract class GameState extends SubjectImpl {
    protected GameStateManager gameStateManager;// Già presente per la comunicazione tra stati
    protected SizeView sizeView;
    
    public GameState(GameStateManager gameStateManager, SizeView sizeView) {
        this.gameStateManager = gameStateManager;
        this.sizeView = sizeView;
    }

    /* Metodi implemetati da ogni stato */
    public abstract void update();
    public abstract void draw(Graphics g);

    /* Metodi mantenuti per leggibilità e utili per possibili aggiornamenti*/
    public void keyPressed(KeyEvent e) {} 
    
    public void keyReleased(KeyEvent e) {} 
    
    /* Metodi implementati dal Paused State per il bottone EXIT */
    public void mousePressed(MouseEvent e) {}
    

    public void mouseMoved(MouseEvent e) {}
}