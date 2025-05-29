 
//QUESTA CLASSE ASTRATTA è PENSATA PER CREARE TUTTI I NEMICI E SONIC. NON HO FATTO ALTRO CHE IMPLEMENTARE LA LOGICA DEL GIOCO ORIGINALE
package entities;
import javax.swing.JPanel;

public abstract class Enemy {
    private string name;
    private float xPos, yPos, xSpeed, ySpeed, width, height;
    private Collider hitbox;
    private BufferedImage sprite;
    //private Rectangle hitbox;
    public Enemy(float xPos, float yPos){
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
      
    public int getWidthR() { return widthR; }
    public int getHeightR() { return heightR; }
    //Questa sezione non mi riguarda (Samuele) perché sono metodi che tanto devo Overridare
    public void moveX(){
        
    }
    public void moveY(){
        
    }
    public void eliminate(){
        
    }
   public abstract void update();
    public abstract void draw(Graphics g, int offsetX, int offsetY);

    /**
     * infligge danno a sonic se applicabile
     */
    public void doDamage(Sonic sonic) {
        // vuoto per entità non ostili
    }
}
