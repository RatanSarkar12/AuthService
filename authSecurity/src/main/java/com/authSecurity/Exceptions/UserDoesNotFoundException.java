package com.authSecurity.Exceptions;

public class UserDoesNotFoundException extends Exception{
    public UserDoesNotFoundException(String massage){
        super(massage);
    }
}
