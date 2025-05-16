package entities;
import colliders.*;
import game_parts.direction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sonic extends Entity{
    private direction dir = direction.right;
    private float speedMod, maxSpeed;
    private int rings;
    private HashMap<direction, Collider> colliders = new HashMap<>();
    private HashMap<direction, Boolean> canMove = new HashMap<>();
    private ArrayList<Tile> tiles = new ArrayList<>();
    private int jumping, jSpeed; //valore che indica quanti frame di salto ha Sonic da "spendere" per sollevarsi

    public Sonic(float x , float y, ArrayList<Tile> t){
        super(x, y);
        width = 15;
        height = 20;
        xSpeed = 5;
        ySpeed = 10;
        jSpeed = 3;
        speedMod = 0.5;
        maxSpeed = 15;
        rings = 0;
        canMove.put(left, true);
        canMove.put(up, true);
        canMove.put(right, true);
        canMove.put(down, false);
        tiles = t;
        initializeColliders(tiles);
        playerDirection = right;
        jumping = 0;

        addKeyListener(new PlayerInputs(this));
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.fillRect(xPos, yPos, width, height);
    }
    public void playerLoop(){
        updatePos();
        updateAction();
        //qui si fa anche l'update delle animazioni
    }
    public void initializeColliders(ArrayList<Tile> tiles){ 
        ArrayList<Tile> ceilings = new ArrayList<>();    
        ArrayList<Tile> floors = new ArrayList<>();    
        ArrayList<Tile> walls = new ArrayList<>();    
        for (Tile t : tiles){
            if (t.getType() == "ceiling"){
                ceilings.add(t);
            } else if (t.getType() == "floor"){
                floors.add(t);
            } else if (t.getType() == "wall"){
                walls.add(t);
            }
        }
        colliders.put(up, new UpCollider(xPos, yPos + height/2, 1, 1, ceilings, this));
        colliders.put(down, new DownCollider(xPos, yPos - height/2, 1, 1, floors, this));
        colliders.put(left, new HorizontalCollider(xPos - width/2, yPos, 1, 1, walls, this));
        colliders.put(right, new HorizontalCollider(xPos + width/2, yPos, 1, 1, walls, this));
        hitbox = new Collider();
    }
    
    public void updateAction(){ //rivaluta, in base alla velocità, se sonic è fermo, sta camminando o correndo
        
    }
    public void updatePos(){
        
    }

    public void setDirection(direction d){
        this.playerDirection = d;
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
        }
    }

    public void setMovement(direction dir, boolean bool){
        canMove.replace(dir, bool);
    }

    public void checkCollisions(){
        for (Map<direction, Collider>.Entry c : colliders.entrySet()) {
            c.check();
        }
    }

    public void loseRings(){
        rings = 0;
    }

    public void brake(){
        xSpeed = 5;
    }

    public Rectangle getHitbox(){
        return hitbox.getSensor();
    }


}

