package com.boristenelsen.registrationTest.controllers;


import com.boristenelsen.registrationTest.Exceptions.AngebotDoesNotExistInDatabaseException;
import com.boristenelsen.registrationTest.dao.Angebot;
import com.boristenelsen.registrationTest.dao.GehwegInformation;
import com.boristenelsen.registrationTest.dto.ClientBestellung;
import com.boristenelsen.registrationTest.services.*;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class UserDashboardController {

    @Autowired
    GehwegInformationService gehwegInformationService;

    @Autowired
    UserService userService;
    @Autowired
    AngebotService angebotService;
    @Autowired
    PdfService pdfService;

    @Autowired
    AuftragService auftragService;

    @GetMapping("/home/dashboard")
    public String dashboard(Model model, Principal principal) {

        String email = getEmail();
        List<GehwegInformation> gehwegInformation = gehwegInformationService.getGehWegInformationObjects(email);
        model.addAttribute("fullusername", getFullUserName());
        model.addAttribute("gehwegInformationList", gehwegInformation);

        if (userService.isUserAdmin(principal)) return "redirect:/home/adminDashboard";
        else return "uebersicht";
    }


    @GetMapping("/home/dashboard/profil")
    public String profil(Model model) {
        model.addAttribute("userObject", userService.loadUser(getEmail()));
        return "profil";
    }

    @GetMapping("/home/dashboard/{gehwegInformationsId}")
    public String angebotsaufforderungUebersicht(@PathVariable int gehwegInformationsId, Model model) {

        ClientBestellung cb = gehwegInformationService.loadClientBestellungByID(gehwegInformationsId);
        model.addAttribute("clientbestellung", cb);

        return "angebotsaufforderung";
    }

    @GetMapping("/home/dashboard/angebot/{bestellundId}")
    public String angebotAnnahme(@PathVariable int bestellundId, Model model) {
        ClientBestellung cb = gehwegInformationService.loadClientBestellungByID(bestellundId);
        Angebot angebot = angebotService.loadAngebot(bestellundId);

        model.addAttribute("clientbestellung", cb);
        model.addAttribute("angebot", angebot);
        return "angebotannahme";

    }

    @GetMapping("/home/dashboard/angebot/downloadAngebot/{bestellungId}")
    public ResponseEntity<InputStreamResource> downloadAngebot(@PathVariable int bestellungId) throws IOException {

        ClientBestellung cb = gehwegInformationService.loadClientBestellungByID(bestellungId);
        Angebot angebot = angebotService.loadAngebot(bestellungId);
        ByteArrayInputStream bis = pdfService.generatePdf(cb, angebot);

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=angebot_" + angebot.getBestellungId() + ".pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/home/createAuftrag/{angebotId}")
    public String createAuftrag(@PathVariable String angebotId, Model model) throws AngebotDoesNotExistInDatabaseException {

        auftragService.registerAngebot(angebotId);
        Angebot angebot = angebotService.loadAngebot(angebotId);
        angebot.setStatus("geschlossen");
        angebotService.saveAngebot(angebot);

        return "redirect:/home/dashboard";
    }

    private String getFullUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            String name = userService.getUserName(email);
            return name;

        } else {
            return "";
        }
    }


    private String getEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();

        } else return "";
    }

}
