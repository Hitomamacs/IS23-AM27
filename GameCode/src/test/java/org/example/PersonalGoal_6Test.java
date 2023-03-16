package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PersonalGoal_6Test {
    PersonalGoal_6 personalGoal_6;
    private final Coordinates a = new Coordinates(5,0);
    private final Coordinates b = new Coordinates(4,3);
    private final Coordinates c = new Coordinates(0,4);
    private final Coordinates d = new Coordinates(2,3);
    private final Coordinates e = new Coordinates(4,1);
    private final Coordinates f = new Coordinates(0,2);


    @BeforeEach
    void setUp(){
        personalGoal_6 = new PersonalGoal_6();

    }

    @Test
    public void testInitialize() {

        personalGoal_6.initialize();
        HashMap<Coordinates, Color> coloredGoal = personalGoal_6.getColoredGoal();
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