package org.project.Controller.Control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.project.Controller.Control.GameOrchestrator;
import org.project.Controller.States.*;
import org.project.Model.*;
import org.project.Model.CommonGoals.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to save and load a game
 *
 */
public class Persistencer {
    public static Gson gson_parser = new GsonBuilder()
            .registerTypeAdapter(Player.class, new PlayerDeserializer())
            .excludeFieldsWithoutExposeAnnotation()  // se vuoi escludere i campi senza l'annotazione @Expose
            .create();

    //* This method takes a GameOrchestrator object and returns a JSON string and then takes the JSON sting and writes it to a file*/
    /** This method takes a GameOrchestrator object and returns a JSON string and then takes the JSON sting and writes it to a file
     * @param gameOrchestrator
     * @param fileName
     */
    public void saveGame(GameOrchestrator gameOrchestrator, String fileName) {
        String json = gson_parser.toJson(gameOrchestrator);
        try {
            File file = new File(fileName + ".json");
            if(file.exists()) {
                file.delete();
            };
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

    /**
     * This method takes a file name and returns a GameOrchestrator object
     * @param fileName
     * @return
     */
    public static GameOrchestrator loadGame(String fileName) {
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(fileName ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GameOrchestrator gameOrchestrator = gson_parser.fromJson(reader, GameOrchestrator.class);
        return gameOrchestrator;
    }

    /**
     * Since the states are from an abstract class we need to load the states manually
     * @param gameOrchestrator
     * @return
     */
    public boolean load_states(GameOrchestrator gameOrchestrator) {
        gameOrchestrator.setSelectedCGoal(new ArrayList<CommonGoal>());
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

    /**
     * Since the C Goals are from an abstract class we need to load the states manually
     * @param gameOrchestrator
     * @return
     */

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

    /**
     * Since the P Goals are from an abstract class we need to load the states manually we are using json files to store the personal goals
     * this method loads the personal goals from the json files
     * @param gameOrchestrator
     */
    public void load_pgoals(GameOrchestrator gameOrchestrator){
        for(Player p : gameOrchestrator.getPlayers()){
            p.personal_list_init("PGoals.json");
            p.recoverPersonalGoal();
        }
    }

    /**
     * this method combines all the load methods
     * @param filename
     * @return
     */

    public GameOrchestrator load_all(String filename){
        GameOrchestrator loaded = loadGame(filename);
        load_states(loaded);
        load_common_goals(loaded);
        load_pgoals(loaded);
        return loaded;
    }

    /**
     * this method creates the filename concatenating the players nicknames in alphabetical order
     * @param gameOrchestrator
     * @return
     */

    public String get_file_name(GameOrchestrator gameOrchestrator){
        List<String> names = new ArrayList<>();
        for(Player p : gameOrchestrator.getPlayers()){
            names.add(p.getNickname());
        }
        List<String> sorted_list = names.stream().sorted().toList();
        String file_name = "";
        for(String s : sorted_list){
            file_name += s;
        }
        return file_name;


    }

}
