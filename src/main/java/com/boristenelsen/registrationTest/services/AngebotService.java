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

    /*Angebote haben einen Status , entweder sind Sie offen oder geschlossen, falls Sie offen sind werden Sie als offene Angebote angezeigt,
     * falls geschlossen, dann werden Sie als Aktiver Auftrag angezeigt
     *
     * */
    @Autowired
    AngebotRepository angebotRepository;

    @Autowired
    PositionService positionService;

    @Autowired
    GehwegInformationService gehwegInformationService;

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
            angebot.setStatus("offen");


            angebotRepository.save(angebot);
        }
    }

    public Angebot loadAngebot(int bestellungId) {

        return angebotRepository.findByBestellungId(bestellungId);
    }

    public Angebot loadAngebot(String angebotId) {

        return angebotRepository.findByAngebotId(angebotId);
    }

    public List<AngebotDto> loadAlleOffenenAngebote() {
        List<Angebot> angebotList = angebotRepository.findAll();

        List<AngebotDto> angebotDtoList = new ArrayList<>();

        for (Angebot angebot : angebotList) {
            if (angebot.getStatus().equals("offen")) {
                ClientBestellung cb = gehwegInformationService.loadClientBestellungByID(angebot.getBestellungId());
                AngebotDto angebotDto = AngebotDto.builder()
                        .bestellungId(angebot.getBestellungId())
                        .gesamtPreis(angebot.getGesamtPreis())
                        .clientBestellung(cb)
                        .angebotId(angebot.getAngebotId())
                        .build();
                angebotDtoList.add(angebotDto);
            }
        }
        return angebotDtoList;
    }

    public List<Angebot> loadAllAngebote() {
        return angebotRepository.findAll();
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

    public void saveAngebot(Angebot angebot) {
        angebotRepository.save(angebot);
    }
}
