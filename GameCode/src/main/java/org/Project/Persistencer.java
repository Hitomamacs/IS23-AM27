package org.Project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Persistencer {
    static Gson gson_parser = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    //* This method takes a GameOrchestrator object and returns a JSON string and then takes the JSON sting and writes it to a file*/
    public static void saveGame(GameOrchestrator gameOrchestrator, String fileName) {
        String json = gson_parser.toJson(gameOrchestrator);
        try {
            PrintWriter out = new PrintWriter(new FileWriter(fileName + ".json"));
            out.println(json);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //return json function
    public static String returnJson(GameOrchestrator gameOrchestrator) {
        String json = gson_parser.toJson(gameOrchestrator);
        return json;
    }

    //function to load a game from a file
    public static GameOrchestrator loadGame(String fileName) {
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(fileName + ".json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GameOrchestrator gameOrchestrator = gson_parser.fromJson(reader, GameOrchestrator.class);
        return gameOrchestrator;
    }

    public boolean load_states(GameOrchestrator gameOrchestrator) {
        switch (gameOrchestrator.getCurr_sate_id()) {
            case 1:
                gameOrchestrator.changeState(new ConnectionCheckState(gameOrchestrator));
                return true;

            case 2:
                gameOrchestrator.changeState(new EndGameState(gameOrchestrator));
                return true;

            case 3:
                gameOrchestrator.changeState(new FullGridState(gameOrchestrator));
                return true;

            case 4:
                gameOrchestrator.changeState(new PickState(gameOrchestrator));
                return true;

            case 5:
                gameOrchestrator.changeState(new RefillState(gameOrchestrator));
                return true;

            case 6:
                gameOrchestrator.changeState(new StartTurnState(gameOrchestrator));
                return true;

            case 7:
                gameOrchestrator.changeState(new TopUpState(gameOrchestrator));
                return true;

            case 8:
                gameOrchestrator.changeState(new VerifyBoardableState(gameOrchestrator));
                return true;

            case 9:
                gameOrchestrator.changeState(new VerifyCommonGoalState(gameOrchestrator));
                return true;

            case 10:
                gameOrchestrator.changeState(new VerifyGrillableState(gameOrchestrator));
                return true;


        }
        return false;
    }

    public boolean load_common_goals(GameOrchestrator gameOrchestrator) {
        for (int i = 0; i < gameOrchestrator.get_selected_cgoal_int().size(); i++) {
            switch (gameOrchestrator.get_selected_cgoal_int().get(i)) {
                case 1:
                    gameOrchestrator.getSelectedCGoal().add(new CommonGoal_1());
                    break;
                case 2:
                    gameOrchestrator.getSelectedCGoal().add(new CommonGoal_2());
                    break;
                case 3:
                    gameOrchestrator.getSelectedCGoal().add(new CommonGoal_3());
                    break;
                case 4:
                    gameOrchestrator.getSelectedCGoal().add(new CommonGoal_4());
                    break;
                case 5:
                    gameOrchestrator.getSelectedCGoal().add(new CommonGoal_5());
                    break;
                case 6:
                    gameOrchestrator.getSelectedCGoal().add(new CommonGoal_6());
                    break;
                case 7:
                    gameOrchestrator.getSelectedCGoal().add(new CommonGoal_7());
                    break;
                case 8:
                    gameOrchestrator.getSelectedCGoal().add(new CommonGoal_8());
                    break;
                case 9:
                    gameOrchestrator.getSelectedCGoal().add(new CommonGoal_9());
                    break;
                case 10:
                    gameOrchestrator.getSelectedCGoal().add(new CommonGoal_10());
                    break;
                case 11:
                    gameOrchestrator.getSelectedCGoal().add(new CommonGoal_11());
                    break;
                case 12:
                    gameOrchestrator.getSelectedCGoal().add(new CommonGoal_12());
                    break;
            }


}
        return true;
    }

}
