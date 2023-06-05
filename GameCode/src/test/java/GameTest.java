import org.junit.Test;

import org.project.Controller.Control.Game;
import org.project.Model.Player;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


public class GameTest {

    @Test
    public void gameInit() {
        Game game = new Game();
        List<Player> players = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            players.add(new Player("player" + i));
        }
        game.gameInit(players);
        assertTrue(game.getPlayers().size() == 4);
    }
}