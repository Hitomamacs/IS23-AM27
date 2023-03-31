package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TileBagTest {

    //test to see that I create the correct number of tiles
    @Test
    void initializeBag() {
        TileBag tilebag=new TileBag();
        assertEquals(132, tilebag.getBag().size());
    }

    @Test
    public void testCheckCorrectNumberColor(){
        TileBag tileBag = new TileBag();

        for(Color color : Color.values()){
            Set<Tile> tiles = tileBag.getBag().stream()
                    .filter(tile -> tile.getColor() == color)
                    .collect(Collectors.toSet());
            assertEquals(22, tiles.size());
        }
    }

    //test to see that I take the right number of tiles and that they are no longer present in tilebags
    @Test
    public void testRandomPick(){
        TileBag tilebag=new TileBag();
        Set<Tile> pickedTiles = tilebag.randomPick(7);
        assertEquals(7,pickedTiles.size());
        assertFalse(tilebag.getBag().containsAll(pickedTiles));
    }

    //test to see that if the tilebag is empty then I don't take any more tiles
    @Test
    public void testRandomPickEmptyBag() {
        TileBag tilebag = new TileBag();
        tilebag.randomPick(132);
        Set<Tile> pickedTiles = tilebag.randomPick(1);
        assertTrue(pickedTiles.isEmpty());
    }

}