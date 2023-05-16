package org.project.Controller.View;

import org.project.Controller.Control.Game;
import org.project.Controller.Control.User;
import org.project.Model.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//The virtual view class contains the various view classes which will be both common to the server and
//client however the virtual view class its self is just on the server, so a few methods do take model parts
//as input, while the common parts do not so that in the client there won't be any need to know how
//the model data structures are implemented
//hello
public class VirtualView {

    private Game game;
    private BoardView boardView;
    private PointStackView pointStackView;
    private HashMap<String, GridView> gridViews;
    private HashMap<String, TilesView> tilesViews;
    private ScoreBoardView scoreBoardView;
    private HashMap<String, PGoalView> personalGoalViews;
    private ArrayList<CGoalView> commonGoalView;



    public VirtualView(List<User> users, Game game){
        this.game = game;
        boardView = new BoardView();
        pointStackView = new PointStackView();
        gridViews = new HashMap<>();
        tilesViews = new HashMap<>();
        personalGoalViews=new HashMap<>();
        commonGoalView=new ArrayList<>();

        int numPlayers = users.size();
        for (User user : users) {
            String username = user.getUsername();
            gridViews.put(username, new GridView(username));
            tilesViews.put(username, new TilesView(username));
        }
        //Next line is repeated twice assuming common goals are always two, otherwise the number of common
        //goals is needed
        pointStackView.getPointList().add(8);
        pointStackView.getPointList().add(8);
        boardView.init(numPlayers);
    }
    //This method updates the players GridView and tilesView, so is needed in top up
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
    public ScoreBoardView getScoreBoardView() {
        return scoreBoardView;
    }
    public PropertyChangeListener getBoardUpdateListener(){
        return this.boardUpdateListener;
    }
    public PropertyChangeListener getGridUpdateListener(){
        return this.gridUpdateListener;
    }
    public PropertyChangeListener getTilesUpdateListener(){
        return this.tilesUpdateListener;
    }
    PropertyChangeListener boardUpdateListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if("boardUpdate".equals(evt.getPropertyName())){
                GameBoard board = (GameBoard) evt.getNewValue();
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
        }
    };
    PropertyChangeListener gridUpdateListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if("gridUpdate".equals(evt.getPropertyName())){
                Player player = (Player)evt.getNewValue();
                PlayerGrid grid = player.getPlayerGrid();

                String[][] gridStr = new String[6][5];
                Color color;
                Coordinates coordinates;
                for(int i = 0; i < 6; i++){
                    for(int j = 0; j < 5; j++){
                        coordinates = new Coordinates(i, j);
                        if(grid.getSpot(coordinates).isOccupied()){
                            color = grid.getSpot(coordinates).getTile().getColor();
                            switch (color) {
                                case YELLOW -> gridStr[i][j] = "Y";
                                case WHITE -> gridStr[i][j] = "W";
                                case GREEN -> gridStr[i][j] = "G";
                                case BLUE -> gridStr[i][j] = "B";
                                case AZURE -> gridStr[i][j] = "A";
                                case PINK -> gridStr[i][j] = "P";
                            }
                        }
                        else gridStr[i][j] = "N";
                    }
                }
                gridViews.get(player.getNickname()).updateGridView(gridStr);
            }
        }
    };
    PropertyChangeListener tilesUpdateListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if ("tilesUpdate".equals(evt.getPropertyName())) {
                Player player = (Player) evt.getNewValue();
                Tile[] tiles = player.getPickedTiles();

                String[] tilesStr = new String[3];
                Color color;
                for (int i = 0; i < 3; i++) {
                    if (tiles[i] != null) {
                        color = tiles[i].getColor();
                        switch (color) {
                            case YELLOW -> tilesStr[i] = "Y";
                            case WHITE -> tilesStr[i] = "W";
                            case GREEN -> tilesStr[i] = "G";
                            case BLUE -> tilesStr[i] = "B";
                            case AZURE -> tilesStr[i] = "A";
                            case PINK -> tilesStr[i] = "P";
                        }
                    } else tilesStr[i] = "N";
                }
                tilesViews.get(player.getNickname()).updateTilesView(tilesStr);
            }
        }

    };

    public HashMap<String, PGoalView> getPersonalGoalViews() {
        return personalGoalViews;
    }

    public void setPersonalGoalViews(HashMap<String, PGoalView> personalGoalViews) {
        this.personalGoalViews = personalGoalViews;
    }

    public ArrayList<CGoalView> getCommonGoalView() {
        return commonGoalView;
    }

    public void setCommonGoalView(ArrayList<CGoalView> commonGoalView) {
        this.commonGoalView = commonGoalView;
    }
}