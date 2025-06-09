package org.mainPackage.game_parts;

import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import org.mainPackage.engine.entities.impl.EntityManagerImpl;
public class GamePanel extends JPanel{
    private static final int tileWidth = 5;
    private static final int tileHeight = 5;
    private static final int nColumns = 10;
    private static final int nRows = 5;
    private ArrayList<Rectangle> tiles;
    private int[][] grid = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    private BufferedImage tileImage;
    private EntityManagerImpl entityManager;

    public GamePanel(EntityManagerImpl em) {
        setPreferredSize(new Dimension(nColumns * tileWidth, nRows * tileHeight));
        setFocusable(true);
        
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nColumns; c++) {
              if (grid[r][c] == 1) {
                Rectangle tile = new Rectangle(c * tileWidth, r * tileHeight, tileWidth, tileHeight);
                tiles.add(tile);
              }
            }
        }   
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        /*Draw background*/
        g2.setColor(Color.CYAN);
        g2.fillRect(0, 0, getWidth(), getHeight());
        /*Draw tiles*/
        for (java.awt.Rectangle tile : tiles) {
            g2.setColor(Color.LIGHT_GRAY);
            g2.fill(tile);
            if (tileImage != null) {
                /*TODO g2.drawImage*/
            }
        }
        /*Draw player*/
        
    }

    public ArrayList<Rectangle> getTileList(){ return tiles; }
}
