package main;

import javax.swing.SwingUtilities;

public class Launch {
    public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        // Crea e mostra la finestra di gioco
        GameWindow window = new GameWindow();
        window.start(); 
        });
    }
}
