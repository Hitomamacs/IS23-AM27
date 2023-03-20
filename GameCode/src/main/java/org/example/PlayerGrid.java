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

   //* Method that returns a boolean that tells if the number of tiles is available in a column*/
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
        if (c.getY() < 0 || c.getY() > 4) {
            throw new IllegalArgumentException("Column must be between 0 and 5");
        }
        if (c.getX()< 0 || c.getX() > 5) {
            throw new IllegalArgumentException("Row must be between 0 and 6");
        }
        return grid[c.getX()][c.getY()];
    }

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
    public void quickGridSetter(Color[][] matrix){

        int i = 0;
        int j = 0;
        Tile tile;
        Color color;

        for(i = 0; i < 6; i++){
            for(j = 0; j < 5; j++){
                color = matrix[i][j];
                tile = new Tile(color, 0);
                grid[i][j].placeTile(tile);
            }
        }
        return;
    }
    public void printColorPlayerGrid(){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (grid[i][j].isOccupied()) {
                    if(grid[i][j].getTile().getColor() == Color.PINK)
                        System.out.print("P ");
                    if(grid[i][j].getTile().getColor() == Color.GREEN)
                        System.out.print("G ");
                    if(grid[i][j].getTile().getColor() == Color.YELLOW)
                        System.out.print("Y ");
                    if(grid[i][j].getTile().getColor() == Color.WHITE)
                        System.out.print("W ");
                    if(grid[i][j].getTile().getColor() == Color.AZURE)
                        System.out.print("A ");
                    if(grid[i][j].getTile().getColor() == Color.BLUE)
                        System.out.print("B ");
                } else {
                    System.out.print("O ");
                }
            }
            System.out.println();
        }
    }
}







