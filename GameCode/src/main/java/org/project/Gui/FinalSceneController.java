package org.project.Gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller that controls the Final scene
 */
public class FinalSceneController {

    private GuiUserInterface guiUserInterface;
    private GuiFx centralController;
    @FXML
    private Label FirstNameLable;
    @FXML
    private Label SecondNameLable;
    @FXML
    private Label ThirdNameLable;
    @FXML
    private Label FourthNameLable;
    @FXML
    private Label FirstScore;
    @FXML
    private Label SecondScore;
    @FXML
    private Label ThirdScore;
    @FXML
    private Label FourthScore;
    @FXML
    private Button BackButton;
    @FXML
    private Button QuitButton;

    /**
     * The method is used to return to the Main Scene
     * @param actionEvent button press
     */
    public void BackToMain(ActionEvent actionEvent) {
        Platform.runLater(() -> {
            centralController.showMainScene();
        });
    }
    public void setCentralController(GuiFx guiFx) {
        this.centralController = guiFx;
    }
    public void setGuiUserInterface(GuiUserInterface guiUserInterface) {
        this.guiUserInterface=guiUserInterface;
    }

    /**
     * The method initializes the final scene showing player names and scores in order of high scores
     * @param finalScore map of the final scores
     *                   the keys are the names of the players and the values are the corresponding scores
     */
    public void init(HashMap<String,Integer> finalScore) {

        String result;

        if(finalScore.size()==2) {

            ThirdScore.setVisible(false);
            ThirdNameLable.setVisible(false);
            FourthScore.setVisible(false);
            FourthNameLable.setVisible(false);

            result=AssignScore(finalScore);
            FirstNameLable.setText(result);
            FirstScore.setText(finalScore.get(result).toString());
            finalScore.remove(result);
            result=AssignScore(finalScore);
            SecondNameLable.setText((result));
            SecondScore.setText(finalScore.get(result).toString());

        } else if (finalScore.size()==3) {

            FourthScore.setVisible(false);
            FourthNameLable.setVisible(false);

            result=AssignScore(finalScore);
            FirstNameLable.setText(result);
            FirstScore.setText(finalScore.get(result).toString());
            finalScore.remove(result);
            result=AssignScore(finalScore);
            SecondNameLable.setText((result));
            SecondScore.setText(finalScore.get(result).toString());
            finalScore.remove(result);
            result=AssignScore(finalScore);
            ThirdNameLable.setText((result));
            ThirdScore.setText(finalScore.get(result).toString());
            finalScore.remove(result);

        }else{
            result=AssignScore(finalScore);
            FirstNameLable.setText(result);
            FirstScore.setText(finalScore.get(result).toString());
            finalScore.remove(result);
            result=AssignScore(finalScore);
            SecondNameLable.setText((result));
            SecondScore.setText(finalScore.get(result).toString());
            finalScore.remove(result);
            result=AssignScore(finalScore);
            ThirdNameLable.setText((result));
            ThirdScore.setText(finalScore.get(result).toString());
            finalScore.remove(result);
            result=AssignScore(finalScore);
            FourthNameLable.setText((result));
            FourthScore.setText(finalScore.get(result).toString());
            finalScore.remove(result);
        }
    }

    /**
     * The method finds the player with the highest score in the final score map by comparing scores
     * @param finalScore map of the final scores,
     *                  the keys are the names of the players and the values are the corresponding scores
     * @return the player with the highest score
     */
    public String AssignScore(HashMap<String,Integer> finalScore){
        String highestScorer = null;
        int highestScore = Integer.MIN_VALUE;

        for (Map.Entry<String, Integer> entry : finalScore.entrySet()) {
            String playerName = entry.getKey();
            int score = entry.getValue();

            if (score > highestScore) {
                highestScore = score;
                highestScorer = playerName;
            }
        }
        return highestScorer;
    }

    /**
     * The method is an event handler for the Quit button, it calls the quit() method to quit the application.
     * @param actionEvent button press
     */
    public void QuitAction(ActionEvent actionEvent) {
        quit();
    }

    /**
     * The method is called to close the application
     */
    public void quit(){
        Platform.exit();
        System.exit(0);
    }
}
