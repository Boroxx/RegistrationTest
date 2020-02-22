package com.boristenelsen.registrationTest.services;


import com.boristenelsen.registrationTest.Exceptions.AngebotDoesNotExistInDatabaseException;
import com.boristenelsen.registrationTest.dao.Angebot;
import com.boristenelsen.registrationTest.dao.Auftrag;
import com.boristenelsen.registrationTest.dto.AuftragDto;
import com.boristenelsen.registrationTest.dto.ClientBestellung;
import com.boristenelsen.registrationTest.repo.AuftragRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuftragService {

    @Autowired
    private AngebotService angebotService;
    @Autowired
    private GehwegInformationService gehwegInformationService;

    @Autowired
    private AuftragRepository auftragRepository;

    @Transactional
    public void registerAngebot(String angebotId) throws AngebotDoesNotExistInDatabaseException {
        Angebot angebot = angebotService.loadAngebot(angebotId);
        if (angebot == null)
            throw new AngebotDoesNotExistInDatabaseException("Angebot existiert noch nicht in Datenbank");


        Auftrag auftrag = Auftrag.builder()
                .allemengen(angebot.getAllemengen())
                .bestellungId(angebot.getBestellungId())
                .pickedPositions(angebot.getPickedPositions())
                .gesamtPreis(angebot.getGesamtPreis())
                .build();
        auftragRepository.save(auftrag);


    }

    public List<AuftragDto> loadAuftraege() {
        List<Auftrag> auftragList = auftragRepository.findAll();
        List<AuftragDto> auftragDtoList = new ArrayList<>();

        for (Auftrag auftrag : auftragList) {
            ClientBestellung cb = gehwegInformationService.loadClientBestellungByID(auftrag.getBestellungId());
            AuftragDto auftragDto = AuftragDto.builder()
                    .allemengen(auftrag.getAllemengen())
                    .bestellungId(auftrag.getBestellungId())
                    .pickedPositions(auftrag.getPickedPositions())
                    .gesamtPreis(auftrag.getGesamtPreis())
                    .clientBestellung(cb)
                    .created(auftrag.getCreated())
                    .build();
            auftragDtoList.add(auftragDto);
        }


        return auftragDtoList;
    }
}
