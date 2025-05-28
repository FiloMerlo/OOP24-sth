package colliders;
import java.awt.Rectangle;
import java.util.ArrayList;

import entities.*;

public abstract class Collider{
    /*I Collider sono praticamente dei JPanel che contengono un rettangolo che funge da sensore per le collisioni.
      estendendo JPanel possono essere posizionati dentro ad un altro Jpanel con il sistema di allineamento.*/
    private Rectangle sensor; //usato per rilevare se uno spazio è occupato da una Tile
    private ArrayList<Tile> tiles;
    private Object owner;
    /*Nelle sottoclassi saranno le differenze nei parametri deicostruttori 
    a differenziare i vari Collider, oltre alla funzione collide()*/
    public Collider(int width, int height, ArrayList<Tile> list, Object obj){
        owner = obj;
        sensor = new Rectangle(owner.getX(), owner.getY(), width, height);
        tiles = list;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getSensor() != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.ORANGE);
            g2d.fill(getSensor());
        }
    }   

    public abstract void checkCollisions();
    public abstract void collision();

    public void updatePos(){
        sensor.setLocation(owner.getX(), owner.getY());
    }
    public Rectangle getSensor(){
        return sensor;
    }
    public Object getOwner(){
        return owner;
    }

}
