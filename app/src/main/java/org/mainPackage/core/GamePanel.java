package org.mainPackage.core;

import javax.swing.*;

import org.mainPackage.util.SizeView;
import org.mainPackage.state_management.*;

import java.awt.event.KeyAdapter;    
import java.awt.event.KeyEvent;      
import java.awt.event.MouseAdapter;  
import java.awt.event.MouseEvent;
import org.mainPackage.engine.systems.InputManager;

import java.awt.event.MouseMotionAdapter;
import java.awt.*;

public class GamePanel extends JPanel implements SizeView {

    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 600;

    private GameStateManager gameStateManager;
    

    public GamePanel() {
        this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT)); 
        this.setFocusable(true);
        //this.requestFocusInWindow();
        this.setDoubleBuffered(true); // migliora la qualità del rendering 
        

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                gameStateManager.keyPressed(e);
                System.out.println("DEBUG: Key Pressed in GamePanel: " + KeyEvent.getKeyText(e.getKeyCode()));
                InputManager.getInstance().keyPressed(e); // Passa l'evento a InputManager
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                gameStateManager.keyReleased(e);
                System.out.println("DEBUG: Key Released in GamePanel: " + KeyEvent.getKeyText(e.getKeyCode()));
                InputManager.getInstance().keyReleased(e); // Passa l'evento a InputManager
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                gameStateManager.mousePressed(e);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
        @Override
        public void mouseMoved(MouseEvent e) {
        gameStateManager.mouseMoved(e);
    }
});
    }

    /* Setting del GameStateMangaer dopo aver inizializzato il gamePanel */
    public void setGameStateManager(GameStateManager gameStateManager){
        this.gameStateManager = gameStateManager;
    }

    /* metodi per ottenere le dimensioni del panello di gioco */

    @Override
    public int getSizeWidth() {
        return getSize().width;
    }

    @Override
    public int getSizeHeight() {
        return getSize().height;
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
       
        g2d.dispose();
        /* Spostata la logica del renderer delle tites e delle entità nel PlayingRenderer */

    }
        
}
