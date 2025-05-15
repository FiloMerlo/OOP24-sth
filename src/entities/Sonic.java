package entities;
import colliders.*;
import java.util.HashMap;

public enum direction{left, up, right, down};

public class Sonic extends Entity{
    private direction dir = direction.right;
    private float speedMod;
    private int rings;
    private HashMap<String, Collider> colliders = new HashMap<>();
    private HashMap<String, Boolean> canMove = new HashMap<>();

    public Sonic(float xPos , float yPos){
        super.Entity(xPos, yPos);
        this.width = 15;
        this.height = 20;
        this.xSpeed = 5;
        this.ySpeed = 5;
        this.rings = 0;
        this.speedMod = 0.5;
        this.canMove.put(left, true);
        this.canMove.put(up, true);
        this.canMove.put(right, true);
        this.canMove.put(down, false);
        this.colliders.put(left, new HorizontalCollider(xPos - width/2, yPos - height/2, 1, 1, null, this));
    }

    public boolean getLeft(){
        return left;
    }
    public boolean getDown(){
        return down;
    }
    public boolean getRight(){
        return right;
    }
    public void setLeft(boolean bool){
        left = bool;
    }
    public void setDown(boolean bool){
        down = bool;
    }
    public void setRight(boolean bool){
        right = bool;
    }
    
    public void update(){
        updatePos();
        updateAction();
        //qui si fa anche l'update delle animazioni
    }
    public void updateAction(){ //rivaluta, in base alla velocità, se sonic è fermo, sta camminando o correndo
        
    }

    public void updatePos(){
        if (left && !right){
            x -= xSpeed;
        } else if (!left && right){
            x += xSpeed;
        }
    }

    public void setAction(action a){
        this.playerAction = a;
    }

    public void setDirection(direction d){
        this.playerDirection = d;
    }
}
