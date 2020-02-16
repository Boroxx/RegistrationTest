package com.boristenelsen.registrationTest.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Position {


    @Id
    private String ordnungsnummer;
    private String einheit;
    private String kurztext;

    private double preis;
    private double menge;
    private boolean choosen;


    public double getPreis() {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.00", otherSymbols);

        String temp = df.format(this.preis);
        return Double.parseDouble(temp);
    }

    public void setPreis(double preis) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.00", otherSymbols);

        String temp = df.format(preis);
        this.preis = Double.parseDouble(temp);

    }

    public double getMenge() {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.00", otherSymbols);

        String temp = df.format(this.menge);
        return Double.parseDouble(temp);
    }

    public void setMenge(double menge) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.00", otherSymbols);

        String temp = df.format(menge);
        this.menge = Double.parseDouble(temp);

    }
}
