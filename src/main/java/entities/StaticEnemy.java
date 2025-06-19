package entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class StaticEnemy extends Entity {
    private StaticEnemyAnimator animator;
    private int baseWidth = 36;
    private int baseHeight = 36;

    public StaticEnemy(int x, int y, int width, int height) {
        super(x, y, 0, 0, 0, 0, width, height);
        this.animator = new StaticEnemyAnimator();
    }

    @Override
    public void update() {
         
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY) {
        Graphics2D g2d = (Graphics2D) g;
        BufferedImage frame = animator.getFrame();
        if (frame == null) return;

        float scale = Sonic.getCurrentScale();
        int scaledWidth = (int)(baseWidth * scale);
        int scaledHeight = (int)(baseHeight * scale);

        int drawX = xPos - offsetX;
        int drawY = yPos - offsetY;

        g2d.drawImage(frame, drawX, drawY, scaledWidth, scaledHeight, null);
    }

    @Override
    public void doDamage(Sonic sonic) {
        if (!sonic.isHurt()) {
            sonic.setHurt(true);
        }
    }
}
