package org.project.Controller.View;

import org.project.Controller.Control.User;
import org.project.Model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//The virtual view class contains the various view classes which will be both common to the server and
//client however the virtual view class its self is just on the server, so a few methods do take model parts
//as input, while the common parts do not so that in the client there won't be any need to know how
//the model data structures are implemented
//hello
public class VirtualView {

    private BoardView boardView;
    private PointStackView pointStackView;
    private HashMap<String, GridView> gridViews;
    private HashMap<String, TilesView> tilesViews;
    private HashMap<String, PopUpView> popUpViews;
    private ScoreBoardView scoreBoardView;

    public VirtualView(List<User> users){
        boardView = new BoardView();
        pointStackView = new PointStackView();
        gridViews = new HashMap<>();
        tilesViews = new HashMap<>();
        popUpViews = new HashMap<>();

        int numPlayers = users.size();
        for (User user : users) {
            String username = user.getUsername();
            gridViews.put(username, new GridView(username));
            tilesViews.put(username, new TilesView(username));
            popUpViews.put(username, new PopUpView(username));
        }
        //Next line is repeated twice assuming common goals are always two, otherwise the number of common
        //goals is needed
        pointStackView.getPointList().add(8);
        pointStackView.getPointList().add(8);
        boardView.init(numPlayers);
    }
    //Next will be a series of overloaded update methods called from the various states that will
    //modify the virtual view as needed. Note this just modifies the virtual view data structure but
    //the actual process of updating the client view has to be done by the InputHandler class according
    //to the client type RMI/Socket

    //This first method updates popUpMessages
    public void updateView(String username, String popUpMessage){
        popUpViews.get(username).setErrorMessage(popUpMessage);
    }
    public void updateView(String popUpMessage){
        getPopUpViews().forEach((username, popUpView) ->popUpView.setErrorMessage(popUpMessage) );
    }
    //This method updates the players GridView and tilesView, so is needed in top up
    public void updateView(Tile[] pickedTiles, String username, int tileIndex, int column){
        Tile tile = pickedTiles[tileIndex];
        String tileStr = switch (tile.getColor()) {
            case YELLOW -> "Y";
            case WHITE -> "W";
            case GREEN -> "G";
            case BLUE -> "B";
            case AZURE -> "A";
            case PINK -> "P";
        };
        gridViews.get(username).updateGridView(tileStr, column);
        tilesViews.get(username).removeTile(tileIndex);
    }
    //This method updates the board by passing the current instance of the board to the class
    //the method than creates a simplified data structure representing the board which is then passed
    //to the updateBoardView method
    //The method will be called after in pick and refill state
    public void updateView(GameBoard board){
        String[][] newBoard = new String[9][9];
        Color color;
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(board.getBoard()[i][j].isOccupied()){
                    color = board.getBoard()[i][j].getTile().getColor();
                    switch (color) {
                        case YELLOW -> newBoard[i][j] = "Y";
                        case WHITE -> newBoard[i][j] = "W";
                        case GREEN -> newBoard[i][j] = "G";
                        case BLUE -> newBoard[i][j] = "B";
                        case AZURE -> newBoard[i][j] = "A";
                        case PINK -> newBoard[i][j] = "P";
                    }
                }
                else newBoard[i][j] = "N";
            }
        }
        boardView.updateBoardView(newBoard);
    }
    public void updateView(Tile[] pickedTiles, String username){
        String[] pickedTilesStr = new String[3];
        for(int i = 0; i < 3; i++) {
            if(pickedTiles[i] != null){
                pickedTilesStr[i] = switch (pickedTiles[i].getColor()) {
                    case YELLOW -> "Y";
                    case WHITE -> "W";
                    case GREEN -> "G";
                    case BLUE -> "B";
                    case AZURE -> "A";
                    case PINK -> "P";
                };
            }else pickedTilesStr[i] = "N";
        }
        tilesViews.get(username).updateTilesView(pickedTilesStr);
    }
    public void updateView(PointAssigner pointAssigner, int position){
        int newValue = pointAssigner.getStackList().get(position).peek();
        pointStackView.updatePointStackView(newValue, position);
    }
    public void updateView(HashMap<String, Integer> score){
        scoreBoardView.updateScoreBoardView(score);
    }
    public BoardView getBoardView() {
        return boardView;
    }

    public PointStackView getPointStackView() {
        return pointStackView;
    }

    public HashMap<String, GridView> getGridViews () {
        return gridViews;
    }

    public HashMap<String, TilesView> getTilesViews() {
        return tilesViews;
    }

    public HashMap<String, PopUpView> getPopUpViews() {
        return popUpViews;
    }

    public ScoreBoardView getScoreBoardView() {
        return scoreBoardView;
    }
}