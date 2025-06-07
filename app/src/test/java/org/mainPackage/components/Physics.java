package org.mainPackage.components;

import java.awt.Rectangle;
import java.util.ArrayList;
import org.mainPackage.game_parts.action;
import org.mainPackage.game_parts.direction;
import org.mainPackage.game_parts.Entity;


public abstract class Physics implements Component{
    protected int xSpeed, ySpeed;
    protected Rectangle hitbox;
    protected Entity owner;
    protected ArrayList<Rectangle> tiles;
    public Physics(int xS, int yS, Entity o, ArrayList<Rectangle>tList){
        xSpeed = xS;
        ySpeed = yS;
        owner = o;
        hitbox = new Rectangle(owner.getX(), owner.getY(), owner.getWidth(), owner.getHeight());
        tiles = tList;
    }
    public Rectangle getHitbox(){
        return hitbox;
    }
    public Entity getOwner(){ return owner; }

    public void die(){ 
        owner.delete(); 
    }
    public void update(){
        hitbox.setLocation(owner.getX(), owner.getY());
    }

    public abstract direction getDirection();
    public abstract action getAction();
    public void setMovement(direction dir, boolean bool){}
}
