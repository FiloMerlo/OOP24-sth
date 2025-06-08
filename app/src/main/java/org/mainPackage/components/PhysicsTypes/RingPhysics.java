package org.mainPackage.components.PhysicsTypes;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.mainPackage.colliders.RingCollider;
import org.mainPackage.components.Physics;
import org.mainPackage.game_parts.Entity;
import org.mainPackage.game_parts.action;
import org.mainPackage.game_parts.direction;

public class RingPhysics extends Physics {
    private RingCollider collider;
    private int maxDistance = 10;
    private Point spawn;

    public RingPhysics(Entity o, ArrayList<Rectangle> tList, PlayerPhysics pp, int xS, int yS){
        super(0, 0, o, tList);
        collider = new RingCollider(tList, this, pp);
        spawn = new Point(xS, yS);
    }

    public void update(){
        collider.checkCollisions();
        if (xSpeed > 0){
            if (owner.getX() - spawn.getX() < maxDistance && owner.getX() - spawn.getX() > -maxDistance){
                owner.moveX(xSpeed);
            }
            if (owner.getY() - spawn.getY() < maxDistance && owner.getY() - spawn.getY() > -maxDistance){
                owner.moveY(xSpeed);
            }        
            collider.getSensor().translate(xSpeed, ySpeed);
        }
    }

    public void bounce(Rectangle tileMet){
        if (tileMet.getY() >= owner.getY() + owner.getHeight() ||
            tileMet.getY() <= owner.getY()){
            ySpeed = -ySpeed;
        }
        else {
            xSpeed = -xSpeed;
        }
    }

    public void spredOut(){
        xSpeed = (int)(Math.random() * 4 - 0);
        ySpeed = (int)(Math.random() * 4 - 0);
    }

    public action getAction(){ return null; }
    public direction getDirection(){ return null; }
}
