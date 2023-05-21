import org.junit.Test;

import org.project.Controller.Control.Game;
import org.project.Model.Player;

import static org.junit.Assert.assertTrue;


public class GameTest {

    @Test
    public void gameInit() {
        Game game = new Game();
        for(int i = 0; i < 4; i++){
            game.getPlayers().add(new Player("player" + i));
        }
        game.gameInit(4);
        assertTrue(game.getPlayers().size() == 4);
    }
}