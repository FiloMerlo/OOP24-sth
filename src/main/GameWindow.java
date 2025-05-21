package main;

import javax.swing.*;

public class GameWindow extends JFrame {
    private final GamePanel panel;

    public GameWindow() {
        setTitle("Sonic Game");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        panel = new GamePanel();
        add(panel);
        pack();
        setLocationRelativeTo(null);
    }

    public void start() {
        setVisible(true);                  
        panel.requestFocusInWindow();      
        panel.createBufferStrategy(3);     //  Prepara triple buffering
        panel.startGameLoop();             
    }
}