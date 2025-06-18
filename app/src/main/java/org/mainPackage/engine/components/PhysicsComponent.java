package org.mainPackage.engine.components;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.mainPackage.engine.entities.impl.EntityImpl;
import org.mainPackage.engine.entities.impl.EntityManagerImpl;
import org.mainPackage.engine.events.api.EventType;
import org.mainPackage.engine.events.impl.GameEvent;
import org.mainPackage.engine.events.impl.SubjectImpl;
import org.mainPackage.enums.direction;


public abstract class PhysicsComponent extends SubjectImpl implements Component{
    protected float xSpeed = 0, ySpeed = 0;
    protected TransformComponent hitbox;
    protected EntityImpl owner;
    protected ArrayList<Rectangle2D.Float> tiles;
    public PhysicsComponent(EntityImpl o, ArrayList<Rectangle2D.Float>tList){
        owner = o;
        hitbox = owner.getComponent(TransformComponent.class);
        tiles = tList;
        this.addObserver(EntityManagerImpl.getInstance());
    }
    public void die(){ 
        GameEvent e = new GameEvent(EventType.ENTITY_DEAD, owner);
        notifyObservers(e);
    }

    public abstract void update(float deltaTime);
    
    public boolean canGoThere(direction dir, float distance){
        /*This method is to be used for both movemnt of X axis and Y axis*/
        Rectangle2D.Float wannaBeThere = new Rectangle2D.Float();
        if (dir == direction.right || dir == direction.left){
            wannaBeThere.setRect(hitbox.getX() + distance, hitbox.getY(), hitbox.getWidth(), hitbox.getHeight());
        } else if (dir == direction.up || dir == direction.down){
            wannaBeThere.setRect(hitbox.getX(), hitbox.getY() + distance, hitbox.getWidth(), hitbox.getHeight());
        }
    
        /*check if moving as the entity wants to would cause it to compenetrate in any tile*/
        for (Rectangle2D.Float tile : tiles) {
            if (wannaBeThere.intersects(tile)){
                return false;
            }
        }

        return true;
    }
    
    public EntityImpl getOwner() {
        return owner;
    }
    public TransformComponent getHitbox(){
        return hitbox;
    }
    
}
