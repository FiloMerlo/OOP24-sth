package colliders;
import java.awt.Rectangle;
import java.util.ArrayList;
import entities.*;

public abstract class Collider extends javax.swing.JPanel{
    /*I Collider sono praticamente dei JPanel che contengono un rettangolo che funge da sensore per le collisioni.
      estendendo JPanel possono essere posizionati dentro ad un altro Jpanel con il sistema di allineamento.*/
    private Rectangle sensor;
    private List<Tile> tiles;
    private Sonic sonic;
    private boolean colliding;
    /*Nelle sottoclassi saranno le differenze nei parametri deicostruttori 
    a differenziare i vari Collider, oltre alla funzione collide()*/
    public Collider(int width, int height, ArrayList<Tile> list, Sonic s){
        sensor = new Rectangle(0, 0, width, height);
        tiles = list;
        Sonic = s;
        colliding = false;
    }
    public Rectangle getSensor(){
        return sensor;
    }
    //Verifica
    public void check(){
        for (Tile tile : tiles) {
            if(sensor.intersects(t.getSensor()) && colliding == false){
                collision();
            } else if (sensor.intersects(t.getSensor()) == false && colliding){
                collEnded();
            }
        }
    }
    public void collision(){
        colliding = true;
    }
    public void collEnded(){
        colliding = false;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Rectangle rect = new Rectangle(0, 0, getWidth(), getHeight());
        g2d.setColor(Color.ORANGE);
        g2d.fill(rect);
    }
}
