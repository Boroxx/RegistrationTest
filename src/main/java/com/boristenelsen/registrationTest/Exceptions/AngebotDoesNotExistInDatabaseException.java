package com.boristenelsen.registrationTest.Exceptions;

public class AngebotDoesNotExistInDatabaseException extends Throwable {
    public AngebotDoesNotExistInDatabaseException(String s) {
        super(s);
    }
}
