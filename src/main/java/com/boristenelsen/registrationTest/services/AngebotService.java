package com.boristenelsen.registrationTest.services;

import com.boristenelsen.registrationTest.dao.Angebot;
import com.boristenelsen.registrationTest.dao.Position;
import com.boristenelsen.registrationTest.dto.ClientBestellung;
import com.boristenelsen.registrationTest.repo.AngebotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AngebotService {

    @Autowired
    AngebotRepository angebotRepository;

    @Autowired
    PositionService positionService;

    public void registerAngebot(int bestellungId) {
        String mengen = "";
        Angebot angebot = new Angebot();

        List<Position> positions = positionService.getAllPositionen();
        List<Position> choosedPositions = new ArrayList<>();

        String pickedpositions = "";
        double preis = 0.00;
        for (Position position : positions) {
            if (position.isChoosen()) {
                pickedpositions += position.getOrdnungsnummer() + ";";
                mengen += position.getMenge() + ";";
                double temp = position.getMenge() * position.getPreis();
                preis += temp;
            }
        }
        angebot.setPickedPositions(pickedpositions);
        angebot.setBestellungId(bestellungId);
        angebot.setAllemengen(mengen);
        angebot.setGesamtPreis(preis);

        angebotRepository.save(angebot);


    }

    public Angebot loadAngebot() {

        return new Angebot();
    }


    public List<Position> setUpChosenPositions(ClientBestellung bestellung) {

        Position pos;


        double quadratmeter;
        double gesamt;

        for (Position position : positionService.getAllPositionen()) {
            position.setChoosen(false);
            position.setMenge(1.00);
            positionService.savePosition(position);

        }


        //platten
        pos = positionService.findPositionByOrdnungsnummer("0000005");
        pos.setChoosen(true);
        pos.setMenge(Double.parseDouble((bestellung.getGehwegm2())));
        positionService.savePosition(pos);

        //Boden ausheben

        pos = positionService.findPositionByOrdnungsnummer("0000008");
        pos.setChoosen(true);

        quadratmeter = (bestellung.getGehwegbreite() * 700) / 10000.0;
        gesamt = quadratmeter * 0.7 * 0.67;
        pos.setMenge(gesamt);
        positionService.savePosition(pos);

        //Boden von hand ausheben
        pos = positionService.findPositionByOrdnungsnummer("0000009");
        pos.setChoosen(true);
        quadratmeter = (bestellung.getGehwegbreite() * 700) / 10000.0;
        gesamt = quadratmeter * 0.7 * 0.33;
        pos.setMenge(gesamt);
        positionService.savePosition(pos);

        //Boden laden abfahren
        pos = positionService.findPositionByOrdnungsnummer("0000010");
        pos.setChoosen(true);
        double temp = Double.parseDouble(bestellung.getGehwegm2()) * 0.7;
        pos.setMenge(temp);
        positionService.savePosition(pos);

        //Entsorgungsgebühr
        pos = positionService.findPositionByOrdnungsnummer("0000011");
        pos.setChoosen(true);
        temp = Double.parseDouble(bestellung.getGehwegm2()) * 0.7;
        pos.setMenge(temp);
        positionService.savePosition(pos);

        pos = positionService.findPositionByOrdnungsnummer("0000012");
        pos.setChoosen(true);
        quadratmeter = (bestellung.getGehwegbreite() * 700) / 10000.0;
        gesamt = quadratmeter * 0.2;

        pos.setMenge(gesamt);
        positionService.savePosition(pos);

        pos = positionService.findPositionByOrdnungsnummer("0000013");
        pos.setChoosen(true);
        pos.setMenge(7.00);
        positionService.savePosition(pos);

        pos = positionService.findPositionByOrdnungsnummer("0000014");
        pos.setChoosen(true);
        pos.setMenge(7.00);
        positionService.savePosition(pos);

        pos = positionService.findPositionByOrdnungsnummer("0000019");
        pos.setChoosen(true);
        pos.setMenge(Double.parseDouble((bestellung.getGehwegm2())));
        positionService.savePosition(pos);

        pos = positionService.findPositionByOrdnungsnummer("0000022");
        pos.setChoosen(true);
        quadratmeter = (bestellung.getGehwegbreite() * 700) / 10000.0;
        gesamt = quadratmeter * 0.2;

        pos.setMenge(gesamt);
        positionService.savePosition(pos);

        pos = positionService.findPositionByOrdnungsnummer("0000022");
        pos.setChoosen(true);
        pos.setMenge(7.00);
        positionService.savePosition(pos);

        pos = positionService.findPositionByOrdnungsnummer("0000027");
        pos.setChoosen(true);
        quadratmeter = (bestellung.getGehwegbreite() * 700) / 10000.0;
        gesamt = quadratmeter * 0.05;

        pos.setMenge(gesamt);
        positionService.savePosition(pos);


        if (bestellung.getVorgarten().equals("ja")) {
            Position po = positionService.findPositionByOrdnungsnummer("0000025");
            po.setChoosen(true);
            positionService.savePosition(po);

            po = positionService.findPositionByOrdnungsnummer("0000020");//Randstein liefern
            po.setChoosen(true);
            positionService.savePosition(po);

        }
        if (bestellung.getHindernis().contains("Kappe")) {
            System.out.println("BLAA");
            Position po = positionService.findPositionByOrdnungsnummer("0000007");
            po.setChoosen(true);
            positionService.savePosition(po);
            po = positionService.findPositionByOrdnungsnummer("0000023");//Randstein liefern
            po.setChoosen(true);
            positionService.savePosition(po);


        }

        return positionService.getAllPositionen();


    }
}
