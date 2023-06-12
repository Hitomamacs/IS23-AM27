package org.project.Controller.Control;

public class InvalidLoginException extends Exception{

    public int identifier;
    public InvalidLoginException(String message, int identifier){

        super(message);
        this.identifier = identifier;
    }
    public int getIdentifier(){
        return identifier;
    }

}
