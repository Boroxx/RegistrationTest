package com.boristenelsen.registrationTest.dao;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Angebot {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",
            strategy = "uuid")
    private String angebotId;
    private int bestellungId;
    private double gesamtPreis;
    private String pickedPositions;
    private String allemengen;

}
