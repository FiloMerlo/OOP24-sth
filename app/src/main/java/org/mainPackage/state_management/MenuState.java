package org.mainPackage.state_management;

import java.awt.*;

import java.awt.event.KeyEvent;

import org.mainPackage.renderer.MenuRenderer;



public class MenuState extends GameState {
    private MenuRenderer menuRenderer;
   
    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);
        System.out.println("MenuState inizializzato.");
        menuRenderer = new MenuRenderer();

    }

    @Override
    public void update() {
        menuRenderer.updateAnimation(System.currentTimeMillis(), System.nanoTime());

        // Logica di aggiornamento per il menu
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int panelWidth = g.getClipBounds().width;  
        int panelHeight = g.getClipBounds().height;
        menuRenderer.render(g2d, panelWidth, panelHeight); 
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) 
        gameStateManager.setState(GameStateManager.State.PLAYING);
    }

    
}