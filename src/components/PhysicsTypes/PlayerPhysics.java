package components.PhysicsTypes;
import colliders.*;
import components.Physics;
import game_parts.direction;
import game_parts.action;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.checkerframework.checker.units.qual.s;

public class PlayerPhysics extends Physics{
    private direction direction = right;
    private int speedMod = 1, maxSpeed = 15, jSpeed = -5;
    private int rings = 0, jumping = 0;
    private HashMap<direction, PlayerCollider> colliders = new HashMap<>();
    private HashMap<direction, Boolean> canMove = new HashMap<>();
    private ArrayList<Rectangle> tiles = new ArrayList<>();
    private boolean hurt = false;
    private action playerAction = idle;

    public PlayerPhysics(Entity o, ArrayList<Rectangle> tList){
        super(1, 5, o, tList);
        canMove.put(left, true);
        canMove.put(up, true);
        canMove.put(right, true);
        canMove.put(down, true);
        initializeColliders(tiles);
    }
    public void initializeColliders(ArrayList<Rectangle> tiles){ 
        int width = owner.getWidth(), height = owner.getHeight();
        colliders.put(up, new PlayerCollider(tiles, this, up, width/2, 0));
        colliders.put(down, new PlayerCollider(tiles, this, down, width/2, height));
        colliders.put(left, new PlayerCollider(tiles, this, left, 0, height/2));
        colliders.put(right, new PlayerCollider(tiles, this, right, width, height/2));
        hitbox = new Rectangle(0, 0, width, height);
    }
    @Override
    public void update() {
        for (int pc = 0; pc < 4; pc ++) {
            colliders.get(pc).checkCollisions();
        }
        determineAction();
        moveY();
    }
    
    public void setDirection(direction d){
        this.playerDirection = d;
    }
    public void setMovement(direction dir, boolean bool){
        canMove.replace(dir, bool);
    }
    public void determineAction(){
        if (playerAction != jumping){
            if (canMove(down)){
                playerAction = falling;
            } else{
                if (xSpeed > 15){
                    playerAction = dashing;
                } else if (xSpeed > 10){
                    playerAction = running;
                } else if(xSpeed > 0){
                    playerAction = walking;
                } else {
                    playerAction = idle;
                }
            }
        } else {playerAction = jumping;}
        
    }
    @Override
    public void moveX(direction dir){
        if (dir != playerDirection){
            //Sonic fa inversione, questo gli comporta di perdere la velocità accomulata
            playerDirection = dir;
            brake();
        }
        if(canMove.get(dir) == true){
            owner.moveX(xSpeed);
            //se sonic si muove a terra, guadagna velocità orizzontale
            if (xSpeed < maxSpeed && canMove.get(down) == false){
                xSpeed += speedMod;
            }
        } else{
            brake();
        }
        
    }
    @Override
    public void moveY(){  //questo metodo dev'essere richiamato nel gameloop, per simulare la gravità
    if(jumping > 0){
        if(canMove.get(up)){
            owner.moveY(ySpeed);
            jumping--;
        } else { //cade immediatamente quando colpisce il soffitto
            jumping = 0;
        }
    } 
    else {
        ySpeed = 5;
    } 
    if (canMove.get(down)){ //se non ha l'impeto del salto né terreno sotto i piedi, cade
        owner.moveY(ySpeed);
    }
}
    public void jump(){
        if (canMove.get(down) == false){
            jumping = 5;
            ySpeed = jSpeed;
            setAction(jumping);
        }
    }
    public void checkCollisions(){
        for (Map<direction, Collider>.Entry c : colliders.entrySet()) {
            c.check();
        }
    }
    public void takeDamage(){
        if (playerAction != hurt){
            if (rings > 0){
                for (int i = 0; i < rings; i++){
                    
                }
                rings = 0;
                setAction(hurt);
            } else {
                owner.delete();
            }
            
        }
    }
    public void gotRing(){
        rings++;
    }

    public void brake(){
        xSpeed = 1 * playerDirection.getValue();
    }
    public Rectangle getHitbox(){
        return hitbox.getSensor();
    }

    public action getPlayerAction() { return playerAction; }
}
