package org.project.Gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.project.ClientView;
import org.project.Model.Coordinates;

import java.util.*;

public class MainSceneController {
    private GuiUserInterface guiUserInterface;
    private GuiController guiController;

    private Coordinates lastpick;

    private double[] initialPosition = new double[2];

    private double[] initialPositionTile = new double[2];

    private int pickedTile = 0;

    private int[] lastTile = new int[]{-1,-1,-1,-1,-1};
    
    private int last_col = -1;

    private boolean firstboolean = true;

    private Set<Integer> addedPositions = new HashSet<>();

    //riferimento alla ClientView



    @FXML
    private GridPane GridGriglia;

    @FXML
    private GridPane GrigliaBoard;
    @FXML
    private Label Status;
    @FXML
    private Button Pick;
    
    private int index = 1;

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

    private Coordinates firstTile = new Coordinates(8,8);

    private HashMap<Integer, ImageView> helper_board = new HashMap<>();

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

    private GuiFx centralController;
    public void setCentralController(GuiFx controller){
        this.centralController = controller;
    }

    public Label getStatus(){
        return this.Status;
    }
    public void init(){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                //Coordinates c = new Coordinates(i, j);
                ImageView image = new ImageView();
                image.setFitHeight(GrigliaBoard.getHeight() / 9);
                image.setFitWidth(GrigliaBoard.getWidth() / 9);
                helper_board.put(((int) i * 9 + j), image);
                GrigliaBoard.add(image, j, i);
            }
        }
        //decidedCommonGoals();
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
            decidedCommonGoals();
            });
        //decidedCommonGoals();

    }


    public void decidedCommonGoals(){
        CGoalCard1.setImage(commonGoal[guiUserInterface.getClientView().getCommonGoalView().get(0)-1].getImage());
        CommonGoal2.setImage(commonGoal[guiUserInterface.getClientView().getCommonGoalView().get(1)-1].getImage());
    }

    public void moveTiles(){
        int i = 0;
        last_col = -1;
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

                    Tile1.setOnMouseDragged(event -> {
                        Tile1.setLayoutX(event.getSceneX() - initialPosition[0]);
                        Tile1.setLayoutY(event.getSceneY() - initialPosition[1]);
                    });

                    Tile1.setOnMouseReleased(event -> {
                        index = 1;
                        // Perform your logic here

                        int col = Math.min((int) ((event.getSceneX() - GridGriglia.getLayoutX()) / (GridGriglia.getWidth() / 5)), 4);
                        int row = Math.min((int) ((event.getSceneY() - GridGriglia.getLayoutY()) / (GridGriglia.getHeight() / 6)), 5);

                        if (col >= 0 && col < 5 && row >= 0 && row < 6) {
                            //System.out.println("Tile " + 1 + " dropped at column " + col + ", row " + row);

                            if(last_col ==col ||last_col == -1 ){
                            last_col = col;
                            this.lastTile[col] ++;
                                if(this.lastTile[col] + coordinates.size()-1 >= 5){
                                    last_col = -1;
                                }
                            guiUserInterface.getClient().SendTopUpMessage(guiUserInterface.getNickname(), col, 0 );}

                        }

                        //guiUserInterface.getClient().SendTopUpMessage(guiUserInterface.getNickname(), );

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
                                // Perform your logic here

                                int col = Math.min((int) ((event.getSceneX() - GridGriglia.getLayoutX()) / (GridGriglia.getWidth() / 5)), 4);
                                int row = Math.min((int) ((event.getSceneY() - GridGriglia.getLayoutY()) / (GridGriglia.getHeight() / 6)), 5);
                                pickedTile = 1;
                                
                                if (col >= 0 && col < 5 && row >= 0 && row < 6) {
                                    if(last_col ==col ||last_col == -1 ){
                                    last_col = col;
                                    this.lastTile[col] ++;
                                        if(this.lastTile[col] + coordinates.size()-1 >= 5){
                                            last_col = -1;
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
                            if(last_col ==col || last_col == -1 ){
                            last_col = col;
                            this.lastTile[col] ++;
                                if(this.lastTile[col] + coordinates.size()-1 >= 5){
                                    last_col = -1;
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
                break;
            case 2:
                 tilecopy = new ImageView(Tile2.getImage());
                    Tile2.setImage(null);
                break;
            case 3:
                tilecopy = new ImageView(Tile3.getImage());
                Tile3.setImage(null);
                break;
            default:
                break;
                
        }
        tilecopy.setFitWidth(GrigliaBoard.getWidth()/5);
        tilecopy.setFitHeight(GrigliaBoard.getHeight()/6);
        GridGriglia.add(tilecopy, last_col, 5- this.lastTile[last_col]);
        
        
        
    }


    public void setGuiUserInterface(GuiUserInterface guiUserInterface) {
        this.guiUserInterface = guiUserInterface;
    }

    public void setGuiController(GuiController guiController) {
        this.guiController = guiController;
    }
    public void SwitchToGrids(){
        centralController.showOtherGridsScene();
    }

    public void QuitAction(ActionEvent actionEvent) {
        quit();


    }



    public void quit(){
        guiUserInterface.getClient().SendQuitMessage(guiUserInterface.getNickname());
        //guiUserInterface.getClient().close();
        Platform.exit();
        System.exit(0);


    }

}
