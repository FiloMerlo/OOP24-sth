package org.mainPackage.colliders;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.mainPackage.engine.components.PhysicsComponent;

public abstract class Collider{
    /*I Collider sono praticamente dei JPanel che contengono un rettangolo che funge da sensore per le collisioni.
      estendendo JPanel possono essere posizionati dentro ad un altro Jpanel con il sistema di allineamento.*/
    protected Rectangle sensor; //usato per rilevare se uno spazio è occupato da una Tile
    protected ArrayList<Rectangle> tiles;
    protected PhysicsComponent physic;
    /*Nelle sottoclassi saranno le differenze nei parametri deicostruttori 
    a differenziare i vari Collider, oltre alla funzione collide()*/
    public Collider(ArrayList<Rectangle> list, PhysicsComponent phy){
        tiles = list;
        physic = phy;
    }

    public abstract void checkCollisions();

    public PhysicsComponent getPhysic(){
        return physic;
    }
    public Rectangle getSensor(){
        return sensor;
    }

}
