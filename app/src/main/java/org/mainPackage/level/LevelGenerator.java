package org.mainPackage.level;

/**
 * Generates a level grid based on predefined rules.
 * 
 * Each cell in the grid contains an integer that represents a specific game entity:
 * <ul>
 *   <li>0 → Empty space</li>
 *   <li>1 → Tile (ground, border)</li>
 *   <li>2 → Static enemy</li>
 *   <li>3 → Chasing enemy</li>
 *   <li>4 → Sonic (spawn point)</li>
 *   <li>5 → Ring</li>
 *   <li>6 → Goal</li>
 * </ul>
 * 
 * This implementation is simple and uses hardcoded rules to place elements.
 */

public class LevelGenerator {

    public static final int EMPTY = 0;
    public static final int TILE = 1;
    public static final int ENEMY_STATIC = 2;
    public static final int ENEMY_CHASING = 3;
    public static final int SONIC = 4;
    public static final int RING = 5;
    public static final int GOAL = 6;

    private final int numRows;
    private final int numCols;
    private int[][] levelGrid;

    /**
     * Constructs a LevelGenerator with specified dimensions.
     * 
     * @param numRows Number of rows in the level grid.
     * @param numCols Number of columns in the level grid.
     */

    public LevelGenerator(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.levelGrid = new int[numRows][numCols];
        generateLevel();
    }

    /**
     * Generates the level grid based on predefined rules.
     * 
     * This method fills the grid with tiles, enemies, rings, and Sonic's spawn point.
     * It also places the goal at a specific position.
     */

    private void generateLevel() {
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                levelGrid[r][c] = EMPTY;
            }
        }
        
        // --- Fill the grid with tiles and borders ---
        for (int c = 0; c < numCols; c++) {
            levelGrid[4][c] = TILE; 
        }
        
        // --- Set the first and last row as borders ---
        for (int r = 0; r < numRows; r++) {
            levelGrid[r][0] = TILE;
            levelGrid[r][numCols - 1] = TILE;
        }

        // --- Set positions for Sonic ---
       levelGrid[3][1] = SONIC;

        // --- Place static enemies and rings ---
        for (int c = 10; c < numCols - 10; c += 10) {
            levelGrid[2][c] = (c % 20 == 0) ? ENEMY_STATIC : RING;
        }
        
        //levelGrid[2][4] = ENEMY_STATIC;

        // --- Place chasing enemies ---
        for (int c = 60; c <= numCols - 60; c += 40) {
            levelGrid[numRows - 3][c] = ENEMY_CHASING;
        }

        for (int c = 30; c < numCols - 10; c += 60) {
            levelGrid[numRows - 1][c] = EMPTY;           
            levelGrid[numRows - 1][c + 1] = EMPTY;         
        }

        // --- Place Goal ---
        levelGrid[3][numCols - 3] = GOAL;
    }

    /**
     * Returns the generated level grid.
     * 
     * @return A 2D array representing the level grid.
     */
    
    public int[][] getLevelGrid() {
        return levelGrid;
    }

}