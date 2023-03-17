package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileBagTest {

    TileBag tilebag=new TileBag();
    @BeforeEach
    @Test
    void initializeBag() {
        tilebag.initializeBag();
        assertEquals(132,.size());
    }

    @Test
    void randomPick() {
    }
}