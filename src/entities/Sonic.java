package entities;
 
import colliders.*;
import game_parts.direction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import util.SpriteLoader;

 
public class Sonic extends Entity {
public class Sonic extends Entity{
    private direction dir = direction.right;
    private float speedMod, maxSpeed;
    private int rings;
    private HashMap<direction, Collider> colliders = new HashMap<>();
    private HashMap<direction, Boolean> canMove = new HashMap<>();
    private ArrayList<Tile> tiles = new ArrayList<>();
    private int jumping, jSpeed; //valore che indica quanti frame di salto ha Sonic da "spendere" per sollevarsi
    private action playerAction = action.idle;
    private action previousAction = action.idle;
    private SonicAnimator animator;
   private boolean isJumping, isRunning, isInAir = false;
    private boolean isHurt = false;
    private boolean wasMovingLeft = false;
    private boolean wasMovingRight = false;
    private boolean facingRight = true;
    
    // Variabili per scaling automatico
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    private int baseWidth = 36;
    private int baseHeight = 36;
    
    // Riferimenti per scaling automatico
    private static int currentWindowWidth = 800;  // Risoluzione di default
    private static int currentWindowHeight = 600;
    private static final int BASE_WINDOW_WIDTH = 800;
    private static final int BASE_WINDOW_HEIGHT = 600;
     
    private long skiddingStartTime = 0;
    private long hurtStartTime = 0;
    private static final long SKIDDING_DURATION = 300;  
    private static final long HURT_DURATION = 1000;     
  
  
  /*
   private boolean left, right, up, down;
   

    public Sonic(int xPos, int yPos, int xSpeed, int ySpeed, int groundSpeed, int groundAngle, int widthR, int heightR) {
        super(xPos, yPos, xSpeed, ySpeed, groundSpeed, groundAngle, widthR, heightR);
     
    }

  
  */
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
        animator = new SonicAnimator();
    }

    }

  
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
         animator = new SonicAnimator();
    }

    public void playerLoop(){
        update();
    }
   @Override
    public void update() {
        updatePos();
        updateAction();
        updateScaleAutomatically();
        
        // Se l'azione è cambiata, resetta l'animazione
        if (playerAction != previousAction) {
            animator.resetAnimation(playerAction);
            previousAction = playerAction;
        }
    }
    
    
    private void updateScaleAutomatically() {
        // Calcola il fattore di scala basato sulla risoluzione corrente
        float newScaleX = (float) currentWindowWidth / BASE_WINDOW_WIDTH;
        float newScaleY = (float) currentWindowHeight / BASE_WINDOW_HEIGHT;
        
        // Mantieni le proporzioni usando il fattore minore per evitare distorsioni
        float newScale = Math.min(newScaleX, newScaleY);
        
        // Limita scaling per evitare sprites troppo grandi o piccoli
        newScale = Math.max(0.3f, Math.min(4.0f, newScale));
        
        this.scaleX = newScale;
        this.scaleY = newScale;
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
    
   public void updatePos() {
        boolean changedDirection = false;

        if (left && !right) {
            changedDirection = wasMovingRight && isRunning;
            xPos -= xSpeed;
            facingRight = false;
        } else if (!left && right) {
            changedDirection = wasMovingLeft && isRunning;
            xPos += xSpeed;
            facingRight = true;
        }

        wasMovingLeft = left;
        wasMovingRight = right;

        
        if (changedDirection) {
            skiddingStartTime = System.currentTimeMillis();
        }

        if (up && !isJumping) {
            yPos -= 10;
            isJumping = true;
            isInAir = true;
        }

        if (isJumping) {
            yPos += 5;
            if (yPos >= groundY()) {
                yPos = groundY();
                isJumping = false;
                isInAir = false;
            }
        }
    }

    private void updateAction() {
        long currentTime = System.currentTimeMillis();
        
        // Controlla se il timer di hurt è scaduto
        if (isHurt && currentTime - hurtStartTime > HURT_DURATION) {
            isHurt = false;
        }
        
        // Controlla se il timer di skidding è scaduto
        boolean isSkiddingActive = (currentTime - skiddingStartTime) < SKIDDING_DURATION;
        
        // Determina l'azione basata sulla priorità
        if (isHurt) {
            playerAction = action.hurt;
        } else if (isSkiddingActive && !isInAir) {
            playerAction = action.skidding;
        } else if (isJumping) {
            playerAction = action.jumping;
        } else if (isInAir) {
            playerAction = action.falling;
        } else if (left || right) {
            
            if (isRunning) {
                // Se sta correndo velocemente, usa dashing
                if (Math.abs(xSpeed) > 8) {
                    playerAction = action.dashing;
                } else {
                    playerAction = action.running;
                }
            } else {
                playerAction = action.walking;
            }
        } else {
            playerAction = action.idle;
        }
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY) {
        BufferedImage currentFrame = animator.getFrame(playerAction);
        if (currentFrame == null) return;
        
        Graphics2D g2d = (Graphics2D) g;
        
       
        int scaledWidth = (int)(baseWidth * scaleX);
        int scaledHeight = (int)(baseHeight * scaleY);
        
         
        int drawX = xPos + offsetX;
        int drawY = yPos + offsetY;
        
        // Se sta guardando a sinistra, flippa l'immagine
        if (!facingRight) {
            g2d.drawImage(currentFrame, 
                drawX + scaledWidth, drawY, 
                -scaledWidth, scaledHeight, 
                null);
        } else {
            g2d.drawImage(currentFrame, 
                drawX, drawY, 
                scaledWidth, scaledHeight, 
                null);
        }
    }
    
    /**
     * Metodo statico per aggiornare le dimensioni della finestra per tutte le istanze di Sonic 
     */
    public static void setWindowDimensions(int width, int height) {
        currentWindowWidth = width;
        currentWindowHeight = height;
    }
    
    /**
     * Ottiene il fattore di scala corrente (utile per altri elementi del gioco)
     */
    public static float getCurrentScale() {
        float scaleX = (float) currentWindowWidth / BASE_WINDOW_WIDTH;
        float scaleY = (float) currentWindowHeight / BASE_WINDOW_HEIGHT;
        float scale = Math.min(scaleX, scaleY);
        return Math.max(0.3f, Math.min(4.0f, scale));
    }
    
    /**
     * Ottiene le dimensioni della finestra correnti
     */
    public static int getCurrentWindowWidth() { return currentWindowWidth; }
    public static int getCurrentWindowHeight() { return currentWindowHeight; }
    
     
    public int getScaledWidth() {
        return (int)(baseWidth * scaleX);
    }
    
   
    public int getScaledHeight() {
        return (int)(baseHeight * scaleY);
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
 public void setLeft(boolean left) { this.left = left; }
    public void setRight(boolean right) { this.right = right; }
    public void setUp(boolean up) { this.up = up; }
    public void setDown(boolean down) { this.down = down; }
    public void startRunning() { isRunning = true; }
    public void stopRunning() { isRunning = false; }
    public void stopJumping() { isJumping = false; }
    
    public void setHurt(boolean hurt) { 
        if (hurt && !this.isHurt) {
            // Inizia il timer di hurt solo se non era già ferito
            this.hurtStartTime = System.currentTimeMillis();
        }
        this.isHurt = hurt; 
    }
    
    public boolean isHurt() { return isHurt; }

    public void takeDamage(RingManager ringManager) {
        if (!isHurt) { // Previene damage multipli
            setHurt(true);
            ringManager.scatterRings(xPos, yPos, 10);
        }
    }

     
    public action getPlayerAction() { return playerAction; }
    public boolean isFacingRight() { return facingRight; }
    public float getScaleX() { return scaleX; }
    public float getScaleY() { return scaleY; }

    private int groundY() {
        return 300;
    }
 
    
    /**
     */
    public void forceAction(action newAction) {
        if (playerAction != newAction) {
            animator.resetAnimation(newAction);
            previousAction = playerAction;
            playerAction = newAction;
        }
    }}
 


