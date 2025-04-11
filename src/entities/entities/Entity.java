//QUESTA CLASSE ASTRATTA è PENSATA PER CREARE TUTTI I NEMICI. NON HO FATTO ALTRO CHE IMPLEMENTARE LA LOGICA DEL GIOCO ORIGINALE
package entities;
public abstract class Entity {
    protected int xPos, yPos;
    protected int xSpeed, ySpeed;
    protected int groundSpeed, groundAngle;
    protected int widthR; //R sta per "raggio". indica la larghezza a partire dal centro dell'entità
    protected int heightR; // indica l'altezza a partire dal centro dell'entità.

    public Entity(int xPos, int yPos, int xSpeed, int ySpeed, int groundSpeed, int  groundAngle, int widthR, int heightR){
        this.xPos = xPos;
        this.yPos = yPos;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.groundSpeed = groundSpeed;
        this.groundAngle = groundAngle;
        this.widthR = widthR;
        this.heightR = heightR;
    }

    public int getGroundSpeed(){
        return groundSpeed;
    }
    public int getGroundAngle(){
        return groundAngle;
    }
    public int getXSpeed(){
        return xSpeed;
    }
    public int getYSpeed(){
        return ySpeed;
    }
    public int getXpos(){
        return xPos;
    }
    public int getYpos(){
        return xPos;
    }
}
