package org.mainPackage.components.PhysicsTypes;

import org.mainPackage.colliders.Collider;
import org.mainPackage.colliders.PlayerCollider;
import org.mainPackage.components.Physics;
import org.mainPackage.game_parts.direction;
import org.mainPackage.game_parts.action;
import org.mainPackage.game_parts.Entity;

import java.awt.Rectangle;
import java.util.*;

public class PlayerPhysics extends Physics{
    private direction playerDir = direction.right;
    private int speedMod = 1, maxSpeed = 15, jSpeed = -5;
    private int rings = 0, jumping = 0;
    private HashMap<direction, PlayerCollider> colliders = new HashMap<>();
    private HashMap<direction, Boolean> canMove = new HashMap<>();
    private ArrayList<Rectangle> tiles = new ArrayList<>();
    private action playerAction = action.idle;
    private boolean hurt = false;

    public PlayerPhysics(Entity o, ArrayList<Rectangle> tList){
        super(1, 5, o, tList);
        canMove.put(direction.left, true);
        canMove.put(direction.up, true);
        canMove.put(direction.right, true);
        canMove.put(direction.down, true);
        initializeColliders(tiles);
    }
    public void initializeColliders(ArrayList<Rectangle> tiles){ 
        int width = owner.getWidth(), height = owner.getHeight();
        colliders.put(direction.up, new PlayerCollider(tiles, this, direction.up, width/2, 0));
        colliders.put(direction.down, new PlayerCollider(tiles, this, direction.down, width/2, height));
        colliders.put(direction.left, new PlayerCollider(tiles, this, direction.left, 0, height/2));
        colliders.put(direction.right, new PlayerCollider(tiles, this, direction.right, width, height/2));
        hitbox = new Rectangle(owner.getX(), owner.getY(), width, height);
    }
    @Override
    public void update() {
        if (hurt){
            takeDamage();
        }
        for (PlayerCollider c : colliders.values()) {
            c.checkCollisions();
        }
        determineAction();
        moveY();
    }
    
    public void setDirection(direction d){
        playerDir = d;
    }
    public void setMovement(direction dir, boolean bool){
        canMove.replace(dir, bool);
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
            //Sonic fa inversione, questo gli comporta di perdere la velocità accomulata
            playerDir = dir;
            brake();
        }
        if(canMove.get(dir) == true){
            owner.moveX(xSpeed);
            moveColliders(xSpeed, 0);
            //se sonic si muove a terra, guadagna velocità orizzontale
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
            owner.moveY(-ySpeed);
            moveColliders(0, -ySpeed);
            jumping--;
        } else { /*hitting the ceiling causes him to start falling */
            jumping = 0;
        }
    } else {
        ySpeed = 5;
    } 
    if (canMove.get(direction.down)){ /*If sonic doesn't have nor the push of the jump, nor a solid ground under his feets, he will fall */
        owner.moveY(ySpeed);
        moveColliders(0, ySpeed);
    }
}
    public void jump(){
        if (canMove.get(direction.down) == false){
            jumping = 5;
            ySpeed = jSpeed;
            playerAction = action.jumping;
        }
    }

    public void moveColliders(int x, int y){
        for (PlayerCollider c : colliders.values()) {
            c.getSensor().translate(x, y);
        }
    }

    public void takeDamage(){
        playerAction = action.hurt;
        if (rings > 0){
            rings = 0;
        } else {
            owner.delete();
        }
    }
    public void gotRing(){
        rings++;
    }

    public void brake(){
        xSpeed = 1 * playerDir.getValue();
    }

    public boolean isHurt() { return hurt; }
    public Rectangle getHitbox(){ return hitbox; }
    public action getAction() { return playerAction; }
    public direction getDirection() { return playerDir; }
}
