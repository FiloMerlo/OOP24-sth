package org.mainPackage.level;


public class LevelGenerator {

    private final int numRows;
    private final int numCols;
    private int[][] levelGrid;

    
    public LevelGenerator(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.levelGrid = new int[numRows][numCols];
        generateLevel();
    }

    private void generateLevel() {
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                levelGrid[r][c] = 0;
            }
        }

        for (int c = 0; c < numCols; c++) {
            levelGrid[4][c] = 1;
        }

        for (int r = 0; r < numRows; r++) {
            levelGrid[r][0] = 1;
            levelGrid[r][numCols - 1] = 1;
        }

       levelGrid[3][1] = 4;

        
        for (int c = 10; c < numCols - 10; c += 10) {
            levelGrid[2][c] = (c % 20 == 0) ? 2 : 5;
        }
        levelGrid[2][4] = 2; // Nemico statico per migliorare il debug

        for (int c = 60; c <= numCols - 60; c += 40) {
            levelGrid[numRows - 3][c] = 3;
        }
        levelGrid[2][8]= 3;
       
        for (int c = 30; c < numCols - 10; c += 60) {
            levelGrid[numRows - 1][c] = 0;           
            levelGrid[numRows - 1][c + 1] = 0;         
        }

       
        levelGrid[3][numCols - 3] = 6;
    }


    public int[][] getLevelGrid() {
        return levelGrid;
    }

}