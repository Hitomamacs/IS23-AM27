package org.project.Gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.project.Model.Coordinates;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.*;

public class MainSceneController {
    private GuiUserInterface guiUserInterface;
    private GuiController guiController;

    @FXML
    private GridPane GrigliaBoard;

    private ArrayList<Rectangle> selectedBorders = new ArrayList<>();
    private ArrayList<ImageView> selectedTiles = new ArrayList<>();

    private ArrayList<Coordinates> coordinates = new ArrayList<>();
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
                //This inverted order seems to be the correct one?
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
                GrigliaBoard.add(tileImageView, j, i);
                int finalI = i;
                int finalJ = j;
                tileImageView.setOnMouseClicked(event -> selectTile(finalJ, finalI));
            }
        }
    }
    public void selectTile(int column, int row) {
        ImageView tile = (ImageView) GrigliaBoard.getChildren().get(row * 9 + column);

        if (selectedTiles.contains(tile)) {
            // Tile is already selected, so deselect it
            tile.setEffect(null);
            selectedTiles.remove(tile);
            GrigliaBoard.getChildren().remove(selectedBorders.get(0));
            selectedBorders.remove(0);

            return;
        }

        if (selectedTiles.size() >= 3) {
            // Maximum number of tiles already selected, remove the oldest selection
            ImageView oldestTile = selectedTiles.get(0);
            oldestTile.setEffect(null);

            selectedTiles.remove(0);
            GrigliaBoard.getChildren().remove(selectedBorders.get(0));
            selectedBorders.remove(0);
        }

        // Create green rectangle border
        selectedBorders.add(new Rectangle(tile.getFitWidth(), tile.getFitHeight()));
        int size = selectedBorders.size();
        Rectangle selectedBorder = selectedBorders.get(size - 1);
        selectedBorder.setStroke(Color.GREEN);
        selectedBorder.setStrokeWidth(2);
        selectedBorder.setFill(Color.TRANSPARENT);

        // Position the border over the tile
        GridPane.setConstraints(selectedBorder, column, row);

        // Add the border to the board
        GrigliaBoard.getChildren().add(selectedBorder);

        // Add the tile to the selected set
        selectedTiles.add(tile);

        // Apply visual effect to the selected tile
        tile.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.GREEN, 10, 0.5, 0, 0));
    }
    public void PickAction(ActionEvent actionEvent){
        if(coordinates.size() > 0){
            centralController.getGuiUserInterface().getClient().SendPickMessage(guiUserInterface.getNickname(), coordinates.size(), coordinates);
        }
        //centralController.getGuiUserInterface().getClient().SendPickMessage();
    }
    public void setGuiUserInterface(GuiUserInterface guiUserInterface) {
        this.guiUserInterface = guiUserInterface;
    }

    public void setGuiController(GuiController guiController) {
        this.guiController = guiController;
    }
}
