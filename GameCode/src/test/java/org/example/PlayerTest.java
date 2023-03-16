package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
    }

    @Test
    void testChangeScore() {
        player.setScore(0);
        player.changeScore(5);
        assertEquals(player.getScore(), 5);
        player.changeScore(0);
        assertEquals(player.getScore(), 5);
        //TODO test for exception missing if I pass negative int
    }
    @Test
    void testModifyCompletedCGoals(){
        boolean[] test;
         test = player.getCompletedCGoals();
         assertFalse(test[0]);
         assertFalse(test[1]);
         player.modifyCompletedCGoals(0);
         test = player.getCompletedCGoals();//makes no sense now as getter is returning a direct reference but in case
                                            //getter is changed to return a copy
         assertTrue(test[0]);
         assertFalse(test[1]);
         player.modifyCompletedCGoals(1);
         test = player.getCompletedCGoals();
         assertTrue(test[0]);
         assertTrue(test[1]);
         //TODO test exception in case position is not in {0,1}

    }
    @Test
    void testVerifyPGoalsPoints(){
        PersonalGoal PGoal;
        PGoal = new PersonalGoal_1;
        Tile tile;
        player.setMyPersonalGoal(PGoal);

        assertEquals(player.verifyPGoalPoints(), 0);

        tile = new Tile(Color.PINK, 0);
        player.getPlayerGrid().topUp(0,tile);
    //TODO finish test cases
    }



}