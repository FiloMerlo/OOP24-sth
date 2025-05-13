//QUESTA CLASSE ASTRATTA è PENSATA PER CREARE TUTTI I NEMICI E SONIC. NON HO FATTO ALTRO CHE IMPLEMENTARE LA LOGICA DEL GIOCO ORIGINALE
package entities;

import java.awt.Color;
import java.awt.Rectangle;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry;

public abstract class Entity {
    private float xPos, yPos;
    private float xSpeed, ySpeed;
    private float groundSpeed, groundAngle;
    private int width, height;
    private Body hitbox;
    //private Rectangle hitbox;
    public Entity(int xPos, int yPos, int xSpeed, int ySpeed, int groundSpeed, int  groundAngle, int width, int height, org.dyn4j.world.World<PhysicsBody> whereAmI){

        this.xPos = xPos;
        this.yPos = yPos;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.groundSpeed = groundSpeed;
        this.groundAngle = groundAngle;
        this.width = width;
        this.height = height;
        //creo la hitbox del personaggio e le dò la capacità di collidere con le altre entità
        this.hitbox = new Body();
        hitbox.addFixture(new Geometry.createRectangle(width, height));
        hitbox.setMass(MassType.NORMAL);
        hitbox.setGravityScale(0.5);
        whereAmI.addBody(hitbox);
        //moveHitbox();
    }
    /*protected void moveHitbox(){
        hitbox.setLocation(xPos, yPos);
    }
    protected void drawHitbox(Graphics g){
        g.setColor(Color.RED);
        g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }
    public static boolean isColliding(float x, float y, int width, int height, int[][] lvlData){
        if(x < 0 || x > Game.GameWindow.width){
            return true;
        }
        if (y < 0 || y > Game.GameWindow.height){
            return true;
        }
        if (isTile(x, y, lvlData)){
            return true;
        }
        return false;
    }*/

    public float getGroundSpeed(){
        return groundSpeed;
    }
    public float getGroundAngle(){
        return groundAngle;
    }
    public float getXSpeed(){
        return xSpeed;
    }
    public float getYSpeed(){
        return ySpeed;
    }
    public float getXpos(){
        return xPos;
    }
    public float getYpos(){
        return xPos;
    }
}