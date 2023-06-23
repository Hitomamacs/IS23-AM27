package org.project.Gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.project.ClientView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GuiFx extends Application {

    private Stage primaryStage;
    private WelcomeController welcomeController;
    private Scene welcomeScene;
    private MainSceneController mainSceneController;
    private Scene mainScene;
    private CreateGameController createGameController;
    private Scene createGameScene;
    private JoinController joinController;
    private Scene joinScene;
    private static ClientView clientView;
    private static GuiUserInterface guiUserInterface;
    public static void setClientView(ClientView clientView1){
        clientView=clientView1;
    }
    public static void setguiUserInterface(GuiUserInterface guiUserInterface1){
        guiUserInterface=guiUserInterface1;
    }

    public GuiUserInterface getGuiUserInterface(){
        return guiUserInterface;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        try{

            this.primaryStage = primaryStage;
            this.primaryStage.setTitle("My Shelfie");

            guiUserInterface.getClientView().addPropertyChangeListener(getPopupListener());
            guiUserInterface.getClientView().addPropertyChangeListener(getRefreshListener());
            guiUserInterface.getClientView().addPropertyChangeListener(getPickListener());
            guiUserInterface.getClientView().addPropertyChangeListener(getTopupListener());

            showWelcomeScene();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void showWelcomeScene(){
        if (welcomeController == null){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(GuiFx.class.getResource("/fxml/welcome.fxml"));
                Scene newWelcomeScene = new Scene(loader.load());

                welcomeController = loader.getController();
                welcomeController.setCentralController(this);

                welcomeScene = newWelcomeScene;
                primaryStage.setScene(newWelcomeScene);
                primaryStage.setResizable(false);

                primaryStage.show();

            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        if (welcomeScene != null) {
            primaryStage.setScene(welcomeScene);
            primaryStage.show();
            primaryStage.setResizable(false);

        }
    }
    public void showCreateGameScene(){
        if (createGameController == null){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(GuiFx.class.getResource("/fxml/createGame.fxml"));
                Scene newCreateGameScene = new Scene(loader.load());

                createGameScene = newCreateGameScene;
                primaryStage.setScene(newCreateGameScene);
                primaryStage.show();
                primaryStage.setResizable(false);

                createGameController = loader.getController();
                createGameController.setCentralController(this);

            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        if (createGameScene != null) {
            primaryStage.setScene(createGameScene);
            primaryStage.show();
            primaryStage.setResizable(false);
        }
    }
    public void showJoinScene(){
        if (joinController == null){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(GuiFx.class.getResource("/fxml/join.fxml"));
                Scene newJoinScene = new Scene(loader.load());

                joinScene = newJoinScene;
                primaryStage.setScene(newJoinScene);
                primaryStage.show();

                joinController = loader.getController();
                joinController.setCentralController(this);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        if (joinScene != null){
            primaryStage.setScene(joinScene);
            primaryStage.show();
        }
    }
    public void showMainScene(){
        if (mainSceneController == null){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(GuiFx.class.getResource("/fxml/MainScene.fxml"));
                Scene newMainScene = new Scene(loader.load());

                mainScene = newMainScene;
                primaryStage.setScene(newMainScene);
                primaryStage.show();

                mainSceneController = loader.getController();
                mainSceneController.setCentralController(this);
                mainSceneController.setGuiUserInterface(guiUserInterface);
                mainSceneController.init();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        if (mainScene != null){
            primaryStage.setScene(mainScene);
            primaryStage.show();
        }
    }
    public PropertyChangeListener getPopupListener() {
        return popupListener;
    }

    PropertyChangeListener popupListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Platform.runLater(() -> {
                if("popupCreate".equals(evt.getPropertyName())){
                    int identifier = guiUserInterface.getClientView().getPopUpIdentifier();
                    String message = guiUserInterface.getClientView().getPopUpErrorMessage();

                    switch(identifier){
                        case (0):
                            createGameController.getLoginStatus().setText(message);
                            break;
                        case (1):
                            joinController.getLoginStatus().setText(message);
                            break;
                        case (2):
                            //Whatever pick has to say
                            mainSceneController.getStatus().setText(message);
                            break;
                        case (3):
                            //Whatever topUp has to say it is probably same code as case 2
                            mainSceneController.getStatus().setText(message);
                            break;
                        case (4):
                            //General messages have to appear on each scene
                            mainSceneController.getStatus().setText(message);
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    };
    public PropertyChangeListener getRefreshListener() {
        return refreshlistener;
    }
    PropertyChangeListener refreshlistener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Platform.runLater(() -> {
                if("refresh".equals(evt.getPropertyName())){
                    showMainScene();
                    mainSceneController.refreshBoard(clientView.getBoard());
                    showMainScene();
                }
            });
        }
    };
    public PropertyChangeListener getPickListener() {
        return pickListener;
    }
    PropertyChangeListener pickListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Platform.runLater(() -> {
                if("pick".equals(evt.getPropertyName())){
                    if(guiUserInterface.getNickname().equals(evt.getNewValue()))
                        mainSceneController.moveTiles();
                    else
                        mainSceneController.refreshBoard(clientView.getBoard());
                    showMainScene();
                }
            });
        }
    };

    public PropertyChangeListener getTopupListener() {
        return topupListener;
    }

    PropertyChangeListener topupListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Platform.runLater(() -> {
                if("topup".equals(evt.getPropertyName())){
                    if(guiUserInterface.getNickname().equals(evt.getNewValue()))
                        mainSceneController.updateGrid(clientView.getGridsview().get(guiUserInterface.getNickname()));
                    showMainScene();

                }

            });
        }
    };
}

