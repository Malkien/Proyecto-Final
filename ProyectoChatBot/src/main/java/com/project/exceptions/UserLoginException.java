package com.project.exceptions;

public class UserLoginException extends Exception{
    public UserLoginException(){
        super("User not found");
    }
}
