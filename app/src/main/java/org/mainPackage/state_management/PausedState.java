package org.mainPackage.state_management;

import java.awt.*;
import java.awt.event.KeyEvent;
//import java.awt.event.MouseEvent; 


import org.mainPackage.renderer.PausedRenderer;




public class PausedState extends GameState {
    
    private PausedRenderer pausedRenderer;
    /*
    private Rectangle exitButtonBounds;  
    private boolean hoveringExit = false; 
     */
    
    public PausedState(GameStateManager gameStateManager) {
        super(gameStateManager);
        System.out.println("PausedState inizializzato.");
        pausedRenderer = new PausedRenderer();

    }
    
    @Override
    public void update() { 

    }
    
    @Override
    public void draw(Graphics g) { 
      
        if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;
            int panelWidth = g.getClipBounds().width;
            int panelHeight = g.getClipBounds().height;
            
            /* 
            int buttonWidth = (int) (panelWidth * 0.2);
            int buttonHeight = (int) (panelHeight * 0.1);
            int buttonX = (panelWidth - buttonWidth) / 2;
            int buttonY = (int) (panelHeight * 0.7); 
            exitButtonBounds.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
            */

           
            pausedRenderer.render(g2d, panelWidth, panelHeight);
        } else {
            System.err.println("Error: Graphics not Graphics2D in PausedState.draw");
        }
    }
    
    
    
    @Override
    public void keyPressed(KeyEvent e) { 
        if (e.getKeyCode() == KeyEvent.VK_P) 
        gameStateManager.setState(GameStateManager.State.PLAYING);
        //gameStateManager.getGameLoop().resumeLoop();
    }

}
