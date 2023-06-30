package org.project.Gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.project.Model.Coordinates;

import java.util.*;

/**
 *Controller that controls the OtherGrids scene
 */

public class OtherGridsController {

    /**
     * The method is used to return to the Main Scene
     * @param actionEvent button press
     */
    public void BackToMain(ActionEvent actionEvent) {
        Platform.runLater(() -> {
            centralController.showMainScene();
        });
    }

    private GuiUserInterface guiUserInterface;
    private GuiFx centralController;
    @FXML
    private GridPane Grid1Pane;
    @FXML
    private GridPane Grid2Pane;
    @FXML
    private GridPane Grid3Pane;
    @FXML
    private ImageView Grid1;
    @FXML
    private ImageView Grid2;
    @FXML
    private ImageView Grid3;
    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label player3;
    @FXML
    private Button Back;
    @FXML
    private Pane bannerPane;
    @FXML
    private Button warning;

    /**
     * The method displays a specific warning message to inform the user that the server is inaccessible or has crashed.
     * This dialog is displayed on top of the main application window.
     */
    public void showBanner() {
        Platform.runLater(() -> {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(centralController.getPrimaryStage());
            dialog.setTitle("Server Crash");
            dialog.setHeaderText("Server is unreachable");

            Label contentLabel = new Label("The server has crashed and is currently unreachable.");
            VBox contentPane = new VBox(10);
            contentPane.setAlignment(Pos.CENTER);
            contentPane.setPadding(new Insets(20));
            contentPane.getChildren().add(contentLabel);

            dialog.getDialogPane().setContent(contentPane);

            dialog.showAndWait();
        });
    }
    private ArrayList<String> playerNames = new ArrayList<>();
    private ArrayList<GridPane> gridPanes = new ArrayList<>();
    private ArrayList<ImageView> grids = new ArrayList<>();
    private ArrayList<Label> labels = new ArrayList<>();
    private HashMap<Integer, Set<Integer>> addedPositions = new HashMap<>();
    private ImageView[] azureImage = {
            new ImageView(new Image(getClass().getResourceAsStream("/images/Trofei1.1.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Trofei1.2.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Trofei1.3.png")))
    };
    private ImageView[] blueImage = {
            new ImageView(new Image(getClass().getResourceAsStream("/images/Cornici1.1.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Cornici1.2.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Cornici1.3.png")))
    };
    private ImageView[] pinkImage = {
            new ImageView(new Image(getClass().getResourceAsStream("/images/Piante1.1.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Piante1.2.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Piante1.3.png")))
    };
    private ImageView[] yellowImage = {
            new ImageView(new Image(getClass().getResourceAsStream("/images/Giochi1.1.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Giochi1.2.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Giochi1.3.png")))
    };
    private ImageView[] whiteImage = {
            new ImageView(new Image(getClass().getResourceAsStream("/images/Libri1.1.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Libri1.2.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Libri1.3.png")))
    };
    private ImageView[] greenImage = {
            new ImageView(new Image(getClass().getResourceAsStream("/images/Gatti1.1.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Gatti1.2.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Gatti1.3.png")))
    };
    public void setCentralController(GuiFx controller) {
        this.centralController = controller;
    }
    public void setGuiUserInterface(GuiUserInterface guiUserInterface) {
        this.guiUserInterface = guiUserInterface;
    }

    /**
     * The method sets the grids of the other players visible only for the participating players
     * and adding the empty images in the correct position of the grids.
     * In addition, a data structure is prepared to track the locations added by players.
     */
    public void init() {
        grids.add(Grid1);
        grids.add(Grid2);
        grids.add(Grid3);
        gridPanes.add(Grid1Pane);
        gridPanes.add(Grid2Pane);
        gridPanes.add(Grid3Pane);
        labels.add(player1);
        labels.add(player2);
        labels.add(player3);
        HashMap<String, String[][]> hashMap = guiUserInterface.getClientView().getGridsview();

        for (Map.Entry<String, String[][]> entry : hashMap.entrySet()) {
            if (!(entry.getKey().equals(guiUserInterface.getNickname()))) {
                playerNames.add(entry.getKey());
            }
        }
        int num_players = guiUserInterface.getClientView().getGridsview().size() - 2;
        for (int i = 2; i > num_players; i--) {
            grids.get(i).setVisible(false);
            labels.get(i).setVisible(false);
        }
        for (int i = 0; i < num_players + 1; i++) {
            labels.get(i).setText(playerNames.get(i));
            addedPositions.put(i, new HashSet<>());
        }
        for (int i = 0; i < 3; i++) {
            GridPane grid = gridPanes.get(i);
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 5; k++) {
                ImageView image = new ImageView();
                grid.add(image, k, j);
                }
            }
        }
    }

    /**
     * The method refreshes the game grids, adding or removing images in the corresponding positions
     * based on the values of the cells in the player grids.
     */
    public void refreshGrids() {
        Platform.runLater(() -> {
            Random random = new Random();
            double cellWidth = Grid1Pane.getWidth() / 5;
            double cellHeight = Grid1Pane.getHeight() / 6;
            for (int i = 0; i < playerNames.size(); i++) {
                String[][] grid = guiUserInterface.getClientView().getGridsview().get(playerNames.get(i));
                for (int j = 0; j < 6; j++) {
                    for (int k = 0; k < 5; k++) {
                        int c = j * 5 + k;
                        ImageView image = new ImageView();
                        switch (grid[j][k]) {
                            case ("I"):

                                break;
                            case ("A"):
                                if (!addedPositions.get(i).contains(c)) {
                                    image = (ImageView) gridPanes.get(i).getChildren().get(c);
                                    image.setFitWidth(cellWidth);
                                    image.setFitHeight(cellHeight);
                                    image.setImage(azureImage[random.nextInt(3)].getImage());
                                    addedPositions.get(i).add(c);
                                }
                                break;
                            case ("P"):
                                if (!addedPositions.get(i).contains(c)) {
                                    image = (ImageView) gridPanes.get(i).getChildren().get(c);
                                    image.setFitWidth(cellWidth);
                                    image.setFitHeight(cellHeight);
                                    image.setImage(pinkImage[random.nextInt(3)].getImage());
                                    addedPositions.get(i).add(c);
                                }
                                break;
                            case ("G"):
                                if (!addedPositions.get(i).contains(c)) {
                                    image = (ImageView) gridPanes.get(i).getChildren().get(c);
                                    image.setFitWidth(cellWidth);
                                    image.setFitHeight(cellHeight);
                                    image.setImage(greenImage[random.nextInt(3)].getImage());
                                    addedPositions.get(i).add(c);
                                }
                                break;
                            case ("Y"):
                                if (!addedPositions.get(i).contains(c)) {
                                    image = (ImageView) gridPanes.get(i).getChildren().get(c);
                                    image.setFitWidth(cellWidth);
                                    image.setFitHeight(cellHeight);
                                    image.setImage(yellowImage[random.nextInt(3)].getImage());
                                    addedPositions.get(i).add(c);
                                }
                                break;
                            case ("W"):
                                if (!addedPositions.get(i).contains(c)) {
                                    image = (ImageView) gridPanes.get(i).getChildren().get(c);
                                    image.setFitWidth(cellWidth);
                                    image.setFitHeight(cellHeight);
                                    image.setImage(whiteImage[random.nextInt(3)].getImage());
                                    addedPositions.get(i).add(c);
                                }
                                break;
                            case ("B"):
                                if (!addedPositions.get(i).contains(c)) {
                                    image = (ImageView) gridPanes.get(i).getChildren().get(c);
                                    image.setFitWidth(cellWidth);
                                    image.setFitHeight(cellHeight);
                                    image.setImage(blueImage[random.nextInt(3)].getImage());
                                    addedPositions.get(i).add(c);
                                }
                                break;
                            case ("N"):
                                if (addedPositions.get(i).contains(c)) {
                                    image = (ImageView) gridPanes.get(i).getChildren().get(c);
                                    image.setImage(null);
                                    addedPositions.get(i).remove(c);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }

        });
    }
}

