package org.mainPackage.engine.components.PhysicsTypes;

import org.mainPackage.engine.components.PhysicsComponent;
import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.entities.impl.EntityImpl;
import org.mainPackage.engine.events.impl.GameEvent;
import org.mainPackage.enums.action;
import org.mainPackage.enums.direction;
import org.mainPackage.engine.events.api.EventType;
import org.mainPackage.engine.components.WalletComponent;

import java.awt.geom.Rectangle2D;
import java.util.*;

public class PlayerPhysics extends PhysicsComponent {
    private direction playerDir = direction.right;
    private action playerAction = action.idle;
    private float accelMod = 0.01f, maxSpeed = 3.1f, minSpeed = 0.1f, initFallSpeed = 0.1f, fallMod = 0.1f, lowestPoint = 0f, maxFallSpeed = 1, jSpeed = -1.5f;
    private int jumpFrames = 0, maxJumpFrames = 100, brakeForce = 1, iFrames = 0;
    protected HashMap<direction, Boolean> tryToMove = new HashMap<>();
    private boolean hit;

    public PlayerPhysics(EntityImpl o, ArrayList<Rectangle2D.Float> tList){
        super(o, tList);
        ySpeed = 0;
        tryToMove.put(direction.left, false);
        tryToMove.put(direction.up, false);
        tryToMove.put(direction.right, false);
        /*tryToMove for direction.down is always the opposite of tryToMove for direction.up*/
        lowestPoint = owner.getComponent(TransformComponent.class).getY() + 1000;
    }

    public void update(float deltaTime) {
        if (hit == true){
            takeDamage();
        }
        if (iFrames > 0) {
            iFrames--;
        }
        if (tryToMove.get(direction.left) ^ tryToMove.get(direction.right)) {
            moveX(deltaTime);
        } else {
            brake();           
        }
        moveY(deltaTime);
        determineAction();
        checkDeathFall();
    }

    private void checkDeathFall() {
        if (owner.getComponent(TransformComponent.class).getY() >= lowestPoint){
            takeDamage();
        }
    }

    public void moveX(float deltaTime){
        if (playerAction == action.hurt){
            if (canGoThere(playerDir.opposite(), 0.2f * playerDir.opposite().getValue())){
                owner.getComponent(TransformComponent.class).moveX(0.2f * playerDir.opposite().getValue());
            }
        } else {
            /*DETERMINE WHICH DIRECTION THE PLAYER IS TRYING TO MOVE TOWARDS*/
            direction newDir = direction.right;
            if (tryToMove.get(direction.left)) {
                newDir = direction.left;
            }
            if (newDir == playerDir) {
                /*JUMPING HAS ITS OWN MINIMl HORIZONTAL SPEED*/
                if (playerAction == action.jumping && Math.abs(xSpeed) < Math.abs(jSpeed)) {
                    xSpeed = jSpeed * playerDir.opposite().getValue();
                } else
                /*MAKE SURE THE PLAYER ISN'T TOO SLOW*/
                if (Math.abs(xSpeed) < minSpeed){
                    xSpeed = minSpeed * playerDir.getValue();
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
        }
    }
    public void moveY(float deltaTime){
        /*JUMPING*/
        if(jumpFrames > 0){
            if(canGoThere(direction.up, ySpeed)){
                jumpFrames--;
            } else { 
                /*hitting the ceiling causes him to start falling */
                jumpFrames = 0;
                ySpeed = 0;
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
        /*LANDING.    if Sonic is't already on the ground, he lands*/
        else if (canGoThere(direction.down, Float.MIN_VALUE)){ 
            if(ySpeed > 0){
                landing();
            }
        }
        owner.getComponent(TransformComponent.class).moveY(ySpeed);
    }

    public void determineAction(){
        /*Determines what action the player is doing.*/
        if (iFrames < 210){
            if (ySpeed > 0 && playerAction != action.jumping){
                playerAction = action.falling;
            } else if (ySpeed < 0){
                playerAction = action.jumping;
            } else {
                if (ySpeed <= 0){
                    if (xSpeed > 2.5f || xSpeed < -2.5f){
                        playerAction = action.dashing;
                    } else if (xSpeed > 1 || xSpeed < -1){
                        playerAction = action.running;
                    } else if(xSpeed != 0){
                        playerAction = action.walking;
                    } else {
                        playerAction = action.idle;
                    }
                }
            }
        } else {/*action stays unchanged*/}
    }
    
    public void brake() {
        for (int i = 0; i < brakeForce && xSpeed != 0; i++){
            if (xSpeed > -0.01f && xSpeed < 0.1f){
                xSpeed = 0;
            } else if (xSpeed != 0){
                if (xSpeed < 0){
                    xSpeed += 0.1f;
                } else {
                    xSpeed -= 0.1f;
                }
            }
        }  
        if (canGoThere(playerDir, xSpeed)){
            owner.getComponent(TransformComponent.class).moveX(xSpeed);
        }
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
        playerAction = action.hurt;
        hit = false;
        iFrames = 480;
        GameEvent e;
        if (owner.getComponent(WalletComponent.class).getAmount() > 0){
            e = new GameEvent(EventType.PLAYER_HIT, owner);
        }
        else {
            e = new GameEvent(EventType.GAME_OVER, owner);
        }
        notifyObservers(e);
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