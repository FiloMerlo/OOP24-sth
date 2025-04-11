package main;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class GameWindow {
    private javax.swing.JFrame jframe;

    public GameWindow(Character character){
        jframe = new JFrame();
        jframe.setSize(400, 400);
        jframe.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        jframe.add(character);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
        jframe.addWindowFocusListener(new WindowFocusListener() {
            public void windowLostFocus(WindowEvent e){
                character.focusLost();
            }
        });
    }
}