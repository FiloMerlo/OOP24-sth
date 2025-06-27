package org.mainPackage.renderer;

import org.mainPackage.core.GamePanel;
import org.mainPackage.engine.components.HUDComponent;
import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.components.graphics.GenericAnimator;
import org.mainPackage.engine.entities.api.Entity;
import org.mainPackage.engine.entities.impl.EntityManagerImpl;

import java.awt.*;
import java.util.List;

public class PlayingRenderer implements Renderer {

    private final EntityManagerImpl entityManager;
    private final int[][] levelGrid;
    private final int tileWorldSize;

    private int cameraX, cameraY;
    private int currentScreenWidth = GamePanel.DEFAULT_WIDTH;
    private int currentScreenHeight = GamePanel.DEFAULT_HEIGHT;

    private static final Color SKY_COLOR_TOP = new Color(135, 206, 250); 
    private static final Color SKY_COLOR_BOTTOM = new Color(255, 218, 185); 

    public PlayingRenderer(EntityManagerImpl entityManager, int[][] grid, int tileSize) {
        this.entityManager = entityManager;
        this.levelGrid = grid;
        this.tileWorldSize = tileSize;
        this.cameraX = 0;
        this.cameraY = 0;
    }

    public void updateCamera(int targetX, int targetY) {
        cameraX = targetX - currentScreenWidth / 2;
        cameraY = targetY - currentScreenHeight / 2;

        int worldWidth = levelGrid[0].length * tileWorldSize;
        int worldHeight = levelGrid.length * tileWorldSize;

        if (cameraX < 0) cameraX = 0;
        if (cameraY < 0) cameraY = 0;

        if (worldWidth < currentScreenWidth) {
            cameraX = (worldWidth - currentScreenWidth) / 2;
        } else if (cameraX + currentScreenWidth > worldWidth) {
            cameraX = worldWidth - currentScreenWidth;
        }

        if (worldHeight < currentScreenHeight) {
            cameraY = (worldHeight - currentScreenHeight) / 2;
        } else if (cameraY + currentScreenHeight > worldHeight) {
            cameraY = worldHeight - currentScreenHeight;
        }
    }

    public void updateViewPort(int screenWidth, int screenHeight) {
        if (this.currentScreenWidth != screenWidth || this.currentScreenHeight != screenHeight) {
            this.currentScreenWidth = screenWidth;
            this.currentScreenHeight = screenHeight;
        }
    }

    @Override
    public void render(Graphics2D g2d, int width, int height) {
        Graphics2D g = (Graphics2D) g2d.create();

        
        // Disegna lo sfondo fisso
        drawBackground(g, width, height);
        
        g.translate(-cameraX, -cameraY); //effetto della camera
        
        drawTiles(g); 
        /* disegno delle entità */
        drawGameEntities(g);

        drawHUB(g2d, width, height); //sitemare la posizione dell'HUB
        
        g.dispose();
    }

    private void drawBackground(Graphics2D g, int width, int height) {
        GradientPaint skyGradient = new GradientPaint(
            0, 0, SKY_COLOR_TOP,
            0, height * 0.7f, SKY_COLOR_BOTTOM
        );
        g.setPaint(skyGradient);
        g.fillRect(0, 0, width, height); 
       
        g.fillRect(0, 0, width, height);

        drawClouds(g, width, height);
    }

    private void drawClouds(Graphics2D g, int width, int height) {
        g.setColor(Color.WHITE);

        g.fillOval(100, 50, 60, 40);
        g.fillOval(120, 40, 80, 50);
        g.fillOval(140, 50, 60, 40);

        g.fillOval(width - 200, 80, 70, 45);
        g.fillOval(width - 180, 70, 90, 55);
        g.fillOval(width - 160, 80, 70, 45);
    }

    private void drawTiles(Graphics2D g2d) {
        int startCol = Math.max(0, cameraX / tileWorldSize);
        int endCol = Math.min(levelGrid[0].length, (cameraX + currentScreenWidth) / tileWorldSize + 1);
        int startRow = Math.max(0, cameraY / tileWorldSize);
        int endRow = Math.min(levelGrid.length, (cameraY + currentScreenHeight) / tileWorldSize + 1);

        for (int r = startRow; r < endRow; r++) {
            for (int c = startCol; c < endCol; c++) {
                if (levelGrid[r][c] == 1) {
                    int x = c * tileWorldSize;
                    int y = r * tileWorldSize;
                    
                    //if (tileImage != null) {
                      //  g2d.drawImage(tileImage, x, y, tileWorldSize, tileWorldSize, null);
                    //} else {
                        g2d.setColor(Color.LIGHT_GRAY);
                        g2d.fillRect(x, y, tileWorldSize, tileWorldSize);
                        g2d.setColor(Color.DARK_GRAY);
                        g2d.drawRect(x, y, tileWorldSize, tileWorldSize);
                    }
                }
            }
        }
    
    private void drawGameEntities(Graphics2D g) {
        List<Entity> entities = entityManager.getEntities();
        for (Entity e : entities) {
           
            if (e.hasComponent(GenericAnimator.class) && e.hasComponent(TransformComponent.class)) {
                GenericAnimator<?> animator = e.getComponent(GenericAnimator.class);
                TransformComponent transform = e.getComponent(TransformComponent.class);
                
                animator.getCurrentFrame().ifPresent(frame -> {
                    int x = (int) (transform.getX());
                    int y = (int) (transform.getY());
                    g.drawImage(frame, x, y, frame.getWidth(), frame.getHeight(), null);
                });
            }
        }
    }

    private void drawHUB(Graphics2D g, int width, int height) {
        List<Entity> entities = entityManager.getEntities();
        for (Entity e : entities) {
            if (e.hasComponent(HUDComponent.class)) {
                HUDComponent hud = e.getComponent(HUDComponent.class);
                if (hud != null) {
                    hud.render(g, width, height);
                }
            }
        }
    }

}   
   
