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
    
    // --- Configuration constants ---
    final int GROUND_HEIGHT = (numRows - 1);
    final int SECOND_HEIGHT = (numRows - 2);
    final int THIRD_HEIGHT = (numRows - 3);
    final int FOURTH_HEIGHT = (numRows - 4);
    final int FIFTH_HEIGHT = (numRows - 5);
    final int SONIC_START = 1;

    // --- Initialize grid with empty cells ---
    for (int r = 0; r < numRows; r++) {
        for (int c = 0; c < numCols; c++) {
            levelGrid[r][c] = EMPTY;
        }
    }
    
    // --- Borders ---
    for (int r = 0; r < numRows; r++) {
        levelGrid[r][0] = TILE;
        levelGrid[r][numCols - 1] = TILE;
    }

    // --- Singolar tiles and enemies for variety ---
    levelGrid[0][27] = ENEMY_STATIC;
    levelGrid[3][64] = ENEMY_STATIC;
    levelGrid[0][78] = ENEMY_STATIC;
    levelGrid[3][72] = ENEMY_CHASING;
    levelGrid[3][82] = ENEMY_CHASING;
    levelGrid[3][170] = TILE;
    levelGrid[0][220] = ENEMY_STATIC;

    createGroundHoles(levelGrid, GROUND_HEIGHT, numCols);
    
    createPlatform(levelGrid, THIRD_HEIGHT, 10, 8, numCols);  
    createPlatform(levelGrid, FOURTH_HEIGHT, 24, 5, numCols);  
    createPlatform(levelGrid, THIRD_HEIGHT, 50, 6, numCols);  
    createPlatform(levelGrid, FIFTH_HEIGHT, 58, 10, numCols);
    createPlatform(levelGrid, FOURTH_HEIGHT, 75, 5, numCols);  
    
    createPlatform(levelGrid, THIRD_HEIGHT, 143, 12, numCols);
    createPlatform(levelGrid, FOURTH_HEIGHT, 157, 12, numCols);
    createPlatform(levelGrid, THIRD_HEIGHT, 171, 20, numCols);

    placeRings(levelGrid, 10, 30, FIFTH_HEIGHT, 5);
    placeRings(levelGrid, 40, 55, SECOND_HEIGHT, 5);
    placeRings(levelGrid, 75, 100, SECOND_HEIGHT,5);
    placeRings(levelGrid, 116, 134, SECOND_HEIGHT, 3);
    placeRings(levelGrid, 144, 152,FOURTH_HEIGHT, 4);
    placeRings(levelGrid, 161, 167, FIFTH_HEIGHT, 3);
    placeRings(levelGrid, 195, 204, SECOND_HEIGHT, 3);
    placeRings(levelGrid, 222, 242, SECOND_HEIGHT, 5);

    placeChasingEnemies(levelGrid, 150, 210,FOURTH_HEIGHT, 30);
    placeChasingEnemies(levelGrid, 223, 243, SECOND_HEIGHT, 10);
    
    placeStaticEnemies(levelGrid, 12, 15,FOURTH_HEIGHT, 3);
    placeStaticEnemies(levelGrid, 21, 32, SECOND_HEIGHT, 11);
    placeStaticEnemies(levelGrid, 39, 53, SECOND_HEIGHT, 7);
    placeStaticEnemies(levelGrid, 115, 133, SECOND_HEIGHT, 3);
    placeStaticEnemies(levelGrid, 178, 187, FIFTH_HEIGHT, 9);
    placeStaticEnemies(levelGrid, 160, 166, FIFTH_HEIGHT, 3);
    placeStaticEnemies(levelGrid, 196, 208, SECOND_HEIGHT, 6);
    placeStaticEnemies(levelGrid, 220, 245, SECOND_HEIGHT, 5);
    
    // --- Sonic spawn point ---
    levelGrid[GROUND_HEIGHT - 1][SONIC_START] = SONIC;
  

    // --- Goal at the end ---
    levelGrid[GROUND_HEIGHT - 1][numCols - 2] = GOAL;
}

/**
 * Creates holes in the ground of the level.
 *
 * <p>The method modifies the ground row in the level grid by creating empty spaces
 * (holes) at specified distances and widths.</p>
 *
 * @param levelGrid The 2D level array representing the game grid.
 * @param groundRow The index of the ground row where holes should be placed.
 * @param numCols   The total number of columns in the level.
 */

 private void createGroundHoles(int[][] levelGrid, int groundRow, int numCols) {
    
    /** Define holes as {distance from previous hole, hole width} */ 
        int[][] holes = {
        {35, 3},
        {20, 3},
        {35, 2},
        {10, 5},
        {22, 4},
        {24, 3},
        {5, 21},
        {20, 4}
        };

    int currentX = 0;

    for (int i = 0; i < holes.length; i++) {
        int distance = holes[i][0];
        int width = holes[i][1];

        // --- Fill ground until the next hole ---
        for (int c = currentX; c < currentX + distance && c < numCols; c++) {
            levelGrid[groundRow][c] = TILE;
        }

        // --- Skip cells to create the hole ---
        currentX += distance;
        for (int c = currentX; c < currentX + width && c < numCols; c++) {
            levelGrid[groundRow][c] = EMPTY;
        }

        // --- Move the cursor past the hole ---
        currentX += width;
    }
    // --- Fill remaining ground after the last hole ---
        for (int c = currentX; c < numCols; c++) {
            levelGrid[groundRow][c] = TILE;
        }
    }

    /**
     * Places a horizontal platform at a specific height in the level.
     *
     * <p>The method fills a horizontal line of tiles starting from a given column
     * and a given width. It ensures the platform does not exceed the level bounds
     * and does not overwrite tiles above existing tiles of a lower level.</p>
     *
     * @param levelGrid The 2D level array representing the game grid.
     * @param row       The row index where the platform will be placed.
     * @param colStart  The starting column for the platform.
     * @param width     The width of the platform in tiles.
     * @param numCols   Total number of columns in the level.
     */
    
     private void createPlatform(int[][] levelGrid, int row, int colStart, int width, int numCols) {
        for (int c = colStart; c < colStart + width && c < numCols; c++) {
            if (levelGrid[row][c] == EMPTY) {
                levelGrid[row][c] = TILE;
            }
        }
    }

    /**
     * Places rings on the level grid.
     *
     * <p>The rings can be placed on empty tiles either on the ground or on platforms.
     * The method ensures that rings are not placed on top of other objects (tiles, enemies, other rings).</p>
     *
     * @param levelGrid The 2D level array representing the game grid.
     * @param startCol  The starting column for the first ring.
     * @param endCol    The last column where rings can appear.
     * @param row       Height.
     * @param space  Minimum spacing between rings.
     */
     
     private void placeRings(int[][] levelGrid, int startCol, int endCol, int row, int space) {
        for (int c = startCol; c <= endCol; c += space) {
            if (levelGrid[row][c] == EMPTY) {
            levelGrid[row][c] = RING;
            }
        }
    }

    /**
    * Places chasing enemies on the level grid.
    *
    * <p>These enemies behave differently than static enemies and can also
    * be placed on platforms. Ensures no overlap with tiles, rings, or other enemies.</p>
    *
    * @param levelGrid The 2D level array representing the game grid.
    * @param startCol  The first column for placement.
    * @param endCol    The last column for placement.
    * @param row       Height.
    * @param space   Minimum spacing between chasing enemies.
    */

    private void placeChasingEnemies(int[][] levelGrid, int startCol, int endCol, int row, int space) {
        for (int c = startCol; c <= endCol; c += space) {
            if (levelGrid[row][c] == EMPTY) {
                levelGrid[row][c] = ENEMY_CHASING;
            }
        }
    }

    /**
     * Places static enemies on the level grid.
     *
     * <p>Enemies can be placed on the ground or on top of platforms.
     * The method checks that the target cell is empty before placing the enemy.
     * </p>
     *
     * @param levelGrid The 2D level array representing the game grid.
     * @param startCol  The first column to consider for placement.
     * @param endCol    The last column to consider for placement.
     * @param row       Height.
     * @param space   Minimum spacing between enemies.
     */
        
     private void placeStaticEnemies(int[][] levelGrid, int startCol, int endCol, int row, int space) {
        for (int c = startCol; c <= endCol; c += space) {
            if (levelGrid[row][c] == EMPTY) {
                levelGrid[row][c] = ENEMY_STATIC;
            }
        }
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