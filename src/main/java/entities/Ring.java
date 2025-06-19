package entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Ring extends Entity {
    private BufferedImage[] animationFrames;
    private int frameIndex = 0;
    private int frameDelay = 8;
    private int tick = 0;
    private boolean collected = false;

    private int baseSize = 16;

    public Ring(int xPos, int yPos, BufferedImage[] frames) {
        super(xPos, yPos, 0, 0, 0, 0, 16, 16);
        this.animationFrames = frames;
    }

    @Override
    public void update() {
        if (!collected) {
            tick++;
            if (tick >= frameDelay) {
                tick = 0;
                frameIndex = (frameIndex + 1) % animationFrames.length;
            }
        }
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY) {
        if (!collected) {
            Graphics2D g2d = (Graphics2D) g;

            float scale = Sonic.getCurrentScale();
            int size = (int)(baseSize * scale);

            int drawX = xPos - offsetX;
            int drawY = yPos - offsetY;

            g2d.drawImage(animationFrames[frameIndex], drawX, drawY, size, size, null);
        }
    }

    public void collect() {
        collected = true;
    }

    public boolean isCollected() {
        return collected;
    }
}
