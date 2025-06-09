package org.mainPackage.engine.components;

import java.awt.Rectangle;
import java.util.ArrayList;
import org.mainPackage.game_parts.action;
import org.mainPackage.game_parts.direction;
import org.mainPackage.engine.entities.api.Entity;
import org.mainPackage.engine.events.api.EventType;
import org.mainPackage.engine.events.impl.GameEvent;


public abstract class PhysicsComponent implements Component{
    protected int xSpeed, ySpeed;
    protected Rectangle hitbox;
    protected Entity owner;
    protected ArrayList<Rectangle> tiles;
    public PhysicsComponent(int xS, int yS, Entity o, ArrayList<Rectangle>tList){
        xSpeed = xS;
        ySpeed = yS;
        owner = o;
        hitbox = new Rectangle(owner.getComponent(TransformComponent.class).getX(), owner.getComponent(TransformComponent.class).getY(), 
        owner.getComponent(TransformComponent.class).getWidth(), owner.getComponent(TransformComponent.class).getHeight());
        tiles = tList;
    }
    public Rectangle getHitbox(){
        return hitbox;
    }
    public Entity getOwner(){ return owner; }

    public void die(){ 
        GameEvent e = new GameEvent(EventType.ENEMY_DIED, owner);
        e.notify();
    }
    public void update(float deltaTime){
        hitbox.setLocation(owner.getComponent(TransformComponent.class).getX(), owner.getComponent(TransformComponent.class).getY());
    }

    public abstract direction getDirection();
    public abstract action getAction();
    public void setMovement(direction dir, boolean bool){}
}
