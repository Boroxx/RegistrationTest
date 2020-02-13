package com.boristenelsen.registrationTest.controllers;

import com.boristenelsen.registrationTest.dao.Position;
import com.boristenelsen.registrationTest.dto.ClientBestellung;
import com.boristenelsen.registrationTest.services.AngebotService;
import com.boristenelsen.registrationTest.services.GehwegInformationService;
import com.boristenelsen.registrationTest.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminDashboardController {

    @Autowired
    AngebotService angebotService;

    @Autowired
    GehwegInformationService gehwegInformationService;

    @Autowired
    PositionService positionService;


    @GetMapping("/home/adminDashboard")
    public String adminDashboard(Model model) {

        List<ClientBestellung> list = gehwegInformationService.getAllAnfragen();

        model.addAttribute("bestellungen", list);
        return "adminDashboard";
    }

    @GetMapping("/admin/positionen")
    public String adminpositonen(Model model) {

        List<Position> list = positionService.getAllPositionen();

        model.addAttribute("positionen", list);
        return "adminPosition";
    }

    @GetMapping("/admin/positionenEdit")
    public String adminpositonenEdit(Model model) {

        List<Position> list = positionService.getAllPositionen();
        model.addAttribute("position", new Position());

        model.addAttribute("positionen", list);
        return "adminPositionEdit";
    }

    @GetMapping("/admin/vergabe/{id}")
    public String adminVergabeStatus(@PathVariable int id) {

        gehwegInformationService.changeStatus(id);

        return "redirect:/home/adminDashboard";

    }


    @GetMapping("/admin/updateDatabase")
    public String adminPosition() {
        positionService.registerPositions();
        return "home";

    }

    @GetMapping("/admin/createAngebot/{bestellungId}")
    public String createAngebot(@PathVariable int bestellungId, Model model) {

        ClientBestellung bestellung = gehwegInformationService.loadClientBestellungByID(bestellungId);
        List<Position> positionList = angebotService.setUpChosenPositions(bestellung);

        model.addAttribute("positionen", positionList);
        model.addAttribute("angebot", bestellung);

        return "createAngebot";

    }

    @GetMapping("/admin/AngebotSuccess/{bestellungId}")
    public String successAngebot(@PathVariable int bestellungId, Model model) {

        angebotService.registerAngebot(bestellungId);


        return "AngebotSuccess";

    }

    @PostMapping("/admin/positionenEdit")
    public String adminpositonenEdit(@ModelAttribute("positionpreis") double preis, @ModelAttribute("ordnungsnummer") String ordnungsnummer, BindingResult bindingResult, Model model) {

        System.out.println(ordnungsnummer);
        Position position = positionService.findPositionByOrdnungsnummer(ordnungsnummer);
        positionService.storePosition(position, preis);


        return "redirect:/admin/positionenEdit";
    }


}