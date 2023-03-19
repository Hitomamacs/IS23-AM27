package org.example;

public class PlayerGrid {
    private Spot[][] grid;

    public PlayerGrid() {
        grid = new Spot[6][5];
        initializeGrid();
    }

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

    //* Method that fills a spot with a tile with a mechanis similar to a stack*/
    //add exception that checks if a column is full and throws eception
    public void topUp(int column, Tile tile) {
        if (column < 0 || column > 5) {
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

   //* Method that returns a boolean that tells if the number of tiles is available in a column*/
    public boolean spaceCheck(int column, int n_tiles) {
        if (column < 0 || column > 5) {
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

    //* Method that returns a boolean that tells if the grid is full*/
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

    public Spot getSpot(Coordinates c) {
        if (c.getY() < 0 || c.getY() > 5) {
            throw new IllegalArgumentException("Column must be between 0 and 5");
        }
        if (c.getX()< 0 || c.getX() > 6) {
            throw new IllegalArgumentException("Row must be between 0 and 6");
        }
        return grid[c.getY()][c.getX()];
    }

    private boolean columnFull(int column) {
        for (int i = 0; i < 6; i++) {
            if (!grid[i][column].isOccupied()) {
                return false;
            }
        }
        return true;


    }
    public void setTile(int column, int row){
        grid[row][column].setOccupied(false);
    }
}







