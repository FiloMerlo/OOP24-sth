package org.mainPackage.renderer;

import org.mainPackage.core.GamePanel;
import org.mainPackage.engine.components.HUDComponent;
import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.components.PhysicsTypes.PlayerPhysics;
import org.mainPackage.engine.components.graphics.GenericAnimator;
import org.mainPackage.engine.entities.api.Entity;
import org.mainPackage.engine.entities.impl.EntityManagerImpl;
import org.mainPackage.enums.direction;

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
    private direction d = direction.right;
    
    
    public PlayingRenderer(EntityManagerImpl entityManager, int[][] grid, int tileSize) {
        this.entityManager = entityManager;
        this.levelGrid = grid;
        this.tileWorldSize = tileSize;
        this.cameraX = 0;
        this.cameraY = 0;
        /* manca il caricamento dell'immagine delle tiles */
    }

     public void updateCamera(int targetX, int targetY) {
        // Calcola la posizione della telecamera per centrare il target
        cameraX = targetX - currentScreenWidth / 2;
        cameraY = targetY - currentScreenHeight / 2;

        // Calcola le dimensioni totali del mondo
        int worldWidth = levelGrid[0].length * tileWorldSize;
        int worldHeight = levelGrid.length * tileWorldSize;

        // Blocca il bordo sinistro
        if (cameraX < 0) cameraX = 0;
        // Blocca il bordo superiore
        if (cameraY < 0) cameraY = 0;
        
        
        if (worldWidth < currentScreenWidth) {
            cameraX = (worldWidth - currentScreenWidth) / 2;
        } else if (cameraX + currentScreenWidth > worldWidth) {
            // Blocca al bordo destro del mondo
            cameraX = worldWidth - currentScreenWidth;
        }

        
        if (worldHeight < currentScreenHeight) {
            cameraY = (worldHeight - currentScreenHeight) / 2;
        } else if (cameraY + currentScreenHeight > worldHeight) {
            // Blocca al bordo inferiore del mondo
            cameraY = worldHeight - currentScreenHeight;
        }
    }
    
    public void updateViewPort(int screenWidth, int screenHeight) {
        // Se le dimensioni dello schermo sono cambiate, aggiorna e ricalcola le dimensioni delle tiles.
        if (this.currentScreenWidth != screenWidth || this.currentScreenHeight != screenHeight) {
            this.currentScreenWidth = screenWidth;
            this.currentScreenHeight = screenHeight;
            //updateTileDimensions();
        }
    }

    /* 
    private void updateTileDimensions() {
        this.tileWidth = this.currentScreenWidth / TARGET_TILES_ON_SCREEN_WIDTH;
        this.tileHeight = this.currentScreenHeight / TARGET_TILES_ON_SCREEN_HEIGHT;
        
        this.tileWidth = Math.max(1, this.tileWidth);
        this.tileHeight = Math.max(1, this.tileHeight);
    }
    /* da modificare */
    
    
    
    @Override
    public void render(Graphics2D g2d, int width, int height) {
        
        Graphics2D g = (Graphics2D) g2d.create();
        
        // Disegna lo sfondo fisso
        drawBackground(g, width, height); 
        
        g.translate(-cameraX, -cameraY); //effetto della camera
        
        drawTiles(g); 
        /* disegno delle entità */
        drawGameEntities(g);


        drawHitboxes(g);

        //drawTileHitboxes(g);


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
       drawClouds(g, width, height);
    }
    
 
    private void drawClouds(Graphics2D g, int width, int height) {
        g.setColor(Color.WHITE);
        
        // Nuvola 1
        g.fillOval(100, 50, 60, 40);
        g.fillOval(120, 40, 80, 50);
        g.fillOval(140, 50, 60, 40);
        
        // Nuvola 2
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
                if (levelGrid[r][c] == 1) { // Disegna solo le tile di tipo '1'
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

            /*Default to facing right if no PlayerPhysics component found*/

            if (e.hasComponent(PlayerPhysics.class)) {
                PlayerPhysics physics = e.getComponent(PlayerPhysics.class);
                d = physics.getDirection();
            }

            animator.getCurrentFrame().ifPresent(frame -> {
                int x = (int) (transform.getX());
                int y = (int) (transform.getY());
                int w = frame.getWidth();
                int h = frame.getHeight();

                if (d==direction.left) {
                    /*Draw flipped horizontally*/
                    g.drawImage(frame, x + w, y, -w, h, null);
                } else {
                    /*Normal draw*/
                    g.drawImage(frame, x, y, w, h, null);
                }
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


    private void drawHitboxes(Graphics2D g) {
    List<Entity> entities = entityManager.getEntities();
    g.setColor(Color.RED);

    for (Entity e : entities) {
        if (e.hasComponent(TransformComponent.class)) {
            TransformComponent transform = e.getComponent(TransformComponent.class);

            int x = (int) transform.getX();
            int y = (int) transform.getY();
            int w = (int) transform.getWidth();
            int h = (int) transform.getHeight();

            g.drawRect(x, y, w, h); 
        }
    }
}

private void drawTileHitboxes(Graphics2D g2d) {
    g2d.setColor(new Color(0, 255, 0, 100));

    int startCol = Math.max(0, cameraX / tileWorldSize);
    int endCol = Math.min(levelGrid[0].length, (cameraX + currentScreenWidth) / tileWorldSize + 1);
    int startRow = Math.max(0, cameraY / tileWorldSize);
    int endRow = Math.min(levelGrid.length, (cameraY + currentScreenHeight) / tileWorldSize + 1);

    for (int r = startRow; r < endRow; r++) {
        for (int c = startCol; c < endCol; c++) {
            if (levelGrid[r][c] == 1) {
                int x = c * tileWorldSize;
                int y = r * tileWorldSize;
                g2d.fillRect(x, y, tileWorldSize, tileWorldSize);
                g2d.setColor(Color.GREEN);
                g2d.drawRect(x, y, tileWorldSize, tileWorldSize);
                g2d.setColor(new Color(0, 255, 0, 100)); 
            }
        }
    }
}
}   
   
