package org.project.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.Model.Color;
import org.project.Model.Spot;
import org.project.Model.Tile;

import static org.junit.jupiter.api.Assertions.*;

class SpotTest {
    Tile tile;
    Spot place;
    @BeforeEach
    void setUp(){
        tile = new Tile(Color.GREEN, 1);
        place = new Spot(true, tile);
    }


    @Test
    @DisplayName("Testing occupied")
    void isOccupied() {
        assertTrue(place.isOccupied(), "Occupied True");
        place.setOccupied(false);
        assertFalse(place.isOccupied(), "Occupied False");

    }

    @Test
    void setOccupied() {
    }

    @Test
    @DisplayName("Testing Occupied")
    void getTile() {
        assertEquals(tile, place.getTile());
    }

    @Test
    void placeTile() {
    }

    @Test
    void removeTile() {
        Tile removed_tile = place.removeTile();
        assertEquals(tile, removed_tile, "Checking picked tile is ok");
        assertNull(place.getTile());
        assertEquals(tile, removed_tile,"Checking if after deletion tile is still memorized");
    }
}