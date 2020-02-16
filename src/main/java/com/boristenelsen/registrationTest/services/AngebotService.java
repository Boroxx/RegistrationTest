package com.boristenelsen.registrationTest.services;

import com.boristenelsen.registrationTest.Exceptions.AngebotExistsAlreadyInDatabaseExcepetion;
import com.boristenelsen.registrationTest.dao.Angebot;
import com.boristenelsen.registrationTest.dao.Position;
import com.boristenelsen.registrationTest.dto.AngebotDto;
import com.boristenelsen.registrationTest.dto.AngebotTemplateDto;
import com.boristenelsen.registrationTest.dto.ClientBestellung;
import com.boristenelsen.registrationTest.repo.AngebotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AngebotService {

    @Autowired
    AngebotRepository angebotRepository;

    @Autowired
    PositionService positionService;

    @Transactional
    public void registerAngebot(AngebotDto angebotDto) throws AngebotExistsAlreadyInDatabaseExcepetion {

        Angebot angebot = angebotRepository.findByBestellungId(angebotDto.getBestellungId());

        if (angebot != null)
            throw new AngebotExistsAlreadyInDatabaseExcepetion("Angebot existiert schon mit BestellId " + angebotDto.getBestellungId() + " in Datenbank");
        else {

            angebot = new Angebot();
            String mengen = "";
            String pickedpositions = "";
            double preis = 0.00;


            for (Position position : angebotDto.getPickedPositions()) {
                mengen += position.getMenge() + ";";
                if (position.isChoosen()) {
                    pickedpositions += position.getOrdnungsnummer() + ";";
                    double temp = position.getMenge() * position.getPreis();
                    preis += temp;

                }

            }
            angebot.setPickedPositions(pickedpositions);
            angebot.setBestellungId(angebotDto.getBestellungId());
            angebot.setAllemengen(mengen);
            angebot.setGesamtPreis(preis);


            angebotRepository.save(angebot);
        }
    }

    public Angebot loadAngebot(int bestellungId) {

        return angebotRepository.findByBestellungId(bestellungId);
    }


    public AngebotTemplateDto createAngebotTemplate(ClientBestellung bestellung, List<Position> lv) {

        List<Position> template = new ArrayList<>();
        for (Position pos : lv) {

            if (pos.getOrdnungsnummer().equals("0000005")) {
                pos.setChoosen(true);
                pos.setMenge(Double.parseDouble((bestellung.getGehwegm2())));


            }
            if (pos.getOrdnungsnummer().equals("0000008")) {
                pos.setChoosen(true);
                double quadratmeter = (bestellung.getGehwegbreite() * 700) / 10000.0;
                double gesamt = quadratmeter * 0.7 * 0.67;
                pos.setMenge(gesamt);


            }
            if (pos.getOrdnungsnummer().equals("0000009")) {
                pos.setChoosen(true);
                double quadratmeter = (bestellung.getGehwegbreite() * 700) / 10000.0;
                double gesamt = quadratmeter * 0.7 * 0.33;
                pos.setMenge(gesamt);

            }
            if (pos.getOrdnungsnummer().equals("0000010")) {
                pos.setChoosen(true);
                double temp = Double.parseDouble(bestellung.getGehwegm2()) * 0.7;
                pos.setMenge(temp);

            }
            if (pos.getOrdnungsnummer().equals("0000011")) {
                pos.setChoosen(true);
                double temp = Double.parseDouble(bestellung.getGehwegm2()) * 0.7;
                pos.setMenge(temp);

            }
            if (pos.getOrdnungsnummer().equals("0000012")) {
                pos.setChoosen(true);
                double quadratmeter = (bestellung.getGehwegbreite() * 700) / 10000.0;
                double gesamt = quadratmeter * 0.2;

                pos.setMenge(gesamt);

            }
            if (pos.getOrdnungsnummer().equals("0000013")) {
                pos.setChoosen(true);
                pos.setMenge(7.00);
            }
            if (pos.getOrdnungsnummer().equals("0000014")) {
                pos.setChoosen(true);
                pos.setMenge(7.00);

            }
            if (pos.getOrdnungsnummer().equals("0000019")) {
                pos.setChoosen(true);
                pos.setMenge(Double.parseDouble((bestellung.getGehwegm2())));
            }
            if (pos.getOrdnungsnummer().equals("0000022")) {
                pos.setChoosen(true);
                double quadratmeter = (bestellung.getGehwegbreite() * 700) / 10000.0;
                double gesamt = quadratmeter * 0.2;


                pos.setMenge(gesamt);

            }
            if (pos.getOrdnungsnummer().equals("0000023")) {
                pos.setChoosen(true);
                pos.setMenge(7.00);

            }
            if (pos.getOrdnungsnummer().equals("0000027")) {
                pos.setChoosen(true);
                double quadratmeter = (bestellung.getGehwegbreite() * 700) / 10000.0;
                double gesamt = quadratmeter * 0.05;

                pos.setMenge(gesamt);

            }

            if (bestellung.getVorgarten().equals("ja")) {
                if (pos.getOrdnungsnummer().equals("0000025")) {
                    pos.setChoosen(true);

                }
                if (pos.getOrdnungsnummer().equals("0000020")) {
                    pos.setChoosen(true);

                }

            }

            if (bestellung.getHindernis().contains("Kappe")) {
                if (pos.getOrdnungsnummer().equals("0000007")) {
                    pos.setChoosen(true);

                }
                if (pos.getOrdnungsnummer().equals("0000023")) {
                    pos.setChoosen(true);

                }

            }


            template.add(pos);

        }
        AngebotTemplateDto angebotTemplateDto = new AngebotTemplateDto();
        angebotTemplateDto.setBestellungId(bestellung.getId());
        angebotTemplateDto.setPickedPositions(template);
        return angebotTemplateDto;


    }
}
