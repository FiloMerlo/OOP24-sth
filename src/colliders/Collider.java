package colliders;
import java.awt.Rectangle;
import java.util.ArrayList;

import components.Physics;
import entities.Entity;

import entities.*;

public abstract class Collider{
    /*I Collider sono praticamente dei JPanel che contengono un rettangolo che funge da sensore per le collisioni.
      estendendo JPanel possono essere posizionati dentro ad un altro Jpanel con il sistema di allineamento.*/
    protected Rectangle sensor; //usato per rilevare se uno spazio è occupato da una Tile
    protected ArrayList<Rectangle> tiles;
    protected Physic physic;
    /*Nelle sottoclassi saranno le differenze nei parametri deicostruttori 
    a differenziare i vari Collider, oltre alla funzione collide()*/
    public Collider(ArrayList<Rectangle> list, Physics p){
        tiles = list;
        physic = p;
    }

    public abstract void checkCollisions();

    public Object getphysic(){
        return physic;
    }
    public Object getSensor(){
        return sensor;
    }

}
