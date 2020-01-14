package com.boristenelsen.registrationTest.dto;

import com.boristenelsen.registrationTest.annotations.PasswordMatches;
import com.boristenelsen.registrationTest.annotations.ValidEmail;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserDto {

    @NotNull
    @NotEmpty
    private String vorname;

    @NotNull
    @NotEmpty
    private String nachname;

    @NotNull
    private long phonenumber;
    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;
    @NotNull
    @NotEmpty
    @PasswordMatches
    private String password;


    private List<String> role ;
}
