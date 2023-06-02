package org.project.Gui;

import javafx.stage.Stage;

public class GuiController {

    private Stage primaryStage;

    public GuiController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void closeScene(){
        primaryStage.close();
    }
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
