package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ButtonsHandler extends KeyAdapter {
    private final GamePanel panel;

    public ButtonsHandler(GamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();

        if (c == KeyEvent.VK_ENTER && panel.state == GameState.HOME) {
            panel.state = GameState.PLAYING;
        } else if (c == KeyEvent.VK_P) {
            if (panel.state == GameState.PLAYING) {
                panel.state = GameState.PAUSED;
            } else if (panel.state == GameState.PAUSED) {
                panel.state = GameState.PLAYING;
            }
        }
    }
}
