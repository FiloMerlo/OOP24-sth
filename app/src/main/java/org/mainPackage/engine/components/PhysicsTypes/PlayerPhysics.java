package org.mainPackage.engine.components.PhysicsTypes;

import org.mainPackage.colliders.PlayerCollider;
import org.mainPackage.engine.components.PhysicsComponent;
import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.entities.api.*;
import org.mainPackage.engine.events.impl.GameEvent;
import org.mainPackage.enums.action;
import org.mainPackage.enums.direction;
import org.mainPackage.engine.events.api.EventType;

import java.awt.Rectangle;
import java.util.*;

public class PlayerPhysics extends PhysicsComponent{
    private direction playerDir = direction.right;
    private int speedMod = 1, maxSpeed = 15, jSpeed = -5;
    private int rings = 0, jumping = 0;
    private HashMap<direction, Boolean> canMove = new HashMap<>();
    private action playerAction = action.idle;
    private int iFrames = 0;

    public PlayerPhysics(Entity o, ArrayList<Rectangle> tList){
        super(1, 5, o, tList);
        canMove.put(direction.left, true);
        canMove.put(direction.up, true);
        canMove.put(direction.right, true);
        canMove.put(direction.down, true);
        collider = new PlayerCollider(tList, this);
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

        collider.checkCollisions();
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
                if (xSpeed > 15){
                    playerAction = action.dashing;
                } else if (xSpeed > 10){
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
            collider.getSensor().translate(xSpeed, 0);
            /*If sonic moves on the ground, he gains speed*/
            if (xSpeed < maxSpeed && canMove.get(direction.down) == false){
                xSpeed += speedMod;
            }
        } else{
            brake();
        }
        
    }
    public void moveY(){  /*This method won't be requested by PlayerInputs. It simulates gravity*/
    if(jumping > 0){
        if(canMove.get(direction.up)){
            jumping--;
        } else { /*hitting the ceiling causes him to start falling */
            jumping = 0;
        }
    } else if (canMove.get(direction.down)){ 
        ySpeed = 5;
    }
    else { ySpeed = 0; }
    owner.getComponent(TransformComponent.class).moveY(ySpeed);
    collider.getSensor().translate(0, ySpeed);
}
    public void jump(){
        if (canMove.get(direction.down) == false){
            jumping = 5;
            ySpeed = jSpeed;
            playerAction = action.jumping;
        }
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
        e.notify();
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
    public Rectangle getHitbox(){ return hitbox; }
    public action getAction() { return playerAction; }
    public direction getDirection() { return playerDir; }
    public void setMovement(direction dir, boolean bool){ 
        canMove.replace(dir, bool); 
    }

}
