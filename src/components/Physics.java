package components;

import java.awt.Rectangle;

public abstract class Physics implements Component{
    protected float xSpeed, ySpeed;
    protected MovableBody body;
    protected Rectangle hitbox;
    public Physics(float xS, float yS, MovableBody b){
        xSpeed = xS;
        ySpeed = yS;
        body = b;
        hitbox = new Rectangle(body.getX(), body.getY(), body.getWidth(), body.getHeight());
    }
    public Rectangle getHitbox(){
        return hitbox;
    }
    public Body getBody(){
        return body;
    }
    public void moveX(){}
    public void moveY(){}
    public void die(){ 
        body.getOwner().delete(); 
    }
    public void update(){

    }
}
