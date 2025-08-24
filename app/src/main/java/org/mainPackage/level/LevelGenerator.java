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
    // --- Initialize the grid with empty cells ---
    for (int r = 0; r < numRows; r++) {
        for (int c = 0; c < numCols; c++) {
            levelGrid[r][c] = EMPTY;
        }
    }

    // --- Ground line (bottom row filled with tiles) ---
    for (int c = 0; c < numCols; c++) {
        levelGrid[numRows - 1][c] = TILE;
    }

    // --- Borders (left and right columns) ---
    for (int r = 0; r < numRows; r++) {
        levelGrid[r][0] = TILE;
        levelGrid[r][numCols - 1] = TILE;
    }

    // --- Sonic spawn point (above the ground, near the left) ---
    levelGrid[numRows - 2][1] = SONIC;

    // --- Simple low floating platforms ---
    int platformWidth = 8; // each platform is 4 tiles wide
    for (int c = 6; c < numCols - platformWidth - 1; c += 15) {
        int height = numRows - 3; // just one cell above Sonic's head
        for (int w = 0; w < platformWidth; w++) {
            levelGrid[height][c + w] = TILE;
        }
    }

    // --- Place static enemies on the ground ---
    for (int c = 12; c < numCols - 10; c += 20) {
        levelGrid[numRows - 2][c] = ENEMY_STATIC;
    }

    // --- Place chasing enemies on the ground further away ---
    for (int c = 25; c < numCols - 25; c += 40) {
        levelGrid[numRows - 2][c] = ENEMY_CHASING;
    }

    // --- Place rings above ground ---
    /*for (int c = 8; c < numCols - 8; c += 10) {
        levelGrid[numRows - 3][c] = RING;
    }*/

    // --- Goal at the end of the level ---
    levelGrid[numRows - 2][numCols - 3] = GOAL;
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