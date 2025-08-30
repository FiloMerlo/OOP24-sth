package org.mainPackage.renderer;

import org.mainPackage.core.GamePanel;
import org.mainPackage.engine.components.GoalComponent;
import org.mainPackage.engine.components.HUDComponent;
import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.components.PhysicsTypes.PlayerPhysics;
import org.mainPackage.engine.components.graphics.GenericAnimator;
import org.mainPackage.engine.entities.api.Entity;
import org.mainPackage.engine.entities.impl.EntityImpl;
import org.mainPackage.engine.systems.EntityManagerImpl;
import org.mainPackage.enums.direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Renderer for the playing state of the game.
 * <p>
 * This class is responsible for rendering the game world, including tiles, entities,
 * background, and HUD elements.
 * It also manages the camera position and viewport updates based on the player's position.
 * <p>
 * This class implements the Renderer interface and provides methods to draw the playing state on the screen.
 */

public class PlayingRenderer implements Renderer {
    
    private final EntityManagerImpl entityManager;
    private final int[][] levelGrid;
    private final int tileWorldSize;
    private final static int TILE = 1; 
   
    private int cameraX, cameraY;
    
    // --- The current screen width and height, used to adjust the camera and viewport ---
    private int currentScreenWidth = GamePanel.DEFAULT_WIDTH;
    private int currentScreenHeight = GamePanel.DEFAULT_HEIGHT;

    private static final Color SKY_COLOR_TOP = new Color(135, 206, 250); 
    private static final Color SKY_COLOR_BOTTOM = new Color(255, 218, 185); 
    
    private direction d = direction.right;
    
    /** 
     * Constructor for PlayingRenderer.
     * @param entityManager The EntityManagerImpl instance managing game entities.
     * @param grid The 2D array representing the level grid, where each cell indicates the type of tile.
     * @param tileSize The size of each tile in the world, used for rendering and camera calculations.
     */
    
     public PlayingRenderer(EntityManagerImpl entityManager, int[][] grid, int tileSize) {
        this.entityManager = entityManager;
        this.levelGrid = grid;
        this.tileWorldSize = tileSize;
        this.cameraX = 0;
        this.cameraY = 0;
    }

    /**
     * Updates the camera position to follow and center on a target entity.
     * <p>
     * The method calculates the camera's top-left corner so that the target
     * is approximately centered on the screen. It also ensures the camera does
     * not move outside the boundaries of the world, by clamping its position
     * within the valid range.
     * </p>
     * 
     * @param targetX the x-coordinate of the target to center on
     * @param targetY the y-coordinate of the target to center on
     */
    
     public void updateCamera(int targetX, int targetY) {
        // --- Calculate the position of the camera in order to center the target ---
        cameraX = targetX - currentScreenWidth / 2;
        cameraY = targetY - currentScreenHeight / 2;

        // --- Calculate the size of the world ---
        int worldWidth = levelGrid[0].length * tileWorldSize;
        int worldHeight = levelGrid.length * tileWorldSize;

        // --- Blocks left border of the world ---
        if (cameraX < 0) cameraX = 0;
        
        // --- Blocks upper border of the world ---
        if (cameraY < 0) cameraY = 0;
        
        if (worldWidth < currentScreenWidth) {
            cameraX = (worldWidth - currentScreenWidth) / 2;
        } else if (cameraX + currentScreenWidth > worldWidth) {
            // --- Blocks right border of the world ---
            cameraX = worldWidth - currentScreenWidth;
        }

        if (worldHeight < currentScreenHeight) {
            cameraY = (worldHeight - currentScreenHeight) / 2;
        } else if (cameraY + currentScreenHeight > worldHeight) {
            // --- Blocks down border of the world ---
            cameraY = worldHeight - currentScreenHeight;
        }
    }

    /**
     * Updates the viewport dimensions when the screen size changes.
     * <p>
     * This method was originally implemented to support a responsive design,
     * where tile and sprite sizes would adapt dynamically to different screen
     * resolutions. However, since sprite scaling (e.g., Sonic and entities) is
     * not currently used in the game, this method only updates the screen width
     * and height values without adjusting tile dimensions.
     * </p>
     *
     * @param screenWidth  the new screen width in pixels
     * @param screenHeight the new screen height in pixels
     */
    
    public void updateViewPort(int screenWidth, int screenHeight) {
        if (this.currentScreenWidth != screenWidth || this.currentScreenHeight != screenHeight) {
            this.currentScreenWidth = screenWidth;
            this.currentScreenHeight = screenHeight;
            /*updateTileDimensions();*/
        }
    }

    /*
     * Private helper method to recalculate tile dimensions for responsive scaling.
     * <p>
     * This method is kept for completeness but is not currently used, since
     * sprite scaling is not applied in the game.
     * </p>
    
     private void updateTileDimensions() {
        this.tileWidth = this.currentScreenWidth / TARGET_TILES_ON_SCREEN_WIDTH;
        this.tileHeight = this.currentScreenHeight / TARGET_TILES_ON_SCREEN_HEIGHT;
        
        this.tileWidth = Math.max(1, this.tileWidth);
        this.tileHeight = Math.max(1, this.tileHeight);
     }
    */
    
    /**
     * Renders the game world, including tiles, entities, background, and HUD.
     */
    
    @Override
    public void render(Graphics2D g2d, int width, int height) {
        Graphics2D g = (Graphics2D) g2d.create();
        drawBackground(g, width, height); 
       
        // --- Set the camera position ---
        g.translate(-cameraX, -cameraY);
        
        drawTiles(g); 
        drawGameEntities(g);
        drawHUB(g2d, width, height); 
        drawGoal(g);
        g.dispose();
    }
    
    /**
     * Draws the goal area on the screen.
     * <p>
     * This method searches for an entity with a GoalComponent and draws a red rectangle
     * at its position, representing the goal area.
     * </p>
     *
     * @param g the Graphics2D object used for drawing
     */
    
     private void drawGoal(Graphics2D g) {
        TransformComponent goalTransform = null;
        ArrayList<Entity> entityList = entityManager.getEntities();

        for (Entity e : entityList) {
            if (e.hasComponent(GoalComponent.class)) {
                goalTransform = ((EntityImpl) e).getComponent(TransformComponent.class);
            }
        }

        int x = (int) goalTransform.getX();
        int y = (int) goalTransform.getY();
        // --- Ground ---
        g.setColor(new Color(200, 180, 120)); 
        g.fillRect(x, y, 48, 48);
        // --- Pole ---
        g.setColor(new Color(180, 180, 180));
        g.fillRect(x + 18, y - 60, 4, 60);
        // --- Flag ---
        g.setColor(Color.BLUE);
        g.fillPolygon(new int[]{x + 22, x + 40, x + 22},new int[]{y - 60, y - 50, y - 40},3);
    }
    
    /** 
     * Draws the background of the game world.
     * <p>
     * This method creates a gradient sky background.
     * It fills the entire screen with a gradient from top to bottom, simulating a sky.
     * </p>
     * 
     * @param g the Graphics2D object used for drawing
     * @param width the width of the screen
     * @param height the height of the screen
     */
    
     private void drawBackground(Graphics2D g, int width, int height) {
        GradientPaint skyGradient = new GradientPaint(0, 0, SKY_COLOR_TOP,0, height * 0.7f, SKY_COLOR_BOTTOM);
        g.setPaint(skyGradient);
        g.fillRect(0, 0, width, height); 
        drawClouds(g, width, height);
    }
    
    /**
     * Draws clouds on the screen.
     * <p>
     * This method draws two sets of clouds at fixed positions on the screen.
     * The clouds are represented as white ovals to simulate a cloudy sky.
     * </p>
     *
     * @param g the Graphics2D object used for drawing
     * @param width the width of the screen
     * @param height the height of the screen
     */

    private void drawClouds(Graphics2D g, int width, int height) {
        g.setColor(Color.WHITE);
        
        // --- Cloud 1 ---
        g.fillOval(100, 50, 60, 40);
        g.fillOval(120, 40, 80, 50);
        g.fillOval(140, 50, 60, 40);
        
        // --- Cloud 2 ---
        g.fillOval(width - 200, 80, 70, 45);
        g.fillOval(width - 180, 70, 90, 55);
        g.fillOval(width - 160, 80, 70, 45);
    }
    
    /**
     * Draws the tiles of the game world.
     * <p>
     * This method iterates through the level grid and draws tiles based on their type.
     * Only tiles of type 1 are drawn, represented as light gray rectangles with dark gray borders.
     * </p>
     *
     * @param g2d the Graphics2D object used for drawing
     */

    private void drawTiles(Graphics2D g2d) {
        int startCol = Math.max(0, cameraX / tileWorldSize);
        int endCol = Math.min(levelGrid[0].length, (cameraX + currentScreenWidth) / tileWorldSize + 1);
        int startRow = Math.max(0, cameraY / tileWorldSize);
        int endRow = Math.min(levelGrid.length, (cameraY + currentScreenHeight) / tileWorldSize + 1);

        for (int r = startRow; r < endRow; r++) {
            for (int c = startCol; c < endCol; c++) {
                if (levelGrid[r][c] == TILE) { 
                    int x = c * tileWorldSize;
                    int y = r * tileWorldSize;
                    
                    Color baseColor = new Color(200, 180, 120);
                    Color borderColor = new Color(130, 100, 60); 

                    g2d.setColor(baseColor);
                    g2d.fillRect(x, y, tileWorldSize, tileWorldSize);

                    g2d.setColor(borderColor);
                    g2d.drawRect(x, y, tileWorldSize, tileWorldSize);
                }
            }
        }
    }

    /**
     * Draws all game entities on the screen.
     * <p>
     * This method iterates through all entities managed by the EntityManagerImpl,
     * checks for the presence of a GenericAnimator and TransformComponent, and draws
     * the current frame of the animator at the entity's position.
     * </p>
     *
     * @param g the Graphics2D object used for drawing
     */
    
    private void drawGameEntities(Graphics2D g) {
        List<Entity> entities = entityManager.getEntities();
        for (Entity e : entities) {

            if (e.hasComponent(GenericAnimator.class) && e.hasComponent(TransformComponent.class)) {
                GenericAnimator<?> animator = e.getComponent(GenericAnimator.class);
                TransformComponent transform = e.getComponent(TransformComponent.class);

            // --- Default to facing right if no PlayerPhysics component found ---
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
                    // --- Draw flipped horizontally ---
                    g.drawImage(frame, x + w, y, -w, h, null);
                } else {
                    // --- Normal draw ---
                    g.drawImage(frame, x, y, w, h, null);
                }
            });
            }
        }
    }

    /**
     * Draws HUD elements on the screen.
     * <p>
     * This method iterates through all entities and checks for HUDComponent.
     * If found, it calls the render method of the HUDComponent to draw it on the screen.
     * </p>
     *
     * @param g the Graphics2D object used for drawing
     * @param width the width of the screen
     * @param height the height of the screen
     */

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

    /**
     * Draws the hitboxes of all entities on the screen for debugging purposes.
     * <p>
     * This method visually outlines the bounding boxes of entities that have a
     * {@link TransformComponent}, helping developers verify collision areas and
     * entity positions during development and debugging.
     * </p>
     *
     * @param g the {@link Graphics2D} context used for drawing
     */
    
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
}   
   
