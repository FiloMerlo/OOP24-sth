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
    private float accelMod = 0.01f, maxSpeed = 2.8f, minSpeed = 0.1f, initFallSpeed = 0.1f, fallMod = 0.1f, maxFallSpeed = 1;
    private int jumpFrames = 0, maxJumpFrames = 200, jSpeed = -1, brakeForce = 1, iFrames = 0;
    private int knockbackFrames = 0; 
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
        if (hit) {
            takeDamage();
        }

        if (knockbackFrames > 0) {
            knockbackFrames--;
        }

        if (iFrames > 0) {
            iFrames--;
        }

        moveX(deltaTime);
        moveY(deltaTime);
        determineAction();
    }
    
    public void moveX(float deltaTime){

        if (knockbackFrames > 0) {
           
            owner.getComponent(TransformComponent.class).moveX(xSpeed);
            brake(); 
            playerAction = action.hurt; 
        } else if (iFrames > 0) {
           
            if (tryToMove.get(direction.left) ^ tryToMove.get(direction.right)) {
                direction newDir = tryToMove.get(direction.left) ? direction.left : direction.right;

                if (newDir == playerDir) {
                    if (newDir == direction.right && xSpeed < minSpeed) {
                        xSpeed = minSpeed;
                    } else if (newDir == direction.left && xSpeed > -minSpeed) {
                        xSpeed = -minSpeed;
                    }
                    if (canGoThere(newDir, xSpeed)) {
                        owner.getComponent(TransformComponent.class).moveX(xSpeed);
                        if (!canGoThere(direction.down, 0.1f)) {
                            if (xSpeed < maxSpeed && xSpeed > -maxSpeed) {
                                xSpeed += accelMod * playerDir.getValue();
                            }
                        }
                    }
                } else {
                    brake();
                    if (xSpeed == 0) {
                        playerDir = newDir;
                    }
                }
            
                if (Math.abs(xSpeed) > 1) playerAction = action.dashing;
                else if (Math.abs(xSpeed) > 0.5) playerAction = action.running;
                else if (xSpeed != 0) playerAction = action.walking;
                else playerAction = action.idle;

            } else {
                brake();
            }
        } else {
         
            if (tryToMove.get(direction.left) ^ tryToMove.get(direction.right)) {
                direction newDir = tryToMove.get(direction.left) ? direction.left : direction.right;

                if (newDir == playerDir) {
                    if (newDir == direction.right && xSpeed < minSpeed) {
                        xSpeed = minSpeed;
                    } else if (newDir == direction.left && xSpeed > -minSpeed) {
                        xSpeed = -minSpeed;
                    }
                    if (canGoThere(newDir, xSpeed)) {
                        owner.getComponent(TransformComponent.class).moveX(xSpeed);
                        if (!canGoThere(direction.down, 0.1f)) {
                            if (xSpeed < maxSpeed && xSpeed > -maxSpeed) {
                                xSpeed += accelMod * playerDir.getValue();
                            }
                        }
                    }
                } else {
                    brake();
                    if (xSpeed == 0) {
                        playerDir = newDir;
                    }
                }
            } else {
                brake();
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
        /*LANDING.    if Sonic isn't already on the ground, he lands*/
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
        } else {/*action stays unchanged*/}
    }
    
    public void brake() {
        for (int i = 0; i < brakeForce && xSpeed != 0; i++){
            if (xSpeed > -0.1f && xSpeed < 0.1f){
                xSpeed = 0;
            } else if (xSpeed != 0){
                xSpeed += (-1) * playerDir.getValue() * 0.1f;
            }
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
        System.out.println("Sonic took damage!");
        playerAction = action.hurt;
        hit = false;
        knockbackFrames = 15;  
        iFrames = 480;       

        // Knockback direction is opposite of current playerDir
        direction knockbackDir = playerDir.opposite();
        playerDir = knockbackDir;  

        GameEvent e;
        if (owner.getComponent(WalletComponent.class).getAmount() > 0){
            e = new GameEvent(EventType.PLAYER_HIT, owner);
        }
        else {
            System.out.println("GAME OVER!!!!!");
            e = new GameEvent(EventType.GAME_OVER, owner);
        }
        notifyObservers(e);

        xSpeed = 0.2f * knockbackDir.getValue();
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
