package com.boristenelsen.registrationTest.Exceptions;

public class EmailExistsException extends Exception {

    public EmailExistsException(String error){
        super(error);

    }
}
