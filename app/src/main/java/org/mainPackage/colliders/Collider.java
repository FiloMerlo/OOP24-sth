package org.mainPackage.colliders;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.mainPackage.engine.components.PhysicsComponent;

public abstract class Collider{
    /*Colliders are objects that contains a sensor used to detect collisions and all the relative methods*/
    protected Rectangle sensor;
    protected ArrayList<Rectangle> tiles;
    protected PhysicsComponent physic;
    public Collider(ArrayList<Rectangle> list, PhysicsComponent phy){
        tiles = list;
        physic = phy;
        sensor = physic.getHitbox();
    }

    public abstract void checkCollisions();

    public PhysicsComponent getPhysic(){
        return physic;
    }
    public Rectangle getSensor(){
        return sensor;
    }

}
