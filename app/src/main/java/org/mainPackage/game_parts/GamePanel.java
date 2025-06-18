package org.mainPackage.game_parts;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.entities.api.Entity;
import org.mainPackage.engine.entities.api.EntityManager;
import org.mainPackage.graphics.GenericAnimator;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * GamePanel gestisce il rendering della mappa e delle entità.
 */
public class GamePanel extends JPanel {
    private static final int tileWidth = 5;
    private static final int tileHeight = 5;
    private static final int nColumns = 10;
    private static final int nRows = 5;
    private static final String tilePath= "/tile.jpg";
    private final ArrayList<Rectangle> tiles;
    private final int[][] grid = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    private BufferedImage tileImage;
    private final EntityManager em;

    /**
     * @param em il gestore delle entità da disegnare
     * @throws IOException 
     */
    public GamePanel(EntityManager em) throws IOException {
        setPreferredSize(new Dimension(nColumns * tileWidth, nRows * tileHeight));
        setFocusable(true);
        this.em = em;
        this.tiles = new ArrayList<>();
        this.tileImage=ImageIO.read(new File(tilePath));

        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nColumns; c++) {
                if (grid[r][c] == 1) {
                    tiles.add(new Rectangle(c * tileWidth, r * tileHeight, tileWidth, tileHeight));
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.CYAN);
        g2.fillRect(0, 0, getWidth(), getHeight());

        for (Rectangle tile : tiles) {
            g2.setColor(Color.LIGHT_GRAY);
            g2.fill(tile);
            if (tileImage != null) {
                g2.drawImage(tileImage, tile.x, tile.y, this);
            }
        }

        for (Entity e : em.getEntities()) {
            if (e.hasComponent(GenericAnimator.class)) {
                GenericAnimator<?> animator = e.getComponent(GenericAnimator.class);

                animator.getCurrentFrame().ifPresent(frame -> {
                    if (e.hasComponent(TransformComponent.class)) {
                        TransformComponent transform = e.getComponent(TransformComponent.class);
                        int x = (int) transform.getX();
                        int y = (int) transform.getY();
                        g2.drawImage(frame, x, y, frame.getWidth(), frame.getHeight(), null);
                    }
                });
            }
        }
    }

    /**
     * @return lista dei tile 
     */
    public ArrayList<Rectangle> getTileList() {
        return tiles;
    }
}
