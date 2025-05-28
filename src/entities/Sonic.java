package entities;
import colliders.*;
import game_parts.direction;
import game_parts.action;
import game_parts.TileType;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.checkerframework.checker.units.qual.s;

public class Sonic extends Entity{
    private direction dir = direction.right;
    private float speedMod = 0.5, maxSpeed = 15, jSpeed = 3;
    private int rings = 0, jumping = 0;
    private HashMap<direction, Collider> colliders = new HashMap<>();
    private HashMap<direction, Boolean> canMove = new HashMap<>();
    private ArrayList<Tile> tiles = new ArrayList<>();
    private boolean hurt = false;

    private action playerAction = idle;
    private SonicAnimator animator;
  
  /*
   private boolean left, right, up, down;
   

    public Sonic(int xPos, int yPos, int xSpeed, int ySpeed, int groundSpeed, int groundAngle, int widthR, int heightR) {
        super(xPos, yPos, xSpeed, ySpeed, groundSpeed, groundAngle, widthR, heightR);
     
    }

  
  */
    public Sonic(float x , float y, ArrayList<Tile> t){
        super(x, y);
        tiles = t;
        width = 15;
        height = 20;
        xSpeed = 5;
        ySpeed = 10;
        canMove.put(left, true);
        canMove.put(up, true);
        canMove.put(right, true);
        canMove.put(down, false);
        initializeColliders(tiles);
        animator = new SonicAnimator();
        hitbox = new Rectangle(13, 18);
    }

    public void playerLoop(){
        update();
    }
   @Override
    public void update() {
        updatePos();
        updateAction();
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
        colliders.put(up, new UpCollider(xPos, yPos + height/2, 1, 1, ceilings, this));
        colliders.put(down, new DownCollider(xPos, yPos - height/2, 1, 1, floors, this));
        colliders.put(left, new HorizontalCollider(xPos - width/2, yPos, 1, 1, leftWalls, this));
        colliders.put(right, new HorizontalCollider(xPos + width/2, yPos, 1, 1, rightWalls, this));
        hitbox = new Rectangle();
    }
    @Override
    public void draw(Graphics g, int offsetX, int offsetY) {
        BufferedImage currentFrame = animator.getFrame(playerAction);
        g.drawImage(currentFrame, xPos + offsetX, yPos + offsetY, null);
    } 
    
    public void setDirection(direction d){
        this.playerDirection = d;
    }
    public void setMovement(direction dir, boolean bool){
        canMove.replace(dir, bool);
    }
    public void setAction(action a){
        playerAction = a;
    }
    public void determineAction(){
        if (xSpeed > 15){
                setAction(dashing);
            } else if (xSpeed > 10){
                setAction(running);
            } else if(xSpeed > 0){
                setAction(walking);
            } else {
                setAction(idle);}
    }
    @Override
    public void moveX(direction dir){
        if (dir != playerDirection){
            //Sonic fa inversione, questo gli comporta di perdere la velocità accomulata
            brake();
        }
        playerDirection = dir;
        if(canMove.get((String)dir) == true){
            xPos =+ xSpeed*playerDirection.getValue();
            //se sonic si muove a terra, guadagna velocità orizzontale
            if (xSpeed < maxSpeed && canMove.get(down) == false){
                xSpeed += speedMod;
            }
            determineAction();
        } else{
            brake();
        }
        
    }
    @Override
    public void moveY(){  //questo metodo dev'essere richiamato nel gameloop, per simulare la gravità
    if(jumping > 0){
        if(canMove.get(up)){
            yPos += jSpeed;
            jumping--;
        } else { //cade immediatamente quando colpisce il soffitto
            jumping = 0;
        }
    } 
    else if (canMove.get(down)){ //se non ha l'impeto del salto né terreno sotto i piedi, cade
        ySpeed = 10;
        yPos += ySpeed * (-1);
    }
}
    public void jump(){
        if (canMove.get(down) == false){
            jumping = 5;
            setAction(jumping);
        }
    }

    public void checkCollisions(){
        for (Map<direction, Collider>.Entry c : colliders.entrySet()) {
            c.check();
        }
    }
    public void loseRings(){
        if (playerAction != hurt){
            rings = 0;
            setAction(hurt);
        }
    }
    public void brake(){
        xSpeed = 5;
        setAction(idle);
        //ora non sto usando l'azione skiddling perché devo ancora implementare il momentum dopo la fine della pressione dei comandi di movimento.
    }
    public Rectangle getHitbox(){
        return hitbox.getSensor();
    }

    public action getPlayerAction() { return playerAction; }
}
