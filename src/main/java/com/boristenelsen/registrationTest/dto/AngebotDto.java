package com.boristenelsen.registrationTest.dto;

import com.boristenelsen.registrationTest.dao.Position;
import lombok.Data;

import java.util.List;

@Data
public class AngebotDto {


    private String angebotId;
    private List<Position> pickedPositions;
    private List<String> mengen;
    private double gesamtPreis;
    private String bestellungId;


}
