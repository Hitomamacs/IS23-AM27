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
        boolean[] CGoals;
        CGoals = player.getCompletedCGoals();
        assertFalse(CGoals[0]);
        assertFalse(CGoals[1]);
        player.modifyCompletedCGoals(0);
        CGoals = player.getCompletedCGoals();
        assertTrue(CGoals[0]);
        assertFalse(CGoals[1]);
        player.modifyCompletedCGoals(1);
        CGoals = player.getCompletedCGoals();
        assertTrue(CGoals[0]);
        assertTrue(CGoals[1]);
        player.modifyCompletedCGoals(0);
        player.modifyCompletedCGoals(1);
        CGoals = player.getCompletedCGoals();
        assertFalse(CGoals[0]);
        assertFalse(CGoals[1]);
        //TODO missing exception test if I pass position not in {1,2}
    }


}