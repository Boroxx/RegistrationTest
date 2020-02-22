package com.boristenelsen.registrationTest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuftragDto {

    private String auftragId;
    private int bestellungId;
    private double gesamtPreis;
    private String pickedPositions;
    private String allemengen;
    private Date created;
    private ClientBestellung clientBestellung;


}
