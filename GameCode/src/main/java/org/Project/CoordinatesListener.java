package org.Project;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class CoordinatesListener implements  PropertyChangeListener {
    private Game game;

    public CoordinatesListener(Game game){
        this.game = game;
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(((Player) evt.getSource()).getNickname() == game.getOrchestrator().getCurrentPlayer().getNickname()){
            if(game.getOrchestrator().getState() instanceof TopUpState){
                List<Coordinates> coordinates = (List<Coordinates>) evt.getNewValue();
                game.getOrchestrator().setPickedCoordinates(coordinates);
                game.getOrchestrator().executeState();
                //TODO add update view
            }
        }

    }
}
