package org.example;

public class CommonGoal_9 extends CommonGoal{

    public boolean checkGoal(PlayerGrid playerGrid) {
        //counters for the matrix
        int i = 0;
        int j = 0;
        int counterPink = 0, counterYellow = 0, counterGreen = 0, counterWhite = 0, counterBlue = 0, counterAzure = 0;

        for (i = 0; i < 5; i++) {
            for (j = 0; j < 6; j++) {
                if ((playerGrid.getSpot(new Coordinates(i, j)).getTile().getColor() == Color.PINK) &&
                        playerGrid.getSpot(new Coordinates(i, j)).isOccupied()) {
                    counterPink++;
                    if (counterPink == 8) {
                        return true;
                    }
                }
                if (playerGrid.getSpot(new Coordinates(i, j)).getTile().getColor() == Color.YELLOW &&
                        playerGrid.getSpot(new Coordinates(i, j)).isOccupied()) {
                    counterYellow++;
                    if (counterYellow == 8) {
                        return true;
                    }
                }
                if (playerGrid.getSpot(new Coordinates(i, j)).getTile().getColor() == Color.GREEN &&
                        playerGrid.getSpot(new Coordinates(i, j)).isOccupied()) {
                    counterGreen++;
                    if (counterGreen == 8) {
                        return true;
                    }
                }
                if (playerGrid.getSpot(new Coordinates(i, j)).getTile().getColor() == Color.WHITE &&
                        playerGrid.getSpot(new Coordinates(i, j)).isOccupied()) {
                    counterWhite++;
                    if (counterWhite == 8) {
                        return true;
                    }
                }
                if (playerGrid.getSpot(new Coordinates(i, j)).getTile().getColor() == Color.BLUE &&
                        playerGrid.getSpot(new Coordinates(i, j)).isOccupied()) {
                    counterBlue++;
                    if (counterBlue == 8) {
                        return true;
                    }
                }
                if (playerGrid.getSpot(new Coordinates(i, j)).getTile().getColor() == Color.AZURE &&
                        playerGrid.getSpot(new Coordinates(i, j)).isOccupied()) {
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
