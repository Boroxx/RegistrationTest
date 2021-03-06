package com.boristenelsen.registrationTest.dao;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

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
    private String status;


    public double getGesamtPreis() {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.00", otherSymbols);
        String temp = df.format(this.gesamtPreis);

        return Double.parseDouble(temp);
    }

}
