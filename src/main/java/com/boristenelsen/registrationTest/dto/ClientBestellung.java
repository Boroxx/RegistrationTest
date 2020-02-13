package com.boristenelsen.registrationTest.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)

@Data
public class ClientBestellung {


    private int id;
    private String email;
    private String gehwegm2;
    private String name;
    private long telefon;
    private String preis;
    private boolean status;
    private String strasse_hausnummer;
    private String stadt_plz;

    private String vorgarten;
    private String hindernis;
    private String anmerkung;
    private int gehwegbreite;
    private int plattenlaenge;
    private int plattenbreite;
    private String genehmigung;
    private String genehmigungsID;
    private List<String> gehwegBilderListe;
}
