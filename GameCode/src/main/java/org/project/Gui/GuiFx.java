package org.project.Gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.project.ClientView;

import java.util.List;

public class GuiFx extends Application {

    private GuiController guiController;

    private static ClientView clientView;

    private static GuiUserInterface guiUserInterface;

    public static void setClientView(ClientView clientView1){
        clientView=clientView1;
    }

    public static void setguiUserInterface(GuiUserInterface guiUserInterface1){
        guiUserInterface=guiUserInterface1;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/welcome.fxml"));

            Parent root = loader.load();



            WelcomeController welcomeController = loader.getController();
            welcomeController.setGuiUserInterface(guiUserInterface);
            //welcomeController.setClientView(clientView);

            primaryStage.setScene(new Scene(root));
            primaryStage.show();

            guiController=new GuiController(primaryStage);
            welcomeController.setGuiController(guiController);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
