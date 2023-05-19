import org.junit.Test;

import org.project.Controller.Control.Game;
import org.project.Model.Player;

import static org.junit.Assert.assertTrue;


class GameTest {

    @Test
    void gameInit() {
        Game game = new Game();
        for(int i = 0; i < 4; i++){
            game.getPlayers().add(new Player("player" + i));
        }
        game.gameInit(2);
        assertTrue(game.getPlayers().size() == 2);
    }
}