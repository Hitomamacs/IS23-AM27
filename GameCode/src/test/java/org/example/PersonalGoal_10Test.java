package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonalGoal_10Test {

    PersonalGoal_10 goal = new PersonalGoal_10();
    Spot[][] spots = goal.getSpotGrid();

    @BeforeEach
    void setUp(){
        goal = new PersonalGoal_10();
    }

    //Test 1: check that the tiles are in the right position
    @Test
    void checkCorrectPosition(){
        assert spots[3][3].getTile().getColor().equals(Color.GREEN);
        assert spots[5][3].getTile().getColor().equals(Color.PINK);
        assert spots[1][1].getTile().getColor().equals(Color.YELLOW);
        assert spots[0][4].getTile().getColor().equals(Color.AZURE);
        assert spots[2][0].getTile().getColor().equals(Color.WHITE);
        assert spots[4][1].getTile().getColor().equals(Color.BLUE);
    }

    //Test 2: check that other spots are empty
    @Test
    void emptyCheckPosition(){
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                if((i==3 && j==3) || (i==5 && j==3) || (i==1 && j==1) || (i==0 && j==4) || (i==2 && j==0)
                        || (i==4 && j==1)){
                    continue;
                }
                assert !spots[i][j].isOccupied();
            }
        }
    }

    //Test 3: check that the filled Spots have the attribute occupied=true
    @Test
    void fullCheckPosition(){
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                if((i==3 && j==3) || (i==5 && j==3) || (i==1 && j==1) || (i==0 && j==4) || (i==2 && j==0)
                        || (i==4 && j==1)){
                    assert spots[i][j].isOccupied();
                }
            }
        }
    }

    //Test 4: check that spots matrix is created correctly
    @Test
    void checkCorrectMatrix(){
        assertEquals(6, spots.length);
        assertEquals(5, spots[0].length);
    }
}