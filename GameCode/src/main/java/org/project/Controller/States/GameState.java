package org.project.Controller.States;


public interface GameState {

    int stateID = 0;
    public void execute();

    public void changeState();

}
