package main;

import java.awt.*;

public class TextPosition {
    public static void drawCentered(Graphics2D g, String text, int width, int y) {
        FontMetrics fm = g.getFontMetrics();
        int x = (width - fm.stringWidth(text)) / 2;
        g.drawString(text, x, y);
    }

    public static void drawCenteredRectText(Graphics2D g, String text, Rectangle r) {
        FontMetrics fm = g.getFontMetrics();
        int x = r.x + (r.width - fm.stringWidth(text)) / 2;
        int y = r.y + (r.height - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(text, x, y);
    }
}