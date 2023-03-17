package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PersonalGoal_7Test {

    PersonalGoal_7 personalGoal_7;
    private final Coordinates a = new Coordinates(2,1);
    private final Coordinates b = new Coordinates(1,3);
    private final Coordinates c = new Coordinates(0,0);
    private final Coordinates d = new Coordinates(5,2);
    private final Coordinates e = new Coordinates(4,4);
    private final Coordinates f = new Coordinates(3,0);


    @BeforeEach
    void setUp(){
        personalGoal_7 = new PersonalGoal_7();

    }

    @Test
    public void testInitialize() {

        personalGoal_7.initialize();
        HashMap<Coordinates, Color> coloredGoal = personalGoal_7.getColoredGoal();
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