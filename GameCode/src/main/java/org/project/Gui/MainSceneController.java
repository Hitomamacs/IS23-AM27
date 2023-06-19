package org.project.Gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.project.Model.Coordinates;

import java.util.*;

public class MainSceneController {
    private GuiUserInterface guiUserInterface;
    private GuiController guiController;

    @FXML
    private GridPane GrigliaBoard;
    @FXML
    private Label Status;
    @FXML
    private Button Pick;

    @FXML
    private ImageView Tile1;

    @FXML
    private ImageView Tile2;

    @FXML
    private ImageView Tile3;





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

    public Label getStatus(){
        return this.Status;
    }
    public void refreshBoard(String[][] board){
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
                        break;
                    case ("N"):
                        tileImageView.setImage(null);
                        break;
                    default:
                        break;
                }
                tileImageView.setFitWidth(cellWidth);
                tileImageView.setFitHeight(cellHeight);
                GrigliaBoard.add(tileImageView, j, i);
                int finalI = i;
                int finalJ = j;
                ImageView tile = (ImageView) GrigliaBoard.getChildren().get(i * 9 + j);
                if (board[i][j].equals("N")) {
                    tile.setImage(null);
                }
                tileImageView.setOnMouseClicked(event -> selectTile(finalJ, finalI));

            }
        }
    }


    public void moveTiles(){
        int i = 0;
        for(Coordinates c : coordinates){
            ImageView tile = (ImageView) GrigliaBoard.getChildren().get(c.getX() * 9 + c.getY());
            ImageView tileCopy = new ImageView(tile.getImage());
            switch (i){
                case 0:
                    Tile1.setImage(tileCopy.getImage());
                    break;
                case 1:
                    Tile2.setImage(tileCopy.getImage());
                    break;
                case 2:
                    Tile3.setImage(tileCopy.getImage());
                    break;
                default:
                    break;
            }
            selectedTiles.remove(0);
            GrigliaBoard.getChildren().remove(selectedBorders.get(0));
            selectedBorders.remove(0);
            i++;
            tile.setImage(null);
        }
        coordinates.clear();

    }
    public void selectTile(int column, int row) {
        ImageView tile = (ImageView) GrigliaBoard.getChildren().get(row * 9 + column);
        int tileIndex = selectedTiles.indexOf(tile);

        if (tileIndex != -1) { // Tile is already selected, so deselect it
            ImageView deselectedTile = selectedTiles.get(tileIndex);
            deselectedTile.setEffect(null);

            //remove tile from coordinates array
            Iterator<Coordinates> iterator = coordinates.iterator();
            while (iterator.hasNext()) {
                Coordinates c = iterator.next();
                if(c.getX() == row && c.getY() == column) {
                    iterator.remove();
                    break;
                }
            }

            selectedTiles.remove(tileIndex);
            GrigliaBoard.getChildren().remove(selectedBorders.get(tileIndex));
            selectedBorders.remove(tileIndex);

        } else { // Tile is not selected, so select it
            if (selectedTiles.size() >= 3) {
                // Maximum number of tiles already selected, remove the oldest selection
                ImageView oldestTile = selectedTiles.get(0);
                oldestTile.setEffect(null);

                //Remove oldest tile from coordinates
                coordinates.remove(0);

                selectedTiles.remove(0);
                GrigliaBoard.getChildren().remove(selectedBorders.get(0));
                selectedBorders.remove(0);
            }

            // Create green rectangle border only if it doesn't exist already
            if (!selectedBorders.stream().anyMatch(border -> GridPane.getColumnIndex(border) == column && GridPane.getRowIndex(border) == row)) {
                selectedBorders.add(new Rectangle(tile.getFitWidth(), tile.getFitHeight()));
                int size = selectedBorders.size();
                Rectangle selectedBorder = selectedBorders.get(size - 1);
                selectedBorder.setStroke(Color.GREEN);
                selectedBorder.setStrokeWidth(2);
                selectedBorder.setFill(Color.TRANSPARENT);

                selectedBorder.setMouseTransparent(true);

                // Position the border over the tile
                GridPane.setConstraints(selectedBorder, column, row);

                // Add the border to the board
                GrigliaBoard.getChildren().add(selectedBorder);
            }

            // Add the tile to the selected set
            selectedTiles.add(tile);

            //Add tile to coordinates
            coordinates.add(new Coordinates(row, column));

            // Apply visual effect to the selected tile
            tile.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.GREEN, 10, 0.5, 0, 0));
        }
    }

    public void PickAction(ActionEvent actionEvent){
        if(coordinates.size() > 0){
            guiUserInterface.getClient().SendPickMessage(guiUserInterface.getNickname(), coordinates.size(), coordinates);
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
