package components.PhysicsTypes;
import colliders.*;
import game_parts.direction;
import game_parts.action;
import game_parts.TileType;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import org.checkerframework.checker.units.qual.s;

public class PlayerPhysics extends Physics{
    private direction direction = right;
    private float speedMod = 1, maxSpeed = 15, jSpeed = -5;
    private int rings = 0, jumping = 0;
    private HashMap<direction, PlayerCollider> colliders = new HashMap<>();
    private HashMap<direction, Boolean> canMove = new HashMap<>();
    private ArrayList<Tile> tiles = new ArrayList<>();
    private boolean hurt = false;
    private action playerAction = idle;

    public PlayerPhysics(MovableBody b, ArrayList<Tile> t){
        super(1, 5, b);
        tiles = t;
        canMove.put(left, true);
        canMove.put(up, true);
        canMove.put(right, true);
        canMove.put(down, true);
        initializeColliders(tiles);
        animator = body.getOwner().getComponent(SonicAnimator.class);
    }
    public void initializeColliders(ArrayList<Tile> tiles){ 
        //Dò ad ogni Collider solo le Tile con cui può interagire
        ArrayList<Tile> ceilings = new ArrayList<>();
        ArrayList<Tile> floors = new ArrayList<>();    
        ArrayList<Tile> leftWalls = new ArrayList<>();   
        ArrayList<Tile> rightWalls = new ArrayList<>(); 
        for (Tile t : tiles){
            if (t.getType() == ceiling){
                ceilings.add(t);
            } else if (t.getType() == floor){
                floors.add(t);
            } else if (t.getType() == leftWall){
                leftWalls.add(t);
            } else if (t.getType() == rightWall){
                rightWalls.add(t);
            }
        }
        float width = body.getWidth(), height = body.getHeight();
        colliders.put(up, new PlayerCollider(width/2, 0, 1, 1, ceilings, this));
        colliders.put(down, new PlayerCollider(width/2, height, 1, 1, floors, this));
        colliders.put(left, new PlayerCollider(0, height/2, 1, 1, leftWalls, this));
        colliders.put(right, new PlayerCollider(width, height/2, 1, 1, rightWalls, this));
        hitbox = new Rectangle(0, 0, width, height);
    }
    @Override
    public void update() {
        for (PlayerCollider pc : colliders) {
            pc.checkCollisions();
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
            body.moveX(xSpeed);
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
            body.moveY(ySpeed);
            jumping--;
        } else { //cade immediatamente quando colpisce il soffitto
            jumping = 0;
        }
    } 
    else {
        ySpeed = 5;
    } 
    if (canMove.get(down)){ //se non ha l'impeto del salto né terreno sotto i piedi, cade
        body.moveY(ySpeed);
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
                rings = 0;
                setAction(hurt);
            } else {
                body.getOwner().delete();
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
