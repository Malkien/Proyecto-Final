package com.project.exceptions;

public class PasswordNotMatchException extends Exception {
    public PasswordNotMatchException(){
        super("Password not match");
    }
}
