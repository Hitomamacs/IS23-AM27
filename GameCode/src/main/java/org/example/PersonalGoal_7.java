package org.example;

public class PersonalGoal_7 extends PersonalGoal{
    private final Spot[][] spotGrid;

    public PersonalGoal_7() {
        spotGrid = new Spot[6][5];
        initializeGoalGrid();
    }

    //grid initialization with all spots not occupied
    public void initializeGoalGrid() {
        //Spot matrix initialization
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                spotGrid[i][j] = new Spot(false, null);
            }
        }

        spotGrid[0][0].placeTile(new Tile(Color.GREEN, 0));
        spotGrid[0][0].setOccupied(true);
        spotGrid[1][3].placeTile(new Tile(Color.BLUE, 0));
        spotGrid[1][3].setOccupied(true);
        spotGrid[4][4].placeTile(new Tile(Color.YELLOW,0));
        spotGrid[4][4].setOccupied(true);
        spotGrid[3][0].placeTile(new Tile(Color.AZURE,0));
        spotGrid[3][0].setOccupied(true);
        spotGrid[5][2].placeTile(new Tile(Color.WHITE,0));
        spotGrid[5][2].setOccupied(true);
        spotGrid[2][1].placeTile(new Tile(Color.PINK, 0));
        spotGrid[2][1].setOccupied(true);
    }

    public Spot[][] getSpotGrid() {
        return spotGrid;
    }
}
