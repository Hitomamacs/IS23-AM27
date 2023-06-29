package org.project.Gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.stage.Popup;
import org.project.Model.Coordinates;

import java.util.*;

/**
 * Controller that controls the Main scene
 */

public class MainSceneController {
    private GuiUserInterface guiUserInterface;
    private Coordinates lastpick;
    private double[] initialPosition = new double[2];
    private double[] initialPositionTile = new double[2];
    private int pickedTile = 0;
    private int[] lastTile = new int[]{-1,-1,-1,-1,-1};
    private int last_col = 0;
    private int col_tick = -1;
    private boolean firstboolean = true;
    private Set<Integer> addedPositions = new HashSet<>();
    private Set<Integer> addedGridPositions = new HashSet<>();

    @FXML
    private GridPane GridGriglia;
    @FXML
    private GridPane GrigliaBoard;
    @FXML
    private Label Status;
    @FXML
    private Button Pick;
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
    
    private int index = 1;
    private int tileCount = 0;
    @FXML
    private ImageView Tile1;
    @FXML
    private ImageView Tile2;
    @FXML
    private ImageView Tile3;
    @FXML
    private Button Grids;
    @FXML
    private ImageView CGoalCard1;
    @FXML
    private ImageView CommonGoal2;
    @FXML
    private ImageView PersonalGoalImage;
    private Coordinates firstTile = new Coordinates(8,8);
    private HashMap<Integer, ImageView> helper_board = new HashMap<>();
    private HashMap<Integer, ImageView> helper_grid = new HashMap<>();
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
    private ImageView[] commonGoal= {
            new ImageView(new Image(getClass().getResourceAsStream("/images/1.jpg"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/2.jpg"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/3.jpg"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/4.jpg"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/5.jpg"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/6.jpg"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/7.jpg"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/8.jpg"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/9.jpg"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/10.jpg"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/11.jpg"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/12.jpg")))
    };
    private ImageView[] personalGoal= {
            new ImageView(new Image(getClass().getResourceAsStream("/images/Personal_Goals.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Personal_Goals2.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Personal_Goals3.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Personal_Goals4.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Personal_Goals5.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Personal_Goals6.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Personal_Goals7.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Personal_Goals8.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Personal_Goals9.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Personal_Goals10.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Personal_Goals11.png"))),
            new ImageView(new Image(getClass().getResourceAsStream("/images/Personal_Goals12.png")))
    };
    private GuiFx centralController;
    public void setCentralController(GuiFx controller){
        this.centralController = controller;
    }

    /**
     * @return the Status attribute of the current object.
     * The Status attribute is of type Label and specifies the current state of the application.
     */
    public Label getStatus(){
        return this.Status;
    }

    /**
     * The method performs the operations to initialize and configure the graphical interface of the game
     */
    public void init(){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                ImageView image = new ImageView();
                image.setFitHeight(GrigliaBoard.getHeight() / 9);
                image.setFitWidth(GrigliaBoard.getWidth() / 9);
                helper_board.put(( i * 9 + j), image);
                GrigliaBoard.add(image, j, i);
            }
        }
        String username = guiUserInterface.getNickname();
        String[][] grid = guiUserInterface.getClientView().getGridsview().get(username);
        for (int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++){
                if(!(grid[i][j].equals("N") && !(grid[i][j].equals("I")))){
                    addedGridPositions.add(i * 5 + j);
                }
                ImageView image = new ImageView();
                image.setFitHeight(GridGriglia.getWidth() / 5);
                image.setFitHeight(GridGriglia.getHeight() / 6);
                helper_grid.put((i * 5 + j), image);
                GridGriglia.add(image, j ,i);
            }
        }
        decidedCommonGoals();
        decidedPersonalGoals();
    }

    public void boardCheck(String[][] board){
        Platform.runLater(()->{
            System.out.println("Boardcheck");
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    System.out.print(board[i][j] + " ");
                    if(board[i][j].equals("N")){
                        ImageView tile = (ImageView) GrigliaBoard.getChildren().get(i * 9 + j);
                        tile.setImage(null);
                    }
                }
                System.out.println();
            }
        });
    }

    /**
     * The method updates the visual appearance of the game board
     * @param board matrix representing the game board
     */
    public void refreshBoard(String[][] board){
        Platform.runLater(()->{
            Random random = new Random();
            double cellWidth = GrigliaBoard.getWidth() / 9;
            double cellHeight = GrigliaBoard.getHeight() / 9;
            if (!board[firstTile.getX()][firstTile.getY()].equals("N") && !board[firstTile.getX()][firstTile.getY()].equals("I")) {
                firstboolean = true;
                int a = (lastpick.getX()) * 9 + (lastpick.getY());
                addedPositions.clear();
                firstTile = new Coordinates(8, 8);
            }
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    int c = i * 9 + j;
                    int finalI = i;
                    int finalJ = j;

                    switch(board[i][j]){
                        case("I"):

                            break;
                        case("A"):
                            if(!addedPositions.contains(c)) {
                                helper_board.get(c).setImage(azureImage[random.nextInt(3)].getImage());
                                helper_board.get(c).setOnMouseClicked(event -> selectTile(finalJ, finalI));
                                addedPositions.add(c);
                            }
                            break;
                        case("P"):
                            if(!addedPositions.contains(c)) {
                                helper_board.get(c).setImage(pinkImage[random.nextInt(3)].getImage());
                                helper_board.get(c).setOnMouseClicked(event -> selectTile(finalJ, finalI));
                                addedPositions.add(c);
                            }
                            break;
                        case("G"):
                            if(!addedPositions.contains(c)) {
                                helper_board.get(c).setImage(greenImage[random.nextInt(3)].getImage());
                                helper_board.get(c).setOnMouseClicked(event -> selectTile(finalJ, finalI));
                                addedPositions.add(c);
                            }
                            break;
                        case("Y"):
                            if(!addedPositions.contains(c)) {
                                helper_board.get(c).setImage(yellowImage[random.nextInt(3)].getImage());
                                helper_board.get(c).setOnMouseClicked(event -> selectTile(finalJ, finalI));
                                addedPositions.add(c);
                            }
                            break;
                        case("W"):
                            if(!addedPositions.contains(c)) {
                                helper_board.get(c).setImage(whiteImage[random.nextInt(3)].getImage());
                                helper_board.get(c).setOnMouseClicked(event -> selectTile(finalJ, finalI));
                                addedPositions.add(c);
                            }
                            break;
                        case("B"):
                            if(!addedPositions.contains(c)) {
                                helper_board.get(c).setImage(blueImage[random.nextInt(3)].getImage());
                                helper_board.get(c).setOnMouseClicked(event -> selectTile(finalJ, finalI));
                                addedPositions.add(c);
                            }
                            break;
                        case ("N"):
                            if(addedPositions.contains(c)){
                                helper_board.get(c).setImage(null);
                                helper_board.get(c).setOnMouseClicked(null);
                                addedPositions.remove(c);
                            }
                            break;
                        default:
                            break;
                    }
                }
                }
            });
    }

    /**
     * The method sets the images of the common goals
     */
    public void decidedCommonGoals(){
        CGoalCard1.setImage(commonGoal[guiUserInterface.getClientView().getCommonGoalView().get(0)-1].getImage());
        CommonGoal2.setImage(commonGoal[guiUserInterface.getClientView().getCommonGoalView().get(1)-1].getImage());
    }

    /**
     * The method sets the images of the personal goals
     */
    public void decidedPersonalGoals(){
        String nickname = guiUserInterface.getNickname();
        PersonalGoalImage.setImage(personalGoal[guiUserInterface.getClientView().getPersonalGoalViews().get(nickname)].getImage());
        //System.out.println(guiUserInterface.getClientView().getPersonalGoalViews().get(nickname));
    }

    public ImageView getColoredImageTile(String tile){
        Random random = new Random();

        switch(tile){
            case("A"):
                return azureImage[random.nextInt(3)];
            case("P"):
                return pinkImage[random.nextInt(3)];
            case("G"):
                return greenImage[random.nextInt(3)];
            case("Y"):
                return yellowImage[random.nextInt(3)];
            case("W"):
                return whiteImage[random.nextInt(3)];
            case("B"):
                return blueImage[random.nextInt(3)];
            default:
                return null;
        }

    }

    public void refreshTile(String[] tiles ){

        Platform.runLater(()-> {

            for (int i = 0; i < 3; i++) {
                if (tiles[i] != null && !tiles[i].equals("N")) {
                    switch (i) {
                        case (0):
                            Tile1.setImage(getColoredImageTile(tiles[i]).getImage());
                            Tile1.setOnMousePressed(event -> {
                                initialPosition[0] = event.getX();
                                initialPosition[1] = event.getY();
                                initialPositionTile[0] = Tile1.getLayoutX();
                                initialPositionTile[1] = Tile1.getLayoutY();
                            });

                            // the tile is moved according to the movement of the mouse
                            Tile1.setOnMouseDragged(event -> {
                                Tile1.setLayoutX(event.getSceneX() - initialPosition[0]);
                                Tile1.setLayoutY(event.getSceneY() - initialPosition[1]);
                            });

                            Tile1.setOnMouseReleased(event -> {
                                index = 1;

                                int col = Math.min((int) ((event.getSceneX() - GridGriglia.getLayoutX()) / (GridGriglia.getWidth() / 5)), 4);
                                int row = Math.min((int) ((event.getSceneY() - GridGriglia.getLayoutY()) / (GridGriglia.getHeight() / 6)), 5);

                                if (col >= 0 && col < 5 && row >= 0 && row < 6) {
                                    //System.out.println("Tile " + 1 + " dropped at column " + col + ", row " + row);

                                    if(last_col ==col ||col_tick == -1 ){
                                        last_col = col;
                                        col_tick = col;
                                        this.lastTile[col] ++;
                                        if((this.lastTile[col] + tileCount-1) > 5){
                                            col_tick = -1;
                                        }
                                        guiUserInterface.getClient().SendTopUpMessage(guiUserInterface.getNickname(), col, 0 );}
                                }

                                // guiUserInterface.getClient().SendTopUpMessage(guiUserInterface.getNickname(), );
                                // return the tile to its initial position after drag
                                Tile1.setLayoutX(initialPositionTile[0]);
                                Tile1.setLayoutY(initialPositionTile[1]);
                            });
                            break;

                        case (1):
                            Tile2.setImage(getColoredImageTile(tiles[i]).getImage());
                            Tile2.setOnMousePressed(event -> {
                                initialPosition[0] = event.getX();
                                initialPosition[1] = event.getY();
                                initialPositionTile[0] = Tile2.getLayoutX();
                                initialPositionTile[1] = Tile2.getLayoutY();
                            });

                            Tile2.setOnMouseDragged(event -> {
                                Tile2.setLayoutX(event.getSceneX() - initialPosition[0]);
                                Tile2.setLayoutY(event.getSceneY() - initialPosition[1]);
                            });

                            Tile2.setOnMouseReleased(event -> {
                                index = 2;

                                int col = Math.min((int) ((event.getSceneX() - GridGriglia.getLayoutX()) / (GridGriglia.getWidth() / 5)), 4);
                                int row = Math.min((int) ((event.getSceneY() - GridGriglia.getLayoutY()) / (GridGriglia.getHeight() / 6)), 5);
                                pickedTile = 1;

                                if (col >= 0 && col < 5 && row >= 0 && row < 6) {
                                    if(last_col ==col ||col_tick == -1 ){
                                        last_col = col;
                                        col_tick = col;
                                        this.lastTile[col] ++;
                                        if((this.lastTile[col] + tileCount-1) > 5){
                                            col_tick = -1;
                                        }
                                        //System.out.println("Tile " + 1 + " dropped at column " + col + ", row " + row);
                                        guiUserInterface.getClient().SendTopUpMessage(guiUserInterface.getNickname(), col, 1 );
                                    }
                                }
                                Tile2.setLayoutX(initialPositionTile[0]);
                                Tile2.setLayoutY(initialPositionTile[1]);
                            });

                            break;
                        case (2):
                            Tile3.setImage(getColoredImageTile(tiles[i]).getImage());
                            Tile3.setOnMousePressed(event -> {
                                initialPosition[0] = event.getX();
                                initialPosition[1] = event.getY();
                                initialPositionTile[0] = Tile3.getLayoutX();
                                initialPositionTile[1] = Tile3.getLayoutY();
                            });

                            Tile3.setOnMouseDragged(event -> {
                                Tile3.setLayoutX(event.getSceneX() - initialPosition[0]);
                                Tile3.setLayoutY(event.getSceneY() - initialPosition[1]);
                            });

                            Tile3.setOnMouseReleased(event -> {
                                index = 3;
                                // Perform your logic here

                                int col = Math.min((int) ((event.getSceneX() - GridGriglia.getLayoutX()) / (GridGriglia.getWidth() / 5)), 4);
                                int row = Math.min((int) ((event.getSceneY() - GridGriglia.getLayoutY()) / (GridGriglia.getHeight() / 6)), 5);
                                pickedTile = 3;
                                if (col >= 0 && col < 5 && row >= 0 && row < 6) {
                                    if(last_col ==col || col_tick == -1 ){
                                        last_col = col;
                                        col_tick = col;
                                        this.lastTile[col] ++;
                                        if((this.lastTile[col] + tileCount-1) > 5){
                                            col_tick = -1;
                                        }
                                        //System.out.println("Tile " + 1 + " dropped at column " + col + ", row " + row);
                                        guiUserInterface.getClient().SendTopUpMessage(guiUserInterface.getNickname(), col, 2 );
                                    }

                                }
                                Tile3.setLayoutX(initialPositionTile[0]);
                                Tile3.setLayoutY(initialPositionTile[1]);
                            });
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        };


    /**
     * The method manages the movement of the tiles in the game
     */


    public void moveTiles(){
        int i = 0;
        col_tick = -1;
        for(Coordinates c : coordinates){
            ImageView tile = (ImageView) GrigliaBoard.getChildren().get(c.getX() * 9 + c.getY());
            ImageView tileCopy = new ImageView(tile.getImage());
            switch (i){
                case 0:

                    Tile1.setImage(tileCopy.getImage());
                    Tile1.setOnMousePressed(event -> {
                        initialPosition[0] = event.getX();
                        initialPosition[1] = event.getY();
                        initialPositionTile[0] = Tile1.getLayoutX();
                        initialPositionTile[1] = Tile1.getLayoutY();
                    });

                    // the tile is moved according to the movement of the mouse
                    Tile1.setOnMouseDragged(event -> {
                        Tile1.setLayoutX(event.getSceneX() - initialPosition[0]);
                        Tile1.setLayoutY(event.getSceneY() - initialPosition[1]);
                    });

                    Tile1.setOnMouseReleased(event -> {
                        index = 1;

                        int col = Math.min((int) ((event.getSceneX() - GridGriglia.getLayoutX()) / (GridGriglia.getWidth() / 5)), 4);
                        int row = Math.min((int) ((event.getSceneY() - GridGriglia.getLayoutY()) / (GridGriglia.getHeight() / 6)), 5);

                        if (col >= 0 && col < 5 && row >= 0 && row < 6) {
                            //System.out.println("Tile " + 1 + " dropped at column " + col + ", row " + row);

                            if(last_col ==col ||col_tick == -1 ){
                            last_col = col;
                            col_tick = col;
                            this.lastTile[col] ++;
                                if((this.lastTile[col] + tileCount-1) > 5){
                                    col_tick = -1;
                                }
                            guiUserInterface.getClient().SendTopUpMessage(guiUserInterface.getNickname(), col, 0 );}
                        }

                        // guiUserInterface.getClient().SendTopUpMessage(guiUserInterface.getNickname(), );
                        // return the tile to its initial position after drag
                        Tile1.setLayoutX(initialPositionTile[0]);
                        Tile1.setLayoutY(initialPositionTile[1]);
                    });
                    break;

                case 1:

                    Tile2.setImage(tileCopy.getImage());
                    Tile2.setOnMousePressed(event -> {
                        initialPosition[0] = event.getX();
                        initialPosition[1] = event.getY();
                        initialPositionTile[0] = Tile2.getLayoutX();
                        initialPositionTile[1] = Tile2.getLayoutY();
                    });

                    Tile2.setOnMouseDragged(event -> {
                        Tile2.setLayoutX(event.getSceneX() - initialPosition[0]);
                        Tile2.setLayoutY(event.getSceneY() - initialPosition[1]);
                    });

                    Tile2.setOnMouseReleased(event -> {
                        index = 2;

                                int col = Math.min((int) ((event.getSceneX() - GridGriglia.getLayoutX()) / (GridGriglia.getWidth() / 5)), 4);
                                int row = Math.min((int) ((event.getSceneY() - GridGriglia.getLayoutY()) / (GridGriglia.getHeight() / 6)), 5);
                                pickedTile = 1;
                                
                                if (col >= 0 && col < 5 && row >= 0 && row < 6) {
                                    if(last_col ==col ||col_tick == -1 ){
                                    last_col = col;
                                    col_tick = col;
                                    this.lastTile[col] ++;
                                        if((this.lastTile[col] + tileCount-1) > 5){
                                            col_tick = -1;
                                        }
                                    //System.out.println("Tile " + 1 + " dropped at column " + col + ", row " + row);
                                    guiUserInterface.getClient().SendTopUpMessage(guiUserInterface.getNickname(), col, 1 );
                                    }
                                }
                        Tile2.setLayoutX(initialPositionTile[0]);
                        Tile2.setLayoutY(initialPositionTile[1]);
                    });
                    break;

                case 2:


                    Tile3.setImage(tileCopy.getImage());
                    Tile3.setOnMousePressed(event -> {
                        initialPosition[0] = event.getX();
                        initialPosition[1] = event.getY();
                        initialPositionTile[0] = Tile3.getLayoutX();
                        initialPositionTile[1] = Tile3.getLayoutY();
                    });

                    Tile3.setOnMouseDragged(event -> {
                        Tile3.setLayoutX(event.getSceneX() - initialPosition[0]);
                        Tile3.setLayoutY(event.getSceneY() - initialPosition[1]);
                    });

                    Tile3.setOnMouseReleased(event -> {
                        index = 3;
                        // Perform your logic here

                        int col = Math.min((int) ((event.getSceneX() - GridGriglia.getLayoutX()) / (GridGriglia.getWidth() / 5)), 4);
                        int row = Math.min((int) ((event.getSceneY() - GridGriglia.getLayoutY()) / (GridGriglia.getHeight() / 6)), 5);
                        pickedTile = 3;
                        if (col >= 0 && col < 5 && row >= 0 && row < 6) {
                            if(last_col ==col || col_tick == -1 ){
                            last_col = col;
                            col_tick = col;
                            this.lastTile[col] ++;
                                if((this.lastTile[col] + tileCount-1) > 5){
                                    col_tick = -1;
                                }
                            //System.out.println("Tile " + 1 + " dropped at column " + col + ", row " + row);
                            guiUserInterface.getClient().SendTopUpMessage(guiUserInterface.getNickname(), col, 2 );
                        }

                        }
                        Tile3.setLayoutX(initialPositionTile[0]);
                        Tile3.setLayoutY(initialPositionTile[1]);
                    });
                    break;
                default:
                    break;
            }
            selectedTiles.remove(0);
            GrigliaBoard.getChildren().remove(selectedBorders.get(0));
            selectedBorders.remove(0);
            i++;
            tile.setImage(null);

            tile.setEffect(null);
        }
        if (firstboolean) {
            firstboolean = false;
            firstTile = coordinates.get(0);        }

        lastpick = coordinates.get(coordinates.size() - 1);
        coordinates.clear();
    }

    /**
     * The method is used to select or deselect a tile in the game grid.
     * @param column of the game board
     * @param row of the game board
     */
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
            tileCount = coordinates.size();

            // Apply visual effect to the selected tile
            tile.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.GREEN, 10, 0.5, 0, 0));
        }
    }

    /**
     * The method sends a pick message to the server, containing the nickname, the number of the selected tiles
     * and their coordinates
     * @param actionEvent button press
     */
    public void PickAction(ActionEvent actionEvent){
        if(coordinates.size() > 0){
            guiUserInterface.getClient().SendPickMessage(guiUserInterface.getNickname(), coordinates.size(), coordinates);
        }
        //centralController.getGuiUserInterface().getClient().SendPickMessage();
    }

    /**
     * The method updates the game grid with the images corresponding to the tiles present in the grid of the player
     * @param grid of the player
     */
    public void refreshGrid(String[][] grid){
        for(int i = 0; i < 5; i++){
            for(int j = 5; j >= 0; j--){
                if(grid[j][i].equals("N")){
                    lastTile[i] = 5 - j - 1;
                    System.out.println(lastTile[i]);
                    break;
                }
            }
        }
        String username = guiUserInterface.getNickname();
        tileCount = guiUserInterface.getClientView().getTilesview().get(username).length;
        Random random = new Random();
        double cellWidth = GridGriglia.getWidth() / 5;
        double cellHeight = GridGriglia.getHeight() / 6;
        for (int j = 0; j < 6; j++) {
            for (int k = 0; k < 5; k++) {
                int c = j * 5 + k;
                ImageView image = new ImageView();
                switch (grid[j][k]) {
                    case ("I"):
                        break;
                    case ("A"):
                        if (addedGridPositions.contains(c)) {
                            image = (ImageView) GridGriglia.getChildren().get(c);
                            image.setFitWidth(cellWidth);
                            image.setFitHeight(cellHeight);
                            image.setImage(azureImage[random.nextInt(3)].getImage());
                        }
                        break;
                    case ("P"):
                        if (addedGridPositions.contains(c)) {
                            image = (ImageView) GridGriglia.getChildren().get(c);
                            image.setFitWidth(cellWidth);
                            image.setFitHeight(cellHeight);
                            image.setImage(pinkImage[random.nextInt(3)].getImage());
                        }
                        break;
                    case ("G"):
                        if (addedGridPositions.contains(c)) {
                            image = (ImageView) GridGriglia.getChildren().get(c);
                            image.setFitWidth(cellWidth);
                            image.setFitHeight(cellHeight);
                            image.setImage(greenImage[random.nextInt(3)].getImage());
                        }
                        break;
                    case ("Y"):
                        if (addedGridPositions.contains(c)) {
                            image = (ImageView) GridGriglia.getChildren().get(c);
                            image.setFitWidth(cellWidth);
                            image.setFitHeight(cellHeight);
                            image.setImage(yellowImage[random.nextInt(3)].getImage());
                        }
                        break;
                    case ("W"):
                        if (addedGridPositions.contains(c)) {
                            image = (ImageView) GridGriglia.getChildren().get(c);
                            image.setFitWidth(cellWidth);
                            image.setFitHeight(cellHeight);
                            image.setImage(whiteImage[random.nextInt(3)].getImage());
                        }
                        break;
                    case ("B"):
                        if (addedGridPositions.contains(c)) {
                            image = (ImageView) GridGriglia.getChildren().get(c);
                            image.setFitWidth(cellWidth);
                            image.setFitHeight(cellHeight);
                            image.setImage(blueImage[random.nextInt(3)].getImage());
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * The method copies the image of a selected tile from its original position (Tile1, Tile2 or Tile3)
     * and places it in the player grid based on the last_col column and the row calculated by lastTile[last_col]
     * @param grid of the player
     */
    public void updateGrid(String[][] grid){
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 5; j++){
                System.out.print(grid[i][j]);
                }
            System.out.println();
            }

        ImageView tilecopy = null;
        switch (index){
            case 1:
                tilecopy = new ImageView(Tile1.getImage());
                Tile1.setImage(null);
                Tile1.setOnMouseClicked(null);
                Tile1.setOnMouseDragged(null);
                Tile1.setOnMouseReleased(null);
                break;
            case 2:
                 tilecopy = new ImageView(Tile2.getImage());
                    Tile2.setImage(null);
                    Tile2.setOnMouseClicked(null);
                    Tile2.setOnMouseDragged(null);
                    Tile2.setOnMouseReleased(null);

                break;
            case 3:
                tilecopy = new ImageView(Tile3.getImage());
                Tile3.setImage(null);
                Tile3.setOnMouseClicked(null);
                Tile3.setOnMouseDragged(null);
                Tile3.setOnMouseReleased(null);
                break;
            default:
                break;
        }
        tileCount--;
        tilecopy.setFitWidth(GridGriglia.getWidth()/5);
        tilecopy.setFitHeight(GridGriglia.getHeight()/6);
        if(this.lastTile[last_col]>5){
            this.lastTile[last_col] = 5;
        }
        GridGriglia.add(tilecopy, last_col, 5- this.lastTile[last_col]);
    }

    public void setGuiUserInterface(GuiUserInterface guiUserInterface) {
        this.guiUserInterface = guiUserInterface;
    }

    /**
     * The method is used to open the screen where it is possible to see other players' grids
     */
    public void SwitchToGrids(){
        centralController.showOtherGridsScene();
    }

    /**
     * The method is an event handler for the Quit button, it calls the quit() method to quit the application.
     * @param actionEvent button press
     */
    public void QuitAction(ActionEvent actionEvent) {
        quit();
    }

    /**
     * The method is used to open the chat screen
     * @param action button press
     */
    public void openChat(ActionEvent action){
        Platform.runLater(()->{
            centralController.showChatScene();
        });
    }

    /**
     * The method is called to close the application
     */
    public void quit(){
        Platform.exit();
        System.exit(0);
    }
}
