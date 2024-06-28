package com.authSecurity.Exceptions;

public class UserAlreadyExistException extends Throwable {

    public UserAlreadyExistException(String massages){

          super(massages);
    }
}
