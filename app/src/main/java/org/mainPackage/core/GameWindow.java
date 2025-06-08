package org.mainPackage.core;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class GameWindow extends JFrame {
    private GamePanel gamePanel;
    private Game game;

public GameWindow(String title, GamePanel gamePanel, Game game) {
        
        super(title);
        this.gamePanel = gamePanel;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // oppure DO_NOTHING_ON_CLOSE
        setResizable(true);
        add(gamePanel);
        pack(); // adatta la finestra alla dimensione del pannello
        setLocationRelativeTo(null); // centra la finestra
        setVisible(true);
        gamePanel.requestFocusInWindow();
        
        addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent e) {
                if (game != null) {
                    game.stop();
                    System.out.println("Gioco chiuso correttamente");
                } else {
                    System.err.println("Errore in chiusura gioco");
                    System.exit(0);
                }
            }
        });
    }

}

