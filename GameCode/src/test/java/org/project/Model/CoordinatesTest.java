package org.project.Model;

import org.junit.jupiter.api.Test;
import org.project.Model.Coordinates;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {
    private int x = 5;
    private int y = 6;

    Coordinates coordinates = new Coordinates(x, y);

    @Test
    void Verify(){


    assertEquals(coordinates.getX(),x );

}
}