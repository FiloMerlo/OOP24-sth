package entities;

import java.awt.Color;
import java.awt.Graphics;

/**
 * nemico statico che non si muove.
 */
public class StaticEnemy extends Entity {

    public StaticEnemy(int x, int y, int width, int height) {
        super(x, y, 0, 0, 0, 0, width, height);
    }

    @Override
    public void update() {}

    @Override
    public void draw(Graphics g, int offsetX, int offsetY) {
        g.setColor(Color.RED);
        g.fillRect(xPos - widthR - offsetX, yPos - heightR - offsetY, widthR * 2, heightR * 2);
    }

    @Override
    public void doDamage(Sonic sonic) {
        if (!sonic.isHurt()) {
            sonic.setHurt(true);
        }
    }
}
