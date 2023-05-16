package org.project.Controller.States;


import org.project.Controller.States.Exceptions.InvalidMoveException;

public interface GameState {

    int stateID = 0;
    public void execute() throws InvalidMoveException;

    public void changeState() throws InvalidMoveException;

}
