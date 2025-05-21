package main;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ExitHandler extends MouseAdapter {
    private final GamePanel panel;

    public ExitHandler(GamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (panel.state == GameState.PAUSED && panel.getExitButton().contains(e.getPoint())) {
            panel.stopGameLoop();
            SwingUtilities.getWindowAncestor(panel).dispose();
            System.exit(0);
        }
    }
}
