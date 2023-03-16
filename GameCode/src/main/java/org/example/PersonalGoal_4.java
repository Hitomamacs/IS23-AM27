package org.example;

public class PersonalGoal_4 extends PersonalGoal{

    private final Spot[][] spotGrid;

    public PersonalGoal_4() {
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

        spotGrid[4][2].placeTile(new Tile(Color.GREEN, 0));
        spotGrid[4][2].setOccupied(true);
        spotGrid[3][3].placeTile(new Tile(Color.PINK, 0));
        spotGrid[3][3].setOccupied(true);
        spotGrid[0][4].placeTile(new Tile(Color.YELLOW,0));
        spotGrid[0][4].setOccupied(true);
        spotGrid[2][0].placeTile(new Tile(Color.AZURE,0));
        spotGrid[2][0].setOccupied(true);
        spotGrid[4][1].placeTile(new Tile(Color.WHITE,0));
        spotGrid[4][1].setOccupied(true);
        spotGrid[2][2].placeTile(new Tile(Color.BLUE, 0));
        spotGrid[2][2].setOccupied(true);
    }

    public Spot[][] getSpotGrid() {
        return spotGrid;
    }
}
