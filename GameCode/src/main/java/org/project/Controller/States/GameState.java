package org.project.Controller.States;

import org.project.Controller.States.Exceptions.InvalidMoveException;

/**
 * The GameState interface manages game states, allowing you to perform specific actions for each state
 * and change the game state when needed.
 */
public interface GameState {

    int stateID = 0;
    public void execute() throws InvalidMoveException;

    public void changeState() throws InvalidMoveException;

    public int getStateID();



}
