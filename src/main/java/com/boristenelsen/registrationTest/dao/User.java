package com.boristenelsen.registrationTest.dao;


import lombok.Data;

import javax.persistence.*;

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

    @Column(name="user")

    private String email;
    private String password;


    private String stadt_plz;
    private String strasse_hausnummer;

    private String role;


}
