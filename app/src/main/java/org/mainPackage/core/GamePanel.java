package org.mainPackage.core;

import javax.swing.*;

import org.mainPackage.util.SizeView;

import java.awt.event.KeyAdapter;    
import java.awt.event.KeyEvent;      
import java.awt.event.MouseAdapter;  
import java.awt.event.MouseEvent;

import org.mainPackage.engine.systems.GameStateManager;
import org.mainPackage.engine.systems.InputManager;

import java.awt.event.MouseMotionAdapter;
import java.awt.*;

public class GamePanel extends JPanel implements SizeView {

    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 600;

    private GameStateManager gameStateManager;
    

    public GamePanel(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT)); 
        this.setFocusable(true);
        //this.requestFocusInWindow();
        this.setDoubleBuffered(true); // migliora la qualità del rendering 
        
        /* Gli input vengono gestiti tramite l'input manager e il gamestatemanager, quest' ultimo si occupa del Menu mentre il resto viene delegato direttamente 
         * all' InputManager. Questo permette di avere un sistema di input centralizzato e facilmente estendibile per i vari stati.
         */
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameStateManager.getEnumState() == GameStateManager.State.MENU) {
                    gameStateManager.keyPressed(e); // Passa l'evento a GameStateManager
                    System.out.println("GAME PANEL : Key Pressed in GamePanel (Menu): " + KeyEvent.getKeyText(e.getKeyCode()));
                } else {
                    System.out.println("GAME PANEL : Key Pressed in GamePanel (Playing o Paused): " + KeyEvent.getKeyText(e.getKeyCode()));                    InputManager.getInstance().keyPressed(e); // Passa l'evento a InputManager
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (gameStateManager.getEnumState() == GameStateManager.State.MENU) {
                    gameStateManager.keyReleased(e); // Passa l'evento a GameStateManager
                    System.out.println("GAME PANEL : Key Released in GamePanel (Menu): " + KeyEvent.getKeyText(e.getKeyCode()));
                } else {
                    System.out.println("GAME PANEL : Key Released in GamePanel (Playing o Paused): " + KeyEvent.getKeyText(e.getKeyCode()));
                    InputManager.getInstance().keyReleased(e); // Passa l'evento a InputManager
                }
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
        System.out.println("GamePanel - setGameStateManager chiamato.");
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
