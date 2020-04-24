package com.project.exceptions;

public class UsernameLengthException extends Exception{
    public UsernameLengthException(){
        super("Username's length error");
    }
}
