package com.project.exceptions;

public class PasswordInvalidException extends Exception {
    public  PasswordInvalidException(){
        super("The password isn't valid");
    }
}
