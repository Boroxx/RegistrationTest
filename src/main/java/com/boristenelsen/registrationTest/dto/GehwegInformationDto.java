package com.boristenelsen.registrationTest.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class GehwegInformationDto {


    private String vorgarten;
    private List<String> hindernis;
    private String username;
    private String anmerkung;


    @NotNull
    @NotEmpty
    private int gehwegbreite;
    @NotEmpty
    @NotNull
    private int plattenlaenge;
    @NotEmpty
    @NotNull
    private int plattenbreite;


    @NotNull
    private MultipartFile genehmigung;

    @NotNull
    private MultipartFile[] gehwegbilder;

}
