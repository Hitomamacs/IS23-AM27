import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.Before;
import org.project.Controller.Control.Game;
import org.project.Controller.Control.GameOrchestrator;
import org.project.Controller.Control.Persistencer;
import org.project.Controller.Control.User;
import org.project.Controller.States.GameState;
import org.project.Controller.States.StartTurnState;
import org.project.Model.Player;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersistencerTest {
    private Game game;
    private GameOrchestrator orchestrator;
    private GameState state;

    private Persistencer persistencer;


    private GameOrchestrator restored;

    @BeforeEach
    public void setup(){
        persistencer = new Persistencer();

        game = new Game();
        List<Player> players = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            players.add(new Player("player" + i));
        }
        game.gameInit(players);
        orchestrator = new GameOrchestrator(game.getPlayers(), game.getGameBoard(), game.getCommonGoals(), game.getPointAssigner(), game.getTileBag(), game);
        state = new StartTurnState(orchestrator);
        for(int i = 0; i < 4; i++){
            orchestrator.getCurrentPlayer().setConnected(true);
            orchestrator.nextPlayer();
        }
        persistencer.saveGame(orchestrator, "save_test");


    }



    @Test
  public  void load_all() {
        restored = persistencer.load_all("save_test.json");
        //assertSame(restored.getSelectedCGoal().get(0).getClass(), orchestrator.getSelectedCGoal().get(0).getClass());
        //assertSame(restored.getPlayer(0).getMyPersonalGoal().getClass(), orchestrator.getPlayer(0).getMyPersonalGoal().getClass());

    }


}