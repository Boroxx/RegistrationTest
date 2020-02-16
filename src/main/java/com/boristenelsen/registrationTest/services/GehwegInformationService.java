package com.boristenelsen.registrationTest.services;


import com.boristenelsen.registrationTest.dao.BauvorhabenAdresse;
import com.boristenelsen.registrationTest.dao.GehwegInformation;
import com.boristenelsen.registrationTest.dao.User;
import com.boristenelsen.registrationTest.dto.BauvorhabenAdresseDto;
import com.boristenelsen.registrationTest.dto.ClientBestellung;
import com.boristenelsen.registrationTest.dto.GehwegInformationDto;
import com.boristenelsen.registrationTest.repo.BauvorhabenAdresseRepository;
import com.boristenelsen.registrationTest.repo.GehwegInformationRepsoitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class GehwegInformationService {

    @Autowired
    FileStorageService fileStorageService;
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private GehwegInformationRepsoitory gehwegInformationRepsoitory;
    @Autowired
    private BauvorhabenAdresseRepository bauvorhabenAdresseRepository;
    @Autowired
    private UserService userService;
    private BauvorhabenAdresse bauvorhabenAdresse;


    public void registerBauvorhaben(BauvorhabenAdresseDto bvaDto) {
        bauvorhabenAdresse = new BauvorhabenAdresse();
        bauvorhabenAdresse.setStadt_plz(bvaDto.getStadt_plz());
        bauvorhabenAdresse.setStrasse_hausnummer(bvaDto.getStrasse_hausnummer());


    }

    /*Registriert Bauvorhaben in datenbank mithilfe von GehwegInformation und BauvorhabenAdresse*/
    public GehwegInformation registerGehwegInformation(GehwegInformationDto gehwegInformationDto) {

        UUID uuid = UUID.randomUUID();
        String name = userService.getUserName(gehwegInformationDto.getUsername());

        //DAO SETUP
        GehwegInformation gehwegInformation = new GehwegInformation();
        gehwegInformation.setFullName(name);
        gehwegInformation.setUsername(gehwegInformationDto.getUsername());
        gehwegInformation.setVorgarten(gehwegInformationDto.getVorgarten());
        gehwegInformation.setHindernis(concatHindernisse(gehwegInformationDto));
        gehwegInformation.setAnmerkung(gehwegInformationDto.getAnmerkung());
        gehwegInformation.setGehwegbreite(gehwegInformationDto.getGehwegbreite());
        gehwegInformation.setPlattenlaenge(gehwegInformationDto.getPlattenlaenge());
        gehwegInformation.setPlattenbreite(gehwegInformationDto.getPlattenbreite());
        gehwegInformation.setStatus(false);


        //Enthält Bauvorhabenadressdaten und speichert diese im Table der Gehweginformationen
        gehwegInformation.setBauvorhabenAdresse(bauvorhabenAdresse);

        //Speichert file im Filesystem
        String filename = fileStorageService.storeFile(gehwegInformationDto.getGenehmigung(), uuid.toString());
        String downloadUri = fileStorageService.getDownloadUri(filename, uuid.toString());
        gehwegInformation.setGenehmigungDownload(downloadUri);

        String downloadLinksForDatabase = "";
        for (MultipartFile image : gehwegInformationDto.getGehwegbilder()) {
            System.out.println(image.getOriginalFilename());

            String filenametemp = fileStorageService.storeFile(image, uuid.toString());
            String viewUrl = fileStorageService.getDownloadUri(filenametemp, uuid.toString());
            downloadLinksForDatabase += viewUrl + ";";
            gehwegInformation.setGehwegBilderListe(downloadLinksForDatabase);
        }


        gehwegInformationRepsoitory.save(gehwegInformation);

        return gehwegInformation;
    }

    public String concatHindernisse(GehwegInformationDto gIDto) {
        String result = "";
        for (String g : gIDto.getHindernis()) {
            if (g != null) result = result + " " + g;

        }
        return result;

    }

    /*Stellt ClientBestellung zusammen*/
    public ClientBestellung loadClientBestellungByID(int id) {

        ClientBestellung clientBestellung = new ClientBestellung();
        GehwegInformation gehwegInformation = gehwegInformationRepsoitory.findByID(id);

        User user = userService.getUserByEmail(gehwegInformation.getUsername());
        List<String> imagelinks = Arrays.asList(gehwegInformation.getGehwegBilderListe().split(";"));

        BauvorhabenAdresse bauvorhabenAdresse = gehwegInformation.getBauvorhabenAdresse();

        clientBestellung.setName(gehwegInformation.getFullName())
                .setTelefon(user.getPhonenumber())
                .setId(gehwegInformation.getID())
                .setGenehmigung(gehwegInformation.getGenehmigungDownload())
                .setPreis(preis(gehwegInformation.getGehwegbreite() * 700))
                .setGehwegm2(convertCmIntoM2(gehwegInformation.getGehwegbreite() * 700))
                .setEmail(gehwegInformation.getUsername())
                .setStadt_plz(bauvorhabenAdresse.getStadt_plz())
                .setStatus(gehwegInformation.isStatus())
                .setStrasse_hausnummer(bauvorhabenAdresse.getStrasse_hausnummer())
                .setVorgarten(gehwegInformation.getVorgarten())
                .setHindernis(gehwegInformation.getHindernis())
                .setGehwegBilderListe(imagelinks)
                .setGehwegbreite(gehwegInformation.getGehwegbreite())
                .setPlattenbreite(gehwegInformation.getPlattenbreite())
                .setPlattenlaenge(gehwegInformation.getPlattenlaenge());

        String anmerkung;
        if (gehwegInformation.getAnmerkung() == null) {
            anmerkung = "keine";
            clientBestellung.setAnmerkung(anmerkung);
        } else {
            clientBestellung.setAnmerkung(gehwegInformation.getAnmerkung());
        }


        return clientBestellung;

    }

    public List<GehwegInformation> getGehWegInformationObjects(String email) {
        return gehwegInformationRepsoitory.findAllByUsername(email);
    }

    public GehwegInformation getGehWegInformationObject(int id) {
        return gehwegInformationRepsoitory.findByID(id);
    }

    /*Erstell eine ClientBestellungsListe aus der User-Datenbank und der Gehwegeinformation- Datenbank*/
    public List<ClientBestellung> getAllAnfragen() {

        List<ClientBestellung> clientBestellungen = new ArrayList<>();
        List<GehwegInformation> list = gehwegInformationRepsoitory.findAll();

        for (GehwegInformation info : list) {
            ClientBestellung cb = loadClientBestellungByID(info.getID());

            clientBestellungen.add(cb);
        }
        return clientBestellungen;
    }

    public boolean hasBauvorhabenWithLoggedInUsernameAlready(String email) {
        GehwegInformation gehwegInformation = null;

        gehwegInformation = gehwegInformationRepsoitory.findByUsername(email);
        return gehwegInformation != null;
    }

    private String convertCmIntoM2(int zentimeter) {
        String zahl = String.valueOf(zentimeter);
        double temp = Double.parseDouble(zahl);
        temp = temp / 10000;

        return String.valueOf(temp);

    }

    private String preis(int zentimeter) {

        String zahl = String.valueOf(zentimeter);
        double temp = Double.parseDouble(zahl);
        temp = temp / 10000;

        double preis = temp * 240;

        return String.valueOf(preis);

    }

    @Transactional
    public int changeStatus(int id) {
        GehwegInformation gehwegInformation = gehwegInformationRepsoitory.findByID(id);
        gehwegInformation.setStatus(true);
        gehwegInformationRepsoitory.save(gehwegInformation);
        return 1;

    }
}
