package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * gestisce gli anelli nel livello e la dispersione quando sonic viene colpito.
 */
public class RingManager {
    private List<Ring> rings;
    private BufferedImage[] ringFrames;

    public RingManager(BufferedImage[] ringFrames) {
        this.rings = new ArrayList<>();
        this.ringFrames = ringFrames;
    }

    public void update() {
        for (Ring ring : rings) {
            ring.update();
        }
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
        for (Ring ring : rings) {
            ring.draw(g, offsetX, offsetY);
        }
    }

    public void addRing(int x, int y) {
        rings.add(new Ring(x, y, ringFrames));
    }

    public void scatterRings(int x, int y, int count) {
        for (int i = 0; i < count; i++) {
            int offsetX = (int)(Math.random() * 100) - 50;
            int offsetY = (int)(Math.random() * 50) - 25;
            rings.add(new Ring(x + offsetX, y + offsetY, ringFrames));
        }
    }

    public List<Ring> getRings() {
        return rings;
    }
}
