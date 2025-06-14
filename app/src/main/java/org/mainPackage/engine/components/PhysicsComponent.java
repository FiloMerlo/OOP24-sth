package org.mainPackage.engine.components;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import org.mainPackage.colliders.Collider;
import org.mainPackage.engine.entities.api.Entity;
import org.mainPackage.engine.events.api.EventType;
import org.mainPackage.engine.events.impl.GameEvent;
import org.mainPackage.enums.direction;


public abstract class PhysicsComponent implements Component{
    protected float xSpeed, ySpeed;
    protected Rectangle2D.Float hitbox;
    protected Entity owner;
    protected ArrayList<Rectangle2D.Float> tiles;
    protected ArrayList<Collider> colliders = new ArrayList<>();
    public PhysicsComponent(float xS, float yS, Entity o, ArrayList<Rectangle2D.Float>tList){
        xSpeed = xS;
        ySpeed = yS;
        owner = o;
        hitbox = new Rectangle2D.Float();
        tiles = tList;
    }
    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }

    public void die(){ 
        GameEvent e = new GameEvent(EventType.ENEMY_DIED, owner);
        e.notify();
    }
    
    public void update(float deltaTime){
        for (Collider coll : colliders) {
            coll.checkCollisions();
        }
    }
    public abstract void setMovement(direction dir, boolean b);
}
