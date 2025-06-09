package org.mainPackage.engine.components.PhysicsTypes;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.mainPackage.colliders.RingCollider;
import org.mainPackage.engine.components.PhysicsComponent;
import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.entities.api.Entity;
import org.mainPackage.game_parts.action;
import org.mainPackage.game_parts.direction;

public class RingPhysics extends PhysicsComponent {
    private RingCollider collider;
    public RingPhysics(Entity o, ArrayList<Rectangle> tList, PlayerPhysics pp){ 
        super(0, 0, o, tList);
        collider = new RingCollider(tList, this, pp);
    }

    public void update(){
        collider.checkCollisions();
        owner.getComponent(TransformComponent.class).moveX(xSpeed);
        owner.getComponent(TransformComponent.class).moveY(ySpeed);
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
