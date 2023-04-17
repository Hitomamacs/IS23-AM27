package org.project;


public interface GameState {

    int stateID = 0;
    public void execute();

    public void changeState();

}
