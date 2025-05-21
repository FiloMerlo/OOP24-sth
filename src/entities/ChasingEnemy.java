package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * nemico che insegue sonic entro un certo raggio.
 */
public class ChasingEnemy extends Entity {
    private BufferedImage sprite;
    private int spawnX, spawnY, chaseRange;

    public ChasingEnemy(int x, int y, int widthR, int heightR, int chaseRange) {
        super(x, y, 1, 0, 0, 0, widthR, heightR);
        this.spawnX = x;
        this.spawnY = y;
        this.chaseRange = chaseRange;
        this.sprite = SpriteLoader.getChasingEnemySprite();
    }

    public void update(int sonicX) {
        int distance = Math.abs(sonicX - xPos);

        if (distance < chaseRange) {
            xPos += sonicX < xPos ? -xSpeed : xSpeed;
        } else {
            if (xPos < spawnX) xPos += xSpeed;
            else if (xPos > spawnX) xPos -= xSpeed;
        }
    }

    @Override
    public void update() {
        
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY) {
        g.drawImage(sprite, xPos - widthR - offsetX, yPos - heightR - offsetY, null);
    }

    @Override
    public void doDamage(Sonic sonic) {
        if (!sonic.isHurt()) {
            sonic.setHurt(true);
        }
    }
}
