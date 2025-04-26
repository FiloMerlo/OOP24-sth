//QUESTA CLASSE ASTRATTA è PENSATA PER CREARE TUTTI I NEMICI E SONIC. NON HO FATTO ALTRO CHE IMPLEMENTARE LA LOGICA DEL GIOCO ORIGINALE
package entities;

import java.awt.Color;
import java.awt.Rectangle;

public abstract class Entity {
    protected float xPos, yPos;
    protected float xSpeed, ySpeed;
    protected float groundSpeed, groundAngle;
    protected int width; //R sta per "raggio". indica la larghezza a partire dal centro dell'entità
    protected int height; // indica l'altezza a partire dal centro dell'entità.
    protected Rectangle hitbox;
    public Entity(int xPos, int yPos, int xSpeed, int ySpeed, int groundSpeed, int  groundAngle, int width, int height){
        this.xPos = xPos;
        this.yPos = yPos;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.groundSpeed = groundSpeed;
        this.groundAngle = groundAngle;
        this.width = width;
        this.height = height;
        updateHitbox();
    }

    public void updateHitbox(){
        hitbox = new Rectangle((int)x, (int)y, width, height);
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

        float xIndex = x / Game.tileSize;
        float yIndex = y / Game.tileSize;
        //TODO
        return false;
    }

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

