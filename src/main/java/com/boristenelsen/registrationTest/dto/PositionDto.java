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


public class PositionDto {

    private List<Position> chosenPositions;
    private double gesamtPreis;
}
