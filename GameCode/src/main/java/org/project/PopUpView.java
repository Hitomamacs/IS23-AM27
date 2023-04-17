package org.project;

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

    public String getUsername(){ return username; };
}
