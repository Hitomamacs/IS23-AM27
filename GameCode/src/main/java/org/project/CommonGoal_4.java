package org.project;


public class CommonGoal_4 extends CommonGoal{

    private final int goal_ID = 4;
    public Integer getGoalID() {
        return goal_ID;
    }
    
    public boolean checkGoal(PlayerGrid playerGrid) {


        int i, j;

        //I need count to count the number of tiles per group
        //I need counter to count the number of groups of 4
        int count = 0, counter = 0;
        Color color;

        boolean [][] visited = new boolean[6][5];
        for(i = 0; i < 6; i++){
            for(j = 0; j < 5; j++){
                visited[i][j] = false;
            }
        }

        for(i = 0; i < 6; i++){
            for(j = 0; j < 5; j++){
                if(playerGrid.getSpot(new Coordinates(i, j)).isOccupied() && !visited[i][j]){
                    color = playerGrid.getSpot(new Coordinates(i, j)).getTile().getColor();
                    count = verifyExtraPointHelper(i, j, color, visited, playerGrid);
                    if(count > 2){
                        counter++;
                        if(counter>=4){
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }


    public int verifyExtraPointHelper(int i, int j, Color color, boolean[][] visited, PlayerGrid playerGrid){
        int count = 1;
        visited[i][j] = true;

        if(isValidPos(i + 1, j, playerGrid.getGrid().length, playerGrid.getGrid()[0].length) &&
                playerGrid.getSpot(new Coordinates(i+1, j)).isOccupied() &&
                playerGrid.getSpot(new Coordinates(i+1, j)).getTile().getColor() == color && !visited[i + 1][j]){
            count = count + verifyExtraPointHelper(i + 1, j, color, visited, playerGrid);
        }
        if(isValidPos(i - 1,j,playerGrid.getGrid().length,playerGrid.getGrid()[0].length) &&
                playerGrid.getSpot(new Coordinates(i-1, j)).isOccupied() &&
                playerGrid.getSpot(new Coordinates(i-1, j)).getTile().getColor() == color && !visited[i - 1][j]){
            count = count + verifyExtraPointHelper(i - 1, j, color, visited, playerGrid);
        }
        if(isValidPos(i,j + 1,playerGrid.getGrid().length,playerGrid.getGrid()[0].length) &&
                playerGrid.getSpot(new Coordinates(i,j+1)).isOccupied() &&
                playerGrid.getSpot(new Coordinates(i,j+1)).getTile().getColor() == color && !visited[i][j + 1]){
            count = count + verifyExtraPointHelper(i,j + 1, color, visited, playerGrid);
        }
        if(isValidPos(i,j - 1,playerGrid.getGrid().length,playerGrid.getGrid()[0].length) &&
                playerGrid.getSpot(new Coordinates(i,j-1)).isOccupied() &&
                playerGrid.getSpot(new Coordinates(i,j-1)).getTile().getColor() == color && !visited[i][j - 1]){
            count = count + verifyExtraPointHelper(i,j - 1, color, visited, playerGrid);
        }
        return count;
    }
    static boolean isValidPos(int i, int j, int n, int m)
    {
        if (i < 0 || j < 0 || i > n - 1 || j > m - 1) {
            return false;
        }
        return true;
    }
}
