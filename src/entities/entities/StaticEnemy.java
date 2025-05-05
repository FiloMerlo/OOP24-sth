package entities;

import java.awt.Color;
import java.awt.Graphics;

public class StaticEnemy extends Entity {

    public StaticEnemy(int x, int y) {
        super(x, y, 0, 0, 0, 0, 16, 16);
    }

    public void update() {
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
        g.setColor(Color.RED);
        g.fillRect(
            getXpos() - getWidthR() - offsetX,
            getYpos() - getHeightR() - offsetY,
            getWidthR() * 2,
            getHeightR() * 2
        );
    }
}
