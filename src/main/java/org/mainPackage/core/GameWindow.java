package org.mainPackage.core;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Represents the game window for the application.
 * 
 * <p>This class extends {@link JFrame} and is responsible for displaying
 * the {@link GamePanel}, handling window events, and managing integration 
 * with the {@link Game} instance.</p>
 * 
 * <p>Main responsibilities:
 * <ul>
 *   <li>Sets up the main frame properties.</li>
 *   <li>Embeds the {@code GamePanel} as the main drawing surface.</li>
 *   <li>Handles the window closing event to properly stop the game loop.</li>
 * </ul>
 * </p>
 */

public class GameWindow extends JFrame {
    private GamePanel gamePanel;
    private Game game;

    /**
     * Constructs a new GameWindow with the specified title, game panel, and game instance.
     *
     * @param title the title of the window
     * @param gamePanel the panel that will display the game content
     * @param game the game instance used to stop the loop on window close
     */

    public GameWindow(String title, GamePanel gamePanel, Game game) {
        super(title);
        this.gamePanel = gamePanel;
        
        // --- Frame setup ---
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        add(gamePanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // --- Ensure the game panel has focus ---
        gamePanel.requestFocusInWindow();
        
        // --- Window listener for closing event ---
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (game != null) {
                    game.stop();
                } else {
                    System.exit(0);
                }
            }
        });
    }
}

