package org.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PersonalGoal_4Test {

    PersonalGoal_4 personalGoal_4;
    private final Coordinates a = new Coordinates(3,3);
    private final Coordinates b = new Coordinates(2,2);
    private final Coordinates c = new Coordinates(4,2);
    private final Coordinates d = new Coordinates(4,1);
    private final Coordinates e = new Coordinates(0,4);
    private final Coordinates f = new Coordinates(2,0);


    @BeforeEach
    void setUp(){
        personalGoal_4 = new PersonalGoal_4();

    }

    @Test
    public void testInitialize() {

        personalGoal_4.initialize();
        HashMap<Coordinates, Color> coloredGoal = personalGoal_4.getColoredGoal();
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