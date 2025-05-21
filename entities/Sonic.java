package entities;
import javax.swing.JPanel;

public enum action {idle, walking, running, falling, damage, crouch};
public class Sonic extends Entity{
    private action playerAction = idle;
    private boolean left;
    private boolean down;
    private boolean right;
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
