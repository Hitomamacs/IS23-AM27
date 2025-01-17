package org.project.Model;

import com.google.gson.annotations.Expose;

/**
 * Class that represents the player's grid
 */

public class PlayerGrid {
    @Expose
    private Spot[][] grid;

    public PlayerGrid() {
        grid = new Spot[6][5];
        initializeGrid();
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    /**
     * Method that initializes the grid with empty spots
     */
    private void initializeGrid() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j] = new Spot(false, null);
            }
        }
    }

    /**
     * The method fills a spot with a tile with a mechanism similar to a stack with the exception that checks
     * if a column is full (it throws exception)
     * @param column selected by the player
     * @param tile that the player wants to put in his grid
     */
    public void topUp(int column, Tile tile) {
        if (column < 0 || column > 4) {
            throw new IllegalArgumentException("Column must be between 0 and 5");
        }
        if (columnFull(column)) {
            throw new IllegalArgumentException("Column is full");
        }
        for (int i = 5; i >= 0; i--) {
            if (!grid[i][column].isOccupied()) {
                grid[i][column].placeTile(tile);
                grid[i][column].setOccupied(true);
                return;
            }
        }
    }

    /**
     * @param column in which the player wants to put the selected tiles
     * @param n_tiles that the player wants to put in the selected column
     * @return a boolean that tells if the number of tiles is available in a column
     */
    public boolean spaceCheck(int column, int n_tiles) {
        if (column < 0 || column > 4) {
            throw new IllegalArgumentException("Column must be between 0 and 5");
        }
        int counter = 0;
        for (int i = 0; i <= 5; i++) {
            if (!grid[i][column].isOccupied()) {
                counter++;
            }
        }
        if (counter >= n_tiles) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return boolean that tells if the grid is full
     */
    public boolean fullCheck() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (!grid[i][j].isOccupied()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param c coordinates of the player grid
     * @return the spot selected with the coordinates
     */
    public Spot getSpot(Coordinates c) {
        if (c.getY() < 0 || c.getY() > 4) {
            throw new IllegalArgumentException("Column must be between 0 and 5");
        }
        if (c.getX()< 0 || c.getX() > 5) {
            throw new IllegalArgumentException("Row must be between 0 and 6");
        }
        return grid[c.getX()][c.getY()];
    }

    /**
     * @param column we want to check if it is full or not
     * @return true if the columns is full, false otherwise
     */
    private boolean columnFull(int column) {
        for (int i = 0; i < 6; i++) {
            if (!grid[i][column].isOccupied()) {
                return false;
            }
        }
        return true;
    }

    public Spot[][] getGrid(){
        return grid;
    }
    public void setTile(int column, int row){
        grid[row][column].setOccupied(false);
    }

    /**
     * The method prints the player grid
     */
    public void printPlayerGrid(){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (grid[i][j].isOccupied()) {
                    System.out.print("X");
                } else {
                    System.out.print("O");
                }
            }
            System.out.println();
        }
    }

    /**
     * The method sets the grid with the colors specified in the matrix
     * @param matrix matrix of Color
     */
    public void quickGridSetter(Color[][] matrix){

        int i = 0;
        int j = 0;
        Tile tile;
        Color color;

        for(i = 0; i < 6; i++){
            for(j = 0; j < 5; j++){
                color = matrix[i][j];
                if(color != null) {
                    tile = new Tile(color, 0);
                    grid[i][j].placeTile(tile);
                }
            }
        }
    }

    /**
     * The method prints the coloured player grid
     */
    public void printColorPlayerGrid(){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (grid[i][j].isOccupied()) {
                    if(grid[i][j].getTile().getColor() == Color.PINK)
                        System.out.print(ANSI_PURPLE+"P ");
                    if(grid[i][j].getTile().getColor() == Color.GREEN)
                        System.out.print(ANSI_GREEN+"G ");
                    if(grid[i][j].getTile().getColor() == Color.YELLOW)
                        System.out.print(ANSI_YELLOW+"Y ");
                    if(grid[i][j].getTile().getColor() == Color.WHITE)
                        System.out.print(ANSI_WHITE+"W ");
                    if(grid[i][j].getTile().getColor() == Color.AZURE)
                        System.out.print(ANSI_CYAN+"A ");
                    if(grid[i][j].getTile().getColor() == Color.BLUE)
                        System.out.print(ANSI_BLUE+"B ");
                } else {
                    System.out.print(ANSI_BLACK+"O ");
                }
            }
            System.out.println();
        }
    }
}







