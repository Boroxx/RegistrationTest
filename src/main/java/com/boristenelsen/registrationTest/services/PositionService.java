package com.boristenelsen.registrationTest.services;


import com.boristenelsen.registrationTest.dao.Position;
import com.boristenelsen.registrationTest.repo.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class PositionService {


    @Autowired
    PositionRepository positionRepository;

    @Autowired
    ResourceLoader resourceLoader;


    private List<Position> setupPositionList() {
        List<Position> positionlist = new ArrayList<>();

        //File file = new File("static/gehwegabsenkung_positions.txt");
        String line = "";

        try {


            InputStream inputStream = new ClassPathResource("static/gehwegabsenkung_positions.txt").getInputStream();


            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 3) {
                    Position position = new Position();
                    System.out.println(data[0]);
                    System.out.println(data[1]);
                    System.out.println(data[2]);
                    position.setOrdnungsnummer(data[0]);
                    position.setEinheit(data[1]);
                    position.setKurztext(data[2]);
                    position.setMenge(1.0);
                    position.setChoosen(false);

                    positionlist.add(position);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return positionlist;


    }

    public Position findPositionByOrdnungsnummer(String ordnungsnummer) {
        return positionRepository.findByOrdnungsnummer(ordnungsnummer);
    }


    public void registerPositions() {

        List<Position> positions = setupPositionList();

        for (Position position : positions) {
            positionRepository.save(position);
        }


    }

    public List<Position> getAllPositionen() {
        return positionRepository.findAll();
    }

    public void storePosition(Position position, Double preis) {

        position.setPreis(preis);
        positionRepository.save(position);

    }

    public void savePosition(Position po) {
        positionRepository.save(po);
    }
}
