package com.boristenelsen.registrationTest.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Auftrag {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",
            strategy = "uuid")
    private String auftragId;
    private int bestellungId;
    private double gesamtPreis;
    private String pickedPositions;
    private String allemengen;
    private Date created;


    @PrePersist
    protected void onCreate() {
        created = new Date();
    }


    public double getGesamtPreis() {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.00", otherSymbols);
        String temp = df.format(this.gesamtPreis);

        return Double.parseDouble(temp);
    }
}
