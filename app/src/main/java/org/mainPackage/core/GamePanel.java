package org.mainPackage.core;

import javax.swing.*;

import java.awt.event.KeyAdapter;    
import java.awt.event.KeyEvent;      
import java.awt.event.MouseAdapter;  
import java.awt.event.MouseEvent;    


import java.awt.*;

public class GamePanel extends JPanel {

    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 600;

    private GameStateManager gameStateManager;

    public GamePanel(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        
        this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT)); 
        this.setFocusable(true);
        //this.requestFocusInWindow();
        this.setDoubleBuffered(true); // migliora la qualità del rendering 
        

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                gameStateManager.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                gameStateManager.keyReleased(e);
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameStateManager.mouseClicked(e);
            }
        });
    }

   

    // rendering grafico
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        // Impostazioni di rendering per renderle migliori
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Render dello stato corrente
        gameStateManager.draw(g2d);
        if (g2d != null) {
            g2d.dispose();
        }
       
    }
        
}
