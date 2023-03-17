package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PersonalGoal_10Test {

    PersonalGoal_10 personalGoal_10;
    private final Coordinates a = new Coordinates(5,3);
    private final Coordinates b = new Coordinates(4,1);
    private final Coordinates c = new Coordinates(3,3);
    private final Coordinates d = new Coordinates(2,0);
    private final Coordinates e = new Coordinates(1,1);
    private final Coordinates f = new Coordinates(0,4);


    @BeforeEach
    void setUp(){
        personalGoal_10 = new PersonalGoal_10();

    }

    @Test
    public void testInitialize() {

        personalGoal_10.initialize();
        HashMap<Coordinates, Color> coloredGoal = personalGoal_10.getColoredGoal();
        assertEquals(6, coloredGoal.size());
        assertTrue(coloredGoal.containsKey(a));
        assertTrue(coloredGoal.containsKey(b));
        assertTrue(coloredGoal.containsKey(c));
        assertTrue(coloredGoal.containsKey(d));
        assertTrue(coloredGoal.containsKey(e));
        assertTrue(coloredGoal.containsKey(f));
        assertEquals(Color.PINK, coloredGoal.get(a));
        assertEquals(Color.BLUE, coloredGoal.get(b));
        assertEquals(Color.GREEN, coloredGoal.get(c));
        assertEquals(Color.WHITE, coloredGoal.get(d));
        assertEquals(Color.YELLOW, coloredGoal.get(e));
        assertEquals(Color.AZURE, coloredGoal.get(f));
    }
}