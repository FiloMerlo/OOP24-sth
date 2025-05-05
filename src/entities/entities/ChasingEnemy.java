package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ChasingEnemy extends Entity {
    private BufferedImage sprite;
    private int spawnX, spawnY, chaseRange;

    public ChasingEnemy(int x, int y, int widthR, int heightR, int chaseRange) {
        super(x, y, 1, 0, 0, 0, widthR, heightR);
        this.spawnX = x;
        this.spawnY = y;
        this.chaseRange = chaseRange;
        sprite = SpriteLoader.getChasingEnemySprite();
    }

    public void update(int sonicX) {
        int distance = Math.abs(sonicX - xPos);

        if (distance < chaseRange) {
            g += sonicX < xPos ? -xSpeed : xSpeed;
        } else {
            // Torna alla posizione iniziale
            if (xPos < spawnX) xPos += xSpeed;
            else if (get > spawnX) xPos -= xSpeed;
        }
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
        g.drawImage(sprite, xPos - widthR - offsetX, yPos - heightR - offsetY, null);
    }
}
