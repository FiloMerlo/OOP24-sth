package org.mainPackage.state_management;

import java.awt.*;

import java.awt.event.KeyEvent;

import org.mainPackage.util.SizeView;
import org.mainPackage.renderer.MenuRenderer;




public class MenuState extends GameState {
    private MenuRenderer menuRenderer;
   
   
    public MenuState(GameStateManager gameStateManager, SizeView sizeView) {
        super(gameStateManager,sizeView);
        System.out.println("MENU STATE: MenuState inizializzato.");
        menuRenderer = new MenuRenderer();

    }

    @Override
    public void update() {
       menuRenderer.updateAnimation();
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int panelWidth = sizeView.getSizeWidth(); 
        int panelHeight = sizeView.getSizeHeight();
        menuRenderer.render(g2d, panelWidth, panelHeight); 
    }

    
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) 
        gameStateManager.setState(GameStateManager.State.PLAYING);
    }
    /* Nel menu non è necessario gestire questi input */
    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("DEBUG: MenuState - keyReleased: " + KeyEvent.getKeyText(e.getKeyCode()));
    }

}
