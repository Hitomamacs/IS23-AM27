package org.project.Controller.Control;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void gameInit() {
        Game game = new Game();
        game.gameInit(2);
        assertTrue(game.getPlayers().size() == 2);
    }
}