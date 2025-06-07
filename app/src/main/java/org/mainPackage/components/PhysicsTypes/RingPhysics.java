package org.mainPackage.components.PhysicsTypes;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.mainPackage.colliders.RingCollider;
import org.mainPackage.components.Physics;
import org.mainPackage.game_parts.Entity;
import org.mainPackage.game_parts.action;
import org.mainPackage.game_parts.direction;

public class RingPhysics extends Physics {
    private RingCollider collider;
    public RingPhysics(Entity o, ArrayList<Rectangle> tList, PlayerPhysics pp){ 
        super(0, 0, o, tList);
        collider = new RingCollider(tList, this, pp);
    }

    public void update(){
        collider.checkCollisions();
        owner.moveX(xSpeed);
        owner.moveY(ySpeed);
        collider.getSensor().translate(xSpeed, ySpeed);
    }

    public void bounce(){
        xSpeed = xSpeed * (-1);
        ySpeed = ySpeed * (-1);
    }

    public void spredOut(){
        xSpeed = (int)(Math.random() * 4 - 0);
        ySpeed = (int)(Math.random() * 4 - 0);
    }

    public action getAction(){ return null; }
    public direction getDirection(){ return null; }
}
