package org.project.Model;

/**
 * The NotToRefillBoardExc class is a custom exception class that extends the Exception class.
 * It is used to handle a specific exception that occurs when the game board is not allowed to fill.
 */
public class NotToRefillBoardExc extends Exception{
    public NotToRefillBoardExc(String message){
        super(message);
    }
}

