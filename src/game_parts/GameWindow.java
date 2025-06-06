package game_parts;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.JFrame;

public class GameWindow {
    private JFrame frame;

    public GameWindow(Character character){
        frame = new JFrame();
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.add(character);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowFocusListener(new WindowFocusListener() {
            public void windowLostFocus(WindowEvent e){
                character.focusLost();
            }
        });
    }
}