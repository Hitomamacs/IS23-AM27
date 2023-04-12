package org.example;

import java.util.ArrayList;
import java.util.List;

public class VirtualView {

    BoardView boardView;
    PointStackView pointStackView;
    List<GridView> gridViews;
    List<TilesView> tilesViews;
    List<PopUpView> popUpViews;

    public VirtualView(List<User> users){
        boardView = new BoardView();
        pointStackView = new PointStackView();
        gridViews = new ArrayList<>();
        tilesViews = new ArrayList<>();
        popUpViews = new ArrayList<>();

        int numPlayers = users.size();
        for(int i = 0; i < numPlayers; i++){
            String username = users.get(i).getUsername();
            gridViews.add(new GridView(username));
            tilesViews.add(new TilesView(username));
            popUpViews.add(new PopUpView(username));
            pointStackView.getPointList().add(8);
            boardView.init(numPlayers);
        }
    }
    //Next will be a series of overloaded update methods called from the various states that will
    //modify the virtual view as needed. Note this just modifies the virtual view data structure but
    //the actual process of updating the client view has to be done by the InputHandler class according
    //to the client type RMI/Socket

    //This first method updates popUpMessages
    public void updateView(String username, String popUpMessage){
        for(PopUpView pView : popUpViews){
            if(pView.username.equals(username)){
                pView.ErrorMessage = popUpMessage;
                break;
            }
        }
    }
    //This method updates the players gridView
    public void updateView(String username, String tile, int column){
        for(GridView gView : gridViews){
            if(gView.username.equals(username)){
                gView.updateGridView(tile, column);
            }
        }
    }
    public void updateView(String username, )
}
