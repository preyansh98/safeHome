package com.pkaushik.safeHome.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(){
        super();
    }

    public UserNotFoundException(String message){
        super(message);
    }
}
