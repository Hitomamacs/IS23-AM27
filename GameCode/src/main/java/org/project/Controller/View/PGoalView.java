package org.project.Controller.View;

public class PGoalView {
    private String[][] pGoalView;

    public PGoalView() {
        this.pGoalView = new String[6][5];
    }
    public void updateView(String[][] pGoalView){
        this.pGoalView = pGoalView;
    }
}
