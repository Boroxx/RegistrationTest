package com.boristenelsen.registrationTest.Exceptions;

public class UsernameNotFoundException extends Exception {

    public UsernameNotFoundException(String error){
        super(error);
    }
}
