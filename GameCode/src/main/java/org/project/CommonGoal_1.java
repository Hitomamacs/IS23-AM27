package org.project;



public class CommonGoal_1 extends CommonGoal{

    private static int goal_ID = 1;
//  Goal is x2   |= =|  2 distinct squares (separate from each other) of four same color tiles
//               |= =|
//Idea is to iterate through playerGrid and for each spot if not already visited
// check spot to the right, below and diagonal below.If not a group of four equal just mark the single
//spot as visited otherwise mark all four as visited. Clearly if spot is empty go directly to next spot
//The two groups of four have to be separated as well, so I'll need a function that marks all paths of same
//colored tiles that starts from a valid group of four as visited. This will avoid considering two connected
//groups of four same colored tiles as two distinct groups
    public boolean checkGoal(PlayerGrid playerGrid) {

        boolean[][] visited = new boolean[6][5];
        int count = 0; //Needed to see if there is at least 2 different squares of four
        Coordinates coordinates;
        Color currentColor;

        for(int i = 0; i < 5; i++){ //stopping before the last row as last row will already have been checked
            for(int j = 0; j < 4; j++ ) { //by previous row control. Same goes for the column
                if(visited[i][j] == false) {
                    visited[i][j] = true;
                    coordinates = new Coordinates(i, j);
                    if (playerGrid.getSpot(coordinates).isOccupied()) {
                        if (checkGoalHelper(playerGrid, coordinates)) { //The helper verifies that the 3 neighbouring tiles
                            visited[i][j + 1] = true;                  //have the same colour (tile to the right,below and diagonal)
                            visited[i + 1][j] = true;
                            visited[i + 1][j + 1] = true;
                            count++;
                            currentColor = playerGrid.getSpot(coordinates).getTile().getColor();
                            pathMarker(currentColor, visited, new boolean[6][5], playerGrid, coordinates);
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
    //Function that marks all paths of same colored tiles starting from the passed coordinate as visited
    private void pathMarker(Color color, boolean[][] visited, boolean[][] pathVisited, PlayerGrid playerGrid, Coordinates coordinate){
        int x = coordinate.getX();
        int y = coordinate.getY();

        if(playerGrid.getSpot(coordinate).isOccupied()){ //If not occupied don't have to do anything, need to separate with if to avoid exception in next line
            if(playerGrid.getSpot(coordinate).getTile().getColor() == color && !pathVisited[x][y]){//Need to distinguish tiles the pathChecker has already checked otherwise infinite loop
                visited[x][y] = true; //So that the checkGoal won't check these tiles
                pathVisited[x][y] = true;

                //Next four ifs make sure the adjacent coordinates are valid and not out of bounds and calls pathMarker again in each direction
                if(isValidCoordinates(new Coordinates(x + 1, y)))
                    pathMarker(color, visited, pathVisited, playerGrid, new Coordinates(x + 1, y));

                if(isValidCoordinates(new Coordinates(x , y + 1)))
                    pathMarker(color, visited, pathVisited, playerGrid, new Coordinates(x, y + 1));

                if(isValidCoordinates(new Coordinates(x - 1, y)))
                    pathMarker(color, visited, pathVisited, playerGrid, new Coordinates(x - 1, y));

                if(isValidCoordinates(new Coordinates(x, y - 1)))
                    pathMarker(color, visited, pathVisited, playerGrid, new Coordinates(x, y - 1));
            }
        }
    }
    //Simple method to check coordinates won't cause out of bound exception
    private boolean isValidCoordinates(Coordinates coordinate){
        int x = coordinate.getX();
        int y = coordinate.getY();

        if(x < 0 || x >= 6)
            return false;
        if(y < 0 || y >= 5)
            return false;
        return true;
    }
}
