package org.mainPackage.engine.components.PhysicsTypes;

import org.mainPackage.engine.components.PhysicsComponent;
import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.entities.impl.EntityImpl;
import org.mainPackage.engine.events.impl.GameEvent;
import org.mainPackage.enums.action;
import org.mainPackage.enums.direction;
import org.mainPackage.engine.events.api.EventType;

import java.awt.geom.Rectangle2D;
import java.util.*;

public class PlayerPhysics extends PhysicsComponent {
    private direction playerDir = direction.right;
    private action playerAction = action.idle;
    private float accelMod = 0.01f, maxSpeed = 1.2f, minSpeed = 0.1f, initFallSpeed = 0.1f, fallMod = 0.1f, maxFallSpeed = 1;
    private int jumpFrames = 0, maxJumpFrames = 100, jSpeed = -1, brakeForce = 1, iFrames = 0;
    protected HashMap<direction, Boolean> tryToMove = new HashMap<>();
    private boolean hit;

    public PlayerPhysics(EntityImpl o, ArrayList<Rectangle2D.Float> tList){
        super(o, tList);
        ySpeed = initFallSpeed;
        tryToMove.put(direction.left, false);
        tryToMove.put(direction.up, false);
        tryToMove.put(direction.right, false);
        /*tryToMove per direction.down è sempre opposto a tryToMove per direction.up*/
    }

    public void update(float deltaTime) {
        if (hit == true){
            takeDamage();
        }
        if (iFrames > 0) {
            iFrames--;
        }
        moveX();
        moveY();
        determineAction();
    }
    public void moveX(){

        if (playerAction == action.hurt){
            if (canGoThere(playerDir.opposite(), xSpeed)){
                owner.getComponent(TransformComponent.class).moveX(xSpeed);
            }
        } else 
        if (tryToMove.get(direction.left) ^ tryToMove.get(direction.right)) {
            /*DETERMINE WHICH DIRECTION THE PLAYER IS TRYING TO MOVE TOWARDS*/
            direction newDir = direction.right;
            if (tryToMove.get(direction.left)) {
                newDir = direction.left;
            }

            if (newDir == playerDir) {
                /*MAKE SURE THE PLAYER ISN'T TOO SLOW*/
                if (newDir == direction.right && xSpeed < minSpeed){
                    xSpeed = minSpeed;
                } else if (newDir == direction.left && xSpeed > -minSpeed) {
                    xSpeed = -minSpeed;
                }
                /*CHECK FOR COLLISIONS AND MOVE IF POSSIBLE*/
                if (canGoThere(newDir, xSpeed)){
                    owner.getComponent(TransformComponent.class).moveX(xSpeed);
                    if (!canGoThere(direction.down, 0.1f)){
                        /*IF SONIC MOVES ON THE GROUND, HE GAINS SPEED*/
                        if (xSpeed < maxSpeed && xSpeed > -maxSpeed){
                            xSpeed += accelMod * playerDir.getValue();
                        }
                    }
                }
            } else {
                brake();
                if (xSpeed == 0){
                    playerDir = newDir;
                }
            }
        } else {
            brake();           
        }
    }
    public void moveY(){
        /*JUMPING*/
        if(jumpFrames > 0){
            if(canGoThere(direction.up, ySpeed)){
                jumpFrames--;
            } else { 
                /*hitting the ceiling causes him to start falling */
                jumpFrames = 0;
                ySpeed = initFallSpeed;
            }
        }
        /*FALLING.   Sonic starts to fall only one update after he ran out of jumpingFrames*/
        else if (canGoThere(direction.down, Math.max(initFallSpeed, ySpeed))){
            if (ySpeed <= 0){
                ySpeed = initFallSpeed;
            } else if (ySpeed < maxFallSpeed){
                ySpeed += fallMod;
            }
        } 
        /*LANDING*/
        else { 
            landing();
        }
        owner.getComponent(TransformComponent.class).moveY(ySpeed);
    }

    public void determineAction(){
        if (iFrames < 210){
            if (ySpeed > 0 && playerAction != action.jumping){
                playerAction = action.falling;
            } else if (ySpeed < 0){
                playerAction = action.jumping;
            } else {
                if (ySpeed <= 0){
                    if (xSpeed > 1 || xSpeed < -1){
                        playerAction = action.dashing;
                    } else if (xSpeed > 0.5 || xSpeed < -0.5){
                        playerAction = action.running;
                    } else if(xSpeed != 0){
                        playerAction = action.walking;
                    } else {
                        playerAction = action.idle;
                    }
                }
            }
        } else {/*L'azione non cambia, rimane action.hurt*/}
    }
    
    public void brake() {
        for (int i = 0; i < brakeForce && xSpeed != 0; i++){
            /*Contingency*/
            if (xSpeed > -0.1f && xSpeed < 0.1f){
                xSpeed = 0;
            } else if (xSpeed != 0){
                xSpeed += (-1) * playerDir.getValue() * 0.1f;
            }
        }  
    }
    
    public void landing() {
        float yDist = Float.MAX_VALUE;
        TransformComponent transform = owner.getComponent(TransformComponent.class);
        for (Rectangle2D.Float tile : tiles) {
            if (tile.getY() >= transform.getY() + transform.getHeight() && canGoThere(direction.down, (float)(tile.getY() - (transform.getY() + transform.getHeight()))) == true){
                float newDist = (float)(tile.getY() - (transform.getY() + transform.getHeight()));
                if (newDist < yDist){
                    yDist = newDist;
                }
            }
        }
        if(yDist == Float.MAX_VALUE){
            yDist = 0;
        }
        transform.moveY(yDist);
        ySpeed = 0;
    }

    public void jump(){
        if (!canGoThere(direction.down, 0.3f) && jumpFrames == 0){
            jumpFrames = 65;
            ySpeed = jSpeed;
            /*Sonic can jump when he's falling, even if he still haven't touched 
             * the ground properly. This is an intended feature, it gives a better
             * feeling to the player inputs
            */
        }
        else if(jumpFrames < maxJumpFrames && playerAction == action.jumping){
            smallJump();
            /*this lets the player control the height of the jump to an extent */
        }
    }
    public void smallJump(){
        jumpFrames += 10;
    }

    public void takeDamage(){
        hit = false;
        iFrames = 240;
        GameEvent e;
        e = new GameEvent(EventType.PLAYER_HIT, owner);
        notifyObservers(e);
        xSpeed = 0.2f * playerDir.opposite().getValue();
    }

    public void hit() { 
        if (iFrames == 0) {
            hit = true;
        }
    }

    public action getAction() { return playerAction; }
    public direction getDirection() { return playerDir; }
    public void setWill(direction dir, boolean bool){
        tryToMove.replace(dir, bool);
    }
}
