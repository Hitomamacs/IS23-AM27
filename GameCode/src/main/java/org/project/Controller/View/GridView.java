package org.project.Controller.View;

/**
 * The class represents the grid view
 */

public class GridView {

    String[][] grid;
    String username;

    /**
     * Constructor
     * The matrix elements are initialized with the "N" String
     * @param username name of the player's grid
     */
    public GridView(String username){
        grid = new String[6][5];
        this.username = username;

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++){
                grid[i][j] = "N";
            }
        }
    }

    /**
     * The method updates the grid view with a new configuration represented by the newGrid,
     * it copies the values from the newGrid into the grid, element by element.
     * @param newGrid new configuration of the grid
     */
    public void updateGridView(String[][] newGrid) {
        for(int i = 0; i < 6; i++){
            System.arraycopy(newGrid[i], 0, grid[i], 0, 5);
        }
    }
    public String getUsername(){
        return username;
    }

    public String[][] getGridView(){
        return grid;
    }
}
