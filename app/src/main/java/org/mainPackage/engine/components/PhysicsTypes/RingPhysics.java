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
    private float maxDistance = 40, spawnX, spawnY, maxSpeed = 0.6f;
    private EntityImpl sonic;
    private direction verticalDir, horizontalDir;
    private boolean tangible = false; /*although this parametrer is false by default,
    all the rings created at the formation of the level, in the main method, have it set to True */

    public RingPhysics(EntityImpl o, ArrayList<Rectangle2D.Float> tList, EntityImpl s){
        super(o, tList);
        sonic = s;
        spawnX = owner.getComponent(TransformComponent.class).getX();
        spawnY = owner.getComponent(TransformComponent.class).getY();
    }

    public void update(float deltaTime){
        /*The ring never stops moving once it starts*/
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
        /*Chec if the player picked it up */
        if (checkIntersection(sonic.getComponent(TransformComponent.class))
            && tangible){
            pickUp();
        } else {
            /*Set the tangibility to true when sonic is not touching it for the first time in the ring's lifetime*/
            if (!checkIntersection(sonic.getComponent(TransformComponent.class))
                && !tangible){
                changeTangibility();
            }
            moveX();
            moveY();
        } 
    }

    public void moveX() {
        if (xSpeed != 0){
            if (Math.abs(owner.getComponent(TransformComponent.class).getX() - spawnX) > maxDistance){
                xSpeed = 0;
            }
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
        if (ySpeed != 0){
            if (Math.abs(owner.getComponent(TransformComponent.class).getY() - spawnY) > maxDistance){
                ySpeed = 0;
            }
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
        notifyObservers(new GameEvent(EventType.ENTITY_DEAD, this.owner));
        sonic.getComponent(WalletComponent.class).increaseAmount();
        System.out.println("Ring picked up!" + tangible);
    }

    public void spreadOut(){
        /*this method is the only way xSpeed and ySpeed can increase, and RingPhysics 
        * can't launch it on his own.
        */
        xSpeed = (float)(Math.random() * (2 * maxSpeed) - maxSpeed);
        ySpeed = (float)(Math.random() * (2 * maxSpeed) - maxSpeed);
    }

    public void changeTangibility(){
        tangible = !tangible;
    }
}
