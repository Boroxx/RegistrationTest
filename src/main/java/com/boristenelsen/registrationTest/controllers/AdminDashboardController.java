package com.boristenelsen.registrationTest.controllers;

import com.boristenelsen.registrationTest.Exceptions.AngebotExistsAlreadyInDatabaseExcepetion;
import com.boristenelsen.registrationTest.Wrapper.PositionPreisWrapper;
import com.boristenelsen.registrationTest.dao.Position;
import com.boristenelsen.registrationTest.dto.AngebotDto;
import com.boristenelsen.registrationTest.dto.AngebotTemplateDto;
import com.boristenelsen.registrationTest.dto.AuftragDto;
import com.boristenelsen.registrationTest.dto.ClientBestellung;
import com.boristenelsen.registrationTest.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@Controller
@SessionScope
public class AdminDashboardController {

    @Autowired
    AngebotService angebotService;

    @Autowired
    GehwegInformationService gehwegInformationService;

    @Autowired
    PositionService positionService;

    @Autowired
    AuftragService auftragService;

    @Autowired
    StatistikService statistikService;

    @Autowired
    UserService userService;


    @GetMapping("/home/adminDashboard")
    public String adminDashboard(Model model) {

        List<ClientBestellung> list = gehwegInformationService.getAllAnfragen();

        model.addAttribute("bestellungen", list);

        return "adminDashboard";
    }

    @GetMapping("/home/adminAuftraege")
    public String adminAuftraege(Model model) {


        List<AuftragDto> auftraege = auftragService.loadAuftraege();
        model.addAttribute("auftraege", auftraege);

        return "adminAuftraege";
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
        model.addAttribute("positionPreisWrapper", new PositionPreisWrapper());
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
        List<Position> lv = positionService.getAllPositionen();
        AngebotTemplateDto angebotTemplateDto = angebotService.createAngebotTemplate(bestellung, lv);

        model.addAttribute("angebottemplate", angebotTemplateDto);
        model.addAttribute("angebot", bestellung);
        return "createAngebot";

    }


    @GetMapping("/admin/AngebotSuccess")
    public String successAngebot(Model model) {


        List<AngebotDto> angebotListe = angebotService.loadAlleOffenenAngebote();
        model.addAttribute("angebotListe", angebotListe);
        return "AngebotSuccess";


    }

   @GetMapping("/admin/nachunternehmerkatalog")
   public String nachunternehmerkatalog(Model model){
        model.addAttribute("unternehmenList",userService.getUnternehmerList());
        return"adminNachunternehmerkatalog";
   }
    @GetMapping("/admin/statistik")
    public String statistiken(Model model) {

        List<AuftragDto> auftragList = auftragService.loadAuftraege();
        int januar = 0, februar = 0, maerz = 0, april = 0, mai = 0, juni = 0, juli = 0, august = 0, september = 0, oktober = 0, november = 0, dezember = 0;
        for (AuftragDto auftrag : auftragList) {

            januar += statistikService.checkMonth("01", auftrag.getCreated());
            februar += statistikService.checkMonth("02", auftrag.getCreated());
            maerz += statistikService.checkMonth("03", auftrag.getCreated());
            april += statistikService.checkMonth("04", auftrag.getCreated());
            mai += statistikService.checkMonth("05", auftrag.getCreated());
            juni += statistikService.checkMonth("06", auftrag.getCreated());
            juli += statistikService.checkMonth("07", auftrag.getCreated());
            august += statistikService.checkMonth("08", auftrag.getCreated());
            september += statistikService.checkMonth("09", auftrag.getCreated());
            oktober += statistikService.checkMonth("10", auftrag.getCreated());
            november += statistikService.checkMonth("11", auftrag.getCreated());
            dezember += statistikService.checkMonth("12", auftrag.getCreated());


        }

        model.addAttribute("januar", januar);
        model.addAttribute("februar", februar);
        model.addAttribute("maerz", maerz);
        model.addAttribute("april", april);
        model.addAttribute("mai", mai);
        model.addAttribute("juni", juni);
        model.addAttribute("juli", juli);
        model.addAttribute("august", august);
        model.addAttribute("september", september);
        model.addAttribute("oktober", oktober);
        model.addAttribute("november", november);
        model.addAttribute("dezember", dezember);

        System.out.println(angebotService.loadAllAngebote().size());
        System.out.println(angebotService.loadAlleOffenenAngebote().size());
        System.out.println(auftragList.size());
        model.addAttribute("angebotsanfragen", angebotService.loadAllAngebote().size());
        model.addAttribute("offeneangebote", angebotService.loadAlleOffenenAngebote().size());
        model.addAttribute("auftraege", auftragList.size());
        model.addAttribute("privatkunden", (userService.userCounter() - 1));


        return "statistik";

    }

    @PostMapping("/admin/positionenEdit")
    public String adminpositonenEdit(@ModelAttribute("positionPreisWrapper") PositionPreisWrapper positionPreisWrapper, BindingResult bindingResult, Model model) {
        Position position = positionService.findPositionByOrdnungsnummer(positionPreisWrapper.getPositionordnungsnummer());
        positionService.storePosition(position, positionPreisWrapper.getPositionpreis());
        return "redirect:/admin/positionenEdit";
    }

    @PostMapping("/admin/createFinalAngebot")
    public String createFinalAngebot(@ModelAttribute("angebottemplate") AngebotDto angebottemplate, BindingResult bindingResult, Model model) throws AngebotExistsAlreadyInDatabaseExcepetion {
        angebotService.registerAngebot(angebottemplate);

        return "redirect:/admin/AngebotSuccess";
    }


}
