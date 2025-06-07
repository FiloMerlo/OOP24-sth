package components;

import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class Physics implements Component{
    protected int xSpeed, ySpeed;
    protected Rectangle hitbox;
    protected Entity owner;
    protected ArrayList<Rectangle> tiles;
    public Physics(int xS, int yS, Entity o, ArrayList<Rectangle>tList){
        xSpeed = xS;
        ySpeed = yS;
        owner = o;
        hitbox = new Rectangle(owner.getX(), owner.getY(), owner.getWidth(), owner.getHeight());
        tiles = tList;
    }
    public Rectangle getHitbox(){
        return hitbox;
    }

    public void die(){ 
        owner.delete(); 
    }
    public void update(){
        hitbox.setLocation(owner.getX(), owner.getY());
    }
}
