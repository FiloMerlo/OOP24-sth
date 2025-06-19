package entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ChasingEnemy extends Entity {
    private ChasingEnemyAnimator animator;
    private int spawnX, chaseRange;
    private boolean facingRight = true;

    private int baseWidth = 36;
    private int baseHeight = 36;

    public ChasingEnemy(int x, int y, int widthR, int heightR, int chaseRange) {
        super(x, y, 1, 0, 0, 0, widthR, heightR);
        this.spawnX = x;
        this.chaseRange = chaseRange;
        this.animator = new ChasingEnemyAnimator();
    }

    public void update(int sonicX) {
        int distance = Math.abs(sonicX - xPos);
        if (distance < chaseRange) {
            facingRight = sonicX >= xPos;
            xPos += facingRight ? xSpeed : -xSpeed;
        } else {
            if (xPos < spawnX) {
                xPos += xSpeed;
                facingRight = true;
            } else if (xPos > spawnX) {
                xPos -= xSpeed;
                facingRight = false;
            }
        }
    }

    @Override
    public void update() {}

    @Override
    public void draw(Graphics g, int offsetX, int offsetY) {
        Graphics2D g2d = (Graphics2D) g;
        BufferedImage frame = animator.getFrame();

        float scale = Sonic.getCurrentScale();
        int scaledWidth = (int)(baseWidth * scale);
        int scaledHeight = (int)(baseHeight * scale);

        int drawX = xPos - offsetX;
        int drawY = yPos - offsetY;

        if (!facingRight) {
            g2d.drawImage(frame, drawX + scaledWidth, drawY, -scaledWidth, scaledHeight, null);
        } else {
            g2d.drawImage(frame, drawX, drawY, scaledWidth, scaledHeight, null);
        }
    }

    @Override
    public void doDamage(Sonic sonic) {
        if (!sonic.isHurt()) {
            sonic.setHurt(true);
        }
    }
}
