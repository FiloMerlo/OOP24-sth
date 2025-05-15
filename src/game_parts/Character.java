package game_parts;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import PlayerInputs;
public enum action {idle, walking, running, falling, damage, crouch};
public class Character extends JPanel{
    private int xPos, yPos, width, height;
    private BufferedImage img;
    private ArrayList<ArrayList<BufferedImage>> animations; //potrebbe funzionare anche sotto forma di matrice credo ...
    private Physics physics;
    private action playerAction = idle;
    private boolean left, down, right;
    public Character(){

        addKeyListener(new PlayerInputs(this));
    }
    public SonicPhysics getPhysics(){
        return physics;
    }

    public void moveX(int movement){
        this.xPos += movement;
    }
    public void moveY(int movement){
        this.yPos += movement;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.fillRect(100 + xPos, 100 + yPos, 10, 10);
    }

    public focusLost(){   //quando esco dalla pagina il personaggio si ferma forzatamente. Se e quando la implementeremo, questo metodo metterà forzatamente il gioco in pausa.
        sonic.setLeft(false);
        sonic.setRight(false);
        sonic.setDown(false);
    }
}
