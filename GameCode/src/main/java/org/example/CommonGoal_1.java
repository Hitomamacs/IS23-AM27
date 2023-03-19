package org.example;

public class CommonGoal_1 extends CommonGoal{
//  Goal is x2   |= =|  2 squares of four same color tiles
//               |= =|
//Idea is to iterate through playerGrid and for each spot if not already visited
// check spot to the right, below and diagonal below.If not a group of four equal just mark the single
//spot as visited otherwise mark all four as visited. Clearly if spot is empty go directly to next spot
    public boolean checkGoal(PlayerGrid playerGrid) {

        boolean[][] visited = new boolean[6][5];
        int count = 0; //Needed to see if there is at least 2 different squares of four
        Coordinates coordinates;

        for(int i = 0; i < 5; i++){ //stopping before the last row as last row will already have been checked
            for(int j = 0; j < 4; j++ ) { //by previous row control. Same goes for the column
                if(visited[i][j] = false) {
                    visited[i][j] = true;
                    coordinates = new Coordinates(i, j);
                    if (playerGrid.getSpot(coordinates).isOccupied()) {
                        if (checkGoalHelper(playerGrid, coordinates)) { //The helper verifies that the 3 neighbouring tiles
                            visited[i][j + 1] = true;                  //have the same colour (tile to the right,below and diagonal)
                            visited[i + 1][j] = true;
                            visited[i + 1][j + 1] = true;
                            count++;
                        }
                    }
                }
            }
        }
        if(count >= 2)
            return true;
        else
            return false;
    }
    //The helper essentially does to big ifs, checks if all the three neighbouring spots are occupied
    //and if that's the case checks all the tiles have the same color, if so returns true
    public boolean checkGoalHelper(PlayerGrid playerGrid, Coordinates coordinates){

        Color color;
        int x = coordinates.getX();
        int y = coordinates.getY();

        color = playerGrid.getSpot(coordinates).getTile().getColor();
        if(playerGrid.getSpot(new Coordinates(x,y+1)).isOccupied() &&
        playerGrid.getSpot(new Coordinates(x+1,y)).isOccupied() &&
        playerGrid.getSpot(new Coordinates(x+1,y+1)).isOccupied()){

            if(playerGrid.getSpot(new Coordinates(x,y+1)).getTile().getColor() == color
                    && playerGrid.getSpot(new Coordinates(x+1,y)).getTile().getColor() == color
                    && playerGrid.getSpot(new Coordinates(x+1,y+1)).getTile().getColor() == color)
                         return true;

        }
        return false;
    }
}
