package com.boristenelsen.registrationTest.dao;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class GehwegInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;

    private String hindernis;
    private String vorgarten;
    private String username;
    private String anmerkung;
    private int gehwegbreite;
    private int plattenlaenge;
    private int plattenbreite;
    private boolean status;
    private String genehmigungDownload;
    @Column(length = 65535, columnDefinition = "text")
    private String gehwegBilderListe;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "bauvorhaben_id")
    private BauvorhabenAdresse bauvorhabenAdresse;

}
