package org.example;

public class PopUpView {
    String ErrorMessage;
    String username;

    public PopUpView(String username){
        this.username = username;
    }
    public void setErrorMessage(String errorMessage){
        this.ErrorMessage = errorMessage;
    }
    public String getErrorMessage(){
        return ErrorMessage;
    }
}
