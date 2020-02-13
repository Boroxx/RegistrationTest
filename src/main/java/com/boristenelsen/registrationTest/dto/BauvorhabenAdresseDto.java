package com.boristenelsen.registrationTest.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class BauvorhabenAdresseDto {


    @NotNull
    @NotEmpty
    private String strasse_hausnummer;
    @NotNull
    @NotEmpty
    private String stadt_plz;


}
