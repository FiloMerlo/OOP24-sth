package org.mainPackage.engine.components;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import org.mainPackage.colliders.Collider;
import org.mainPackage.engine.entities.impl.EntityImpl;
import org.mainPackage.engine.entities.impl.EntityManagerImpl;
import org.mainPackage.engine.events.api.EventType;
import org.mainPackage.engine.events.impl.GameEvent;
import org.mainPackage.engine.events.impl.SubjectImpl;
import org.mainPackage.enums.direction;


public abstract class PhysicsComponent extends SubjectImpl implements Component{
    protected float xSpeed, ySpeed;
    protected Rectangle2D.Float hitbox;
    protected EntityImpl owner;
    protected ArrayList<Rectangle2D.Float> tiles;
    protected ArrayList<Collider> colliders = new ArrayList<>();
    public PhysicsComponent(float xS, float yS, EntityImpl o, ArrayList<Rectangle2D.Float>tList){
        xSpeed = xS;
        ySpeed = yS;
        owner = o;
        hitbox = new Rectangle2D.Float();
        hitbox.setRect(owner.getComponent(TransformComponent.class).getX(), 
        owner.getComponent(TransformComponent.class).getY(), 
        owner.getComponent(TransformComponent.class).getWidth(), 
        owner.getComponent(TransformComponent.class).getHeight());
        tiles = tList;
        this.addObserver(EntityManagerImpl.getInstance());
    }
    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }

    public void die(){ 
        GameEvent e = new GameEvent(EventType.ENTITY_DEAD, owner);
        notifyObservers(e);
    }
    
    public void update(float deltaTime){
        for (Collider coll : colliders) {
            coll.checkCollisions();
        }
    }
    public abstract void setMovement(direction dir, boolean b);
}
