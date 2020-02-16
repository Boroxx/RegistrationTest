package com.boristenelsen.registrationTest.dto;

import com.boristenelsen.registrationTest.dao.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AngebotTemplateDto {


    private String angebotId;
    private List<Position> pickedPositions;
    private List<String> mengen;
    private double gesamtPreis;
    private int bestellungId;
}
