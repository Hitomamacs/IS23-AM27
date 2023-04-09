package org.example;

public class CommonGoal_9 extends CommonGoal{

    /** method that checks the CommonGoal_9 where
     *  eight tiles of the same color must be in the playerGrid
     *  I have a number of counters equal to the number of colors that a Tile can have,
     *  I iterate on the playerGrid and when I find an occupied Spot I check the color of the
     *  Tile and increase the counter of that color by one.
     *  As soon as a counter reaches the value of eight, the goal is verified
     */
    public boolean checkGoal(PlayerGrid playerGrid) {
        //counters for the matrix
        int i = 0;
        int j = 0;
        int counterPink = 0, counterYellow = 0, counterGreen = 0, counterWhite = 0, counterBlue = 0, counterAzure = 0;

        for (i = 0; i < 6; i++) { //iteration row
            for (j = 0; j < 5; j++) { //iteration column
                if (playerGrid.getSpot(new Coordinates(i, j)).isOccupied() &&
                        playerGrid.getSpot(new Coordinates(i, j)).getTile().getColor() == Color.PINK) {
                    counterPink++;
                    if (counterPink == 8) {
                        return true;
                    }
                }
                if (playerGrid.getSpot(new Coordinates(i, j)).isOccupied() &&
                        playerGrid.getSpot(new Coordinates(i, j)).getTile().getColor() == Color.YELLOW) {
                    counterYellow++;
                    if (counterYellow == 8) {
                        return true;
                    }
                }
                if (playerGrid.getSpot(new Coordinates(i, j)).isOccupied() &&
                        playerGrid.getSpot(new Coordinates(i, j)).getTile().getColor() == Color.GREEN) {
                    counterGreen++;
                    if (counterGreen == 8) {
                        return true;
                    }
                }
                if (playerGrid.getSpot(new Coordinates(i, j)).isOccupied() &&
                        playerGrid.getSpot(new Coordinates(i, j)).getTile().getColor() == Color.WHITE) {
                    counterWhite++;
                    if (counterWhite == 8) {
                        return true;
                    }
                }
                if (playerGrid.getSpot(new Coordinates(i, j)).isOccupied() &&
                        playerGrid.getSpot(new Coordinates(i, j)).getTile().getColor() == Color.BLUE) {
                    counterBlue++;
                    if (counterBlue == 8) {
                        return true;
                    }
                }
                if (playerGrid.getSpot(new Coordinates(i, j)).isOccupied() &&
                        playerGrid.getSpot(new Coordinates(i, j)).getTile().getColor() == Color.AZURE) {
                    counterAzure++;
                    if (counterAzure == 8) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
