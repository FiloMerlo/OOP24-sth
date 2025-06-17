package org.mainPackage.engine.components.PhysicsTypes;

import org.mainPackage.colliders.Collider;
import org.mainPackage.colliders.PlayerCollider;
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
    private float speedMod = 0.1f, maxSpeed = 1.2f, fallingSpeed = 0.1f, fallMod = 0.1f, maxFallSpeed = 1;
    private int rings = 0, jumping = 0, maxJumping = 100, jSpeed = -1;
    private HashMap<direction, Boolean> canMove = new HashMap<>();
    private action playerAction = action.idle;
    private int iFrames = 0;

    public PlayerPhysics(EntityImpl o, ArrayList<Rectangle2D.Float> tList){
        super(0.1f, 0.2f, o, tList);
        canMove.put(direction.left, true);
        canMove.put(direction.up, true);
        canMove.put(direction.right, true);
        canMove.put(direction.down, true);
        colliders.add(new PlayerCollider(tList, this, hitbox));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (playerAction == action.hurt){
            takeDamage();
        }
        if (iFrames > 0) {
            iFrames--;
        }
        determineAction();
        moveY();
    }
    
    public void setDirection(direction d){
        playerDir = d;
    }

    public void determineAction(){
        if (playerAction != action.jumping){
            if (canMove.get(direction.down)){
                playerAction = action.falling;
            } else{
                if (xSpeed == maxSpeed){
                    playerAction = action.dashing;
                } else if (xSpeed > 15){
                    playerAction = action.running;
                } else if(xSpeed > 0){
                    playerAction = action.walking;
                } else {
                    playerAction = action.idle;
                }
            }
        } else {playerAction = action.jumping;}
        
    }
    public void moveX(direction dir){
        if (dir != playerDir){
            /*Sonic reverse his direction, losing the speed accumulated*/
            playerDir = dir;
            brake();
        }
        if(canMove.get(dir) == true){
            owner.getComponent(TransformComponent.class).moveX(xSpeed);
            for (Collider coll : colliders) {
                coll.getSensor().x += xSpeed;
            }
            /*If sonic moves on the ground, he gains speed*/
            if (xSpeed < maxSpeed && canMove.get(direction.down) == false){
                xSpeed += speedMod;
            }
        } else{
            brake();
        }
        
    }
    public void moveY(){  /*This method simulates gravity*/
    if(jumping > 0){
        if(canMove.get(direction.up)){
            jumping--;
        } else { /*hitting the ceiling causes him to start falling */
            jumping = 0;
        }
    } else if (canMove.get(direction.down)){ 
        if (ySpeed < fallingSpeed){
            ySpeed = fallingSpeed;
        } else if (ySpeed < maxFallSpeed){
            ySpeed += fallMod;
        }
    } else { ySpeed = 0; }

    owner.getComponent(TransformComponent.class).moveY(ySpeed);
    for (Collider coll : colliders) {
        coll.getSensor().y += ySpeed;
    }
}
    public void jump(){
        if (canMove.get(direction.down) == false && playerAction != action.jumping){
            jumping = 60; /*number of jump frames*/
            ySpeed = jSpeed;
            playerAction = action.jumping;
        }
        else if(jumping < maxJumping && playerAction == action.jumping){
            /*this lets the player  */
            smallJump();
        }
    }
    public void smallJump(){
        jumping += 10;
        ySpeed = jSpeed;
        playerAction = action.jumping;
    }

    public void takeDamage(){
        iFrames = 100;
        GameEvent e;
        brake();
        if (rings > 0){
            rings = 0;
            e = new GameEvent(EventType.PLAYER_HIT, owner);
        } else {
            e = new GameEvent(EventType.GAME_OVER , owner);
        }
        notifyObservers(e);
    }
    public void gotRing(){
        rings++;
    }

    public void brake(){
        xSpeed = 1 * playerDir.getValue();
    }

    public void getHurt() {  
        if (iFrames == 0) {
            playerAction = action.hurt;
        }
    }
    public Rectangle2D.Float getHitbox(){ return hitbox; }
    public action getAction() { return playerAction; }
    public direction getDirection() { return playerDir; }
    public boolean getCanMove(direction dir){ return canMove.get(playerDir); }
    public void setMovement(direction dir, boolean bool){ 
        canMove.replace(dir, bool); 
    }

}
