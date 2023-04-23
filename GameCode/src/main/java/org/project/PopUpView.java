package org.project;

public class PopUpView {
    private String ErrorMessage;
    private String username;

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

    public void setUsername(String username) {
        this.username = username;
    }
}
