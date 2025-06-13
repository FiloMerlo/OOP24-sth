package org.mainPackage.colliders;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.mainPackage.engine.components.PhysicsComponent;

public abstract class Collider{
    /*Colliders are objects that contains a sensor used to detect collisions and all the relative methods*/
    protected Rectangle2D.Float sensor;
    protected ArrayList<Rectangle2D.Float> tiles;
    protected PhysicsComponent physic;
    public Collider(ArrayList<Rectangle2D.Float> list, PhysicsComponent phy){
        tiles = list;
        physic = phy;
        sensor = physic.getHitbox();
    }

    public abstract void checkCollisions();

    public PhysicsComponent getPhysic(){
        return physic;
    }
    public Rectangle2D.Float getSensor(){
        return sensor;
    }

}
