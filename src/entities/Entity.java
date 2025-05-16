//QUESTA CLASSE ASTRATTA è PENSATA PER CREARE TUTTI I NEMICI E SONIC. NON HO FATTO ALTRO CHE IMPLEMENTARE LA LOGICA DEL GIOCO ORIGINALE
package entities;
import javax.swing.JPanel;

public abstract class Entity {
    private string name;
    private float xPos, yPos, xSpeed, ySpeed, width, height;
    private Collider hitbox;
    private BufferedImage sprite;
    //private Rectangle hitbox;
    public Entity(float xPos, float yPos){
        this.xPos = xPos;
        this.yPos = yPos;
        //Il resto dei parametri vengono inizializzati nelle sottoclassi
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

    //Questa sezione non mi riguarda (Samuele) perché sono metodi che tanto devo Overridare
    public void moveX(){
        
    }
    public void moveY(){
        
    }
    public void eliminate(){
        
    }
}