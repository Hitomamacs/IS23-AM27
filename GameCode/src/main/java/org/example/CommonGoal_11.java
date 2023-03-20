package org.example;

public class CommonGoal_11 extends CommonGoal{
    public boolean checkGoal(PlayerGrid playerGrid) {
        //counters for the matrix
        int i; //row
        int j; //column
        int counterDiagonal_1 = 0, counterDiagonal_2 = 0, counterDiagonal_3=0, counterDiagonal_4=0;

        for (i = 1; i < 5; i++) {
            if (playerGrid.getSpot(new Coordinates(i - 1, i - 1)).isOccupied() &&
                    playerGrid.getSpot(new Coordinates(i, i)).isOccupied() &&
                    playerGrid.getSpot(new Coordinates(i - 1, i - 1)).getTile().getColor() ==
                            playerGrid.getSpot(new Coordinates(i, i)).getTile().getColor()) {
                counterDiagonal_1++;
                if (counterDiagonal_1 == 5) {
                    return true;
                }
            }
        }

        for (i = 2, j = 1; i < 5 && j < 6; i++, j++) {
            if (playerGrid.getSpot(new Coordinates(i - 1, j - 1)).isOccupied() &&
                    playerGrid.getSpot(new Coordinates(i, j)).isOccupied() &&
                    playerGrid.getSpot(new Coordinates(i - 1, j - 1)).getTile().getColor() ==
                            playerGrid.getSpot(new Coordinates(i, j)).getTile().getColor()) {
                counterDiagonal_2++;
                if (counterDiagonal_2 == 5) {
                    return true;
                }
            }
        }

        for (i = 4, j=0; i >0 && j<5; i--, j++) {
            if (playerGrid.getSpot(new Coordinates(i, j)).isOccupied() &&
                    playerGrid.getSpot(new Coordinates(i - 1, j + 1)).isOccupied() &&
                    playerGrid.getSpot(new Coordinates(i - 1, j + 1)).getTile().getColor() ==
                            playerGrid.getSpot(new Coordinates(i, j)).getTile().getColor()) {
                counterDiagonal_3++;
                if (counterDiagonal_3 == 5) {
                    return true;
                }
            }
        }

        for (i = 4, j=1; i >0 && j<6; i--, j++) {
            if (playerGrid.getSpot(new Coordinates(i, j)).isOccupied() &&
                    playerGrid.getSpot(new Coordinates(i - 1, j + 1)).isOccupied() &&
                    playerGrid.getSpot(new Coordinates(i - 1, j + 1)).getTile().getColor() ==
                            playerGrid.getSpot(new Coordinates(i, j)).getTile().getColor()) {
                counterDiagonal_4++;
                if (counterDiagonal_4 == 5) {
                    return true;
                }
            }
        }

        return false;
    }
}
