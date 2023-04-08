package org.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class TopUpListener implements PropertyChangeListener {

    private Game game;

    public TopUpListener(Game game){
        this.game = game;

    }
    /*
    @Override
    //TODO just for the moment, need to switch with view class
    public void actionPerformed(ActionEvent e) {
        if(((Player) e.getSource()).getNickname() == game.getOrchestrator().getCurrentPlayer().getNickname()){
            if(game.getOrchestrator().getState() instanceof TopUpState){
                handler.handle();
            }
        }



    }

     */

    @Override
    public void propertyChange(PropertyChangeEvent evt) {


    }
}
