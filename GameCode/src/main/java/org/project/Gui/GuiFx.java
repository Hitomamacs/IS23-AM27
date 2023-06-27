package org.project.Gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.project.ClientPack.ClientView;
import org.project.Controller.Messages.ChatMessage;

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

    private OtherGridsController otherGridsController;

    private Scene otherGridsScene;
    private FinalSceneController finalSceneController;
    private Scene finalScene;

    private ChatController chatController;

    private Scene chatScene;
    private static ClientView clientView;
    private static GuiUserInterface guiUserInterface;
    private boolean refreshBool = false;
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

            this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Platform.exit();
                    System.exit(0);
                }
            });

            guiUserInterface.getClientView().addPropertyChangeListener(getPopupListener());
            guiUserInterface.getClientView().addPropertyChangeListener(getRefreshListener());
            guiUserInterface.getClientView().addPropertyChangeListener(getPickListener());
            guiUserInterface.getClientView().addPropertyChangeListener(getTopupListener());
            guiUserInterface.getClientView().addPropertyChangeListener(getScoreListener());
            guiUserInterface.getClientView().addPropertyChangeListener(getChatListener());
            guiUserInterface.setGuiCentralController(this);

            showWelcomeScene();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public Stage getPrimaryStage() {
        return primaryStage;
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
    public void showOtherGridsScene(){
        if (otherGridsController == null){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(GuiFx.class.getResource("/fxml/OtherGridsController.fxml"));
                Scene newOtherGridsScene = new Scene(loader.load());

                otherGridsScene = newOtherGridsScene;
                primaryStage.setScene(newOtherGridsScene);
                primaryStage.show();

                otherGridsController = loader.getController();
                otherGridsController.setCentralController(this);
                otherGridsController.setGuiUserInterface(guiUserInterface);
                otherGridsController.init();
                otherGridsController.refreshGrids();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        if (otherGridsController != null){
            primaryStage.setScene(otherGridsScene);
            otherGridsController.refreshGrids();
            primaryStage.show();
        }
    }
    public void showFinalScene(){
        if(finalSceneController==null){
            try{
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(GuiFx.class.getResource("/fxml/FinalScene.fxml"));
                Scene newFinalScene= new Scene(loader.load());

                finalSceneController = loader.getController();
                finalSceneController.setCentralController(this);
                finalSceneController.setGuiUserInterface(guiUserInterface);
                finalSceneController.init(guiUserInterface.getClientView().getScoreBoard());

                finalScene=newFinalScene;
                primaryStage.setScene(newFinalScene);
                primaryStage.show();


            }catch(Exception e){
                e.printStackTrace();
            }
        }

        if (finalSceneController != null){
            primaryStage.setScene(finalScene);
            primaryStage.show();
        }
    }
    public void showChatScene(){
        if(chatController == null){
            try{
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(GuiFx.class.getResource("/fxml/Chat.fxml"));
                Scene newChatScene= new Scene(loader.load());

                chatController = loader.getController();
                chatController.setCentralController(this);
                chatController.setGuiUserInterface(guiUserInterface);
                chatController.initialize();

                chatScene = newChatScene;
                primaryStage.setScene(newChatScene);
                primaryStage.show();


            }catch(Exception e){
                e.printStackTrace();
            }
        }

        if ( chatController!= null){
            primaryStage.setScene(chatScene);
            primaryStage.show();
        }
    }
    public void showBanner() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(primaryStage);
            alert.setTitle("Server Crash");
            alert.setHeaderText("Server is unreachable");
            alert.setContentText("The server has crashed and is currently unreachable.");

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    Platform.exit(); // Close the entire application when OK is clicked
                }
            });
        });
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
                    System.out.println("fire popup");

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
                    if(!refreshBool){
                        mainSceneController.refreshGrid(clientView.getGridsview().get(guiUserInterface.getNickname()));
                        refreshBool = true;
                        System.out.println(refreshBool);
                    }
                    //showMainScene();
                    System.out.println("fire refresh");
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
                    System.out.println("fire pick");
                    //showMainScene();
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
                    if(guiUserInterface.getNickname().equals(evt.getNewValue())) {
                        mainSceneController.updateGrid(clientView.getGridsview().get(guiUserInterface.getNickname()));
                    }
                    System.out.println("fire topup");
                    //showMainScene();

                }

            });
        }
    };

    public PropertyChangeListener getScoreListener(){
        return scoreListener;
    }
    PropertyChangeListener scoreListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Platform.runLater(()->{
                if("score".equals(evt.getPropertyName())){
                    System.out.println("fire score");
                    showFinalScene();
                }
            });
        }
    };
    public PropertyChangeListener getChatListener(){
        return chatListener;
    }
    PropertyChangeListener chatListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Platform.runLater(()->{
                if("chat".equals(evt.getPropertyName())){
                    showChatScene();
                    ChatMessage message = clientView.getChat().get(clientView.getChat().size() - 1);
                    chatController.addMessageToChat(message.getUsername(), message.getText());
                    showChatScene();
                }
            });
        }
    };
}

