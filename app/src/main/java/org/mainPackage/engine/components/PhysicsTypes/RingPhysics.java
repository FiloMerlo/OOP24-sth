package org.mainPackage.engine.components.PhysicsTypes;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.mainPackage.engine.components.PhysicsComponent;
import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.components.WalletComponent;
import org.mainPackage.engine.entities.impl.EntityImpl;
import org.mainPackage.engine.events.api.EventType;
import org.mainPackage.engine.events.impl.GameEvent;
import org.mainPackage.enums.direction;

public class RingPhysics extends PhysicsComponent {
    private float maxDistance = 1, spawnX, spawnY, maxSpeed = 0.6f;
    private EntityImpl sonic;
    private direction verticalDir, horizontalDir;
    private boolean tangible = false; /*although this parametrer is false by default,
    all the rings created at the formation of the level in the main method are set true */

    public RingPhysics(EntityImpl o, ArrayList<Rectangle2D.Float> tList, EntityImpl s){
        super(o, tList);
        sonic = s;
        spawnX = owner.getComponent(TransformComponent.class).getX();
        spawnY = owner.getComponent(TransformComponent.class).getY();
    }

    public void update(float deltaTime){
        if (ySpeed > 0){
            verticalDir = direction.down;
        } else if (ySpeed < 0){
            verticalDir = direction.up;
        }
        if (xSpeed > 0){
            horizontalDir = direction.right;
        } else if (xSpeed < 0){
            horizontalDir = direction.left;
        }
        if (checkIntersection(sonic.getComponent(TransformComponent.class))
            && tangible){
            pickUp();
        } else {
            if (!checkIntersection(sonic.getComponent(TransformComponent.class))
                && !tangible){
                changeTangibility();
            }
            moveX();
            moveY();
        } 
    }

    public void moveX() {
        if (xSpeed != 0 && Math.abs(owner.getComponent(TransformComponent.class).getX() - spawnX) <= maxDistance){
            if (canGoThere(horizontalDir, xSpeed)){
                owner.getComponent(TransformComponent.class).moveX(xSpeed);
            } else {
                bounceOnVerticalSurface();
            }
        }
    }

    public void bounceOnVerticalSurface(){
        horizontalDir = horizontalDir.opposite();
        xSpeed *= -1;
    }

    public void moveY() {
        if (ySpeed != 0 && Math.abs(owner.getComponent(TransformComponent.class).getY() - spawnY) <= maxDistance){
            if (canGoThere(verticalDir, ySpeed)){
                owner.getComponent(TransformComponent.class).moveY(ySpeed);
            } else {
                bounceOnHorizontalSurface();
            }
        }
    }
    public void bounceOnHorizontalSurface(){
        verticalDir = verticalDir.opposite();
        ySpeed *= -1;
    }

    public void pickUp(){
        changeTangibility();
        notifyObservers(new GameEvent(EventType.ENTITY_DEAD, this.owner));
        sonic.getComponent(WalletComponent.class).increaseAmount();
        System.out.println("Ring picked up!");
    }

    public void spreadOut(){
        /*this method is the only way xSpeed and ySpeed can increase, and RingPhysics 
        * can't launch it on his own.
        */
        xSpeed = (float)(Math.random() * (2 * maxDistance) - maxDistance);
        ySpeed = (float)(Math.random() * (2 * maxDistance) - maxDistance);
    }

    public void changeTangibility(){
        tangible = !tangible;
    }
}
