package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * rappresenta un anello animato che può essere raccolto da sonic.
 */
public class RingRender extends Entity {
    private BufferedImage[] animationFrames;
    private int frameIndex = 0;
    private int frameDelay = 10;
    private int tick = 0;
    private boolean collected = false;

    public RingRender(int xPos, int yPos, BufferedImage[] frames) {
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
            g.drawImage(animationFrames[frameIndex], xPos - offsetX, yPos - offsetY, null);
        }
    }

    public void collect() {
        collected = true;
    }

    public boolean isCollected() {
        return collected;
    }
}
