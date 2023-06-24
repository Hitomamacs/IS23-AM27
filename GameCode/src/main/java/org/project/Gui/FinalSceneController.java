package org.project.Gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.Map;

public class FinalSceneController {
    private GuiUserInterface guiUserInterface;
    private GuiFx guiFx;
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
    public void setCentralController(GuiFx guiFx) {
        this.guiFx=guiFx;
    }

    public void setGuiUserInterface(GuiUserInterface guiUserInterface) {
        this.guiUserInterface=guiUserInterface;
    }

    public void init(HashMap<String,Integer> finalScore) {

        String result;

        if(finalScore.size()==2){

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
    String AssignScore(HashMap<String,Integer> finalScore){
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
}
