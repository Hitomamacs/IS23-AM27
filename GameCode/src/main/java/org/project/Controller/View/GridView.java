package org.project.Controller.View;

public class GridView {

    String[][] grid;
    String username;

    public GridView(String username){
        grid = new String[6][5];
        this.username = username;

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++){
                grid[i][j] = "N";
            }
        }
    }
    public void updateGridView(String tile, int column) {
        int i = 5;
        while(grid[i][column] != "N"){
            i--;
        }
        grid[i][column] = tile;
    }
    public String getUsername(){
        return username;
    }

    public String[][] getGridView(){
        return grid;
    }
}
