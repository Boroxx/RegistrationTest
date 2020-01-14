package com.boristenelsen.registrationTest.dao;


import com.boristenelsen.registrationTest.annotations.PasswordMatches;
import com.boristenelsen.registrationTest.annotations.ValidEmail;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int ID;
    private String vorname;
    private String nachname;
    private long phonenumber;
    private String email;
    private String password;

    @ManyToMany
    private List<Role> roles;


}
