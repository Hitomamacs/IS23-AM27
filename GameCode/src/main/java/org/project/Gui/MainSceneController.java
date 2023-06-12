package org.project.Gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Random;

public class MainSceneController {
    private GuiUserInterface guiUserInterface;
    private GuiController guiController;

    @FXML
    private GridPane GrigliaBoard;
    private ImageView[] azureImage= {
            new ImageView(new Image(getClass().getResourceAsStream("/images/Trofei1.1.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Trofei1.2.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Trofei1.3.png")))
    };
    private ImageView[] blueImage= {
            new ImageView(new Image(getClass().getResourceAsStream("/images/Cornici1.1.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Cornici1.2.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Cornici1.3.png")))
    };
    private ImageView[] pinkImage= {
            new ImageView(new Image(getClass().getResourceAsStream("/images/Piante1.1.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Piante1.2.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Piante1.3.png")))
    };
    private ImageView[] yellowImage= {
            new ImageView(new Image(getClass().getResourceAsStream("/images/Giochi1.1.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Giochi1.2.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Giochi1.3.png")))
    };
    private ImageView[] whiteImage= {
            new ImageView(new Image(getClass().getResourceAsStream("/images/Libri1.1.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Libri1.2.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Libri1.3.png")))
    };
    private ImageView[] greenImage= {
            new ImageView(new Image(getClass().getResourceAsStream("/images/Gatti1.1.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Gatti1.2.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Gatti1.3.png")))
    };
    private GuiFx centralController;
    public void setCentralController(GuiFx controller){
        this.centralController = controller;
    }
    public void refillBoard(String[][] board){
        Random random = new Random();
        double cellWidth = GrigliaBoard.getWidth() / 9;
        double cellHeight = GrigliaBoard.getHeight() / 9;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                ImageView tileImageView = new ImageView();
                switch(board[i][j]){
                    case("I"):
                        break;
                    case("A"):
                        tileImageView.setImage(azureImage[random.nextInt(3)].getImage());
                        break;
                    case("P"):
                        tileImageView.setImage(pinkImage[random.nextInt(3)].getImage());
                        break;
                    case("G"):
                        tileImageView.setImage(greenImage[random.nextInt(3)].getImage());
                        break;
                    case("Y"):
                        tileImageView.setImage(yellowImage[random.nextInt(3)].getImage());
                        break;
                    case("W"):
                        tileImageView.setImage(whiteImage[random.nextInt(3)].getImage());
                        break;
                    case("B"):
                        tileImageView.setImage(blueImage[random.nextInt(3)].getImage());
                    default:
                        break;
                }
                tileImageView.setFitWidth(cellWidth);
                tileImageView.setFitHeight(cellHeight);
                GrigliaBoard.add(tileImageView, i, j);
            }
        }
    }
    public PropertyChangeListener getRefreshListener() {
        return refreshlistener;
    }

    PropertyChangeListener refreshlistener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Platform.runLater(() -> {
                if("refresh".equals(evt.getPropertyName())){
                    Random random=new Random();
                }
            });
        }
    };

    public void setGuiUserInterface(GuiUserInterface guiUserInterface) {
        this.guiUserInterface = guiUserInterface;
    }

    public void setGuiController(GuiController guiController) {
        this.guiController = guiController;
    }
}
