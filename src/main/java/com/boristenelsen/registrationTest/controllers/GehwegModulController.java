package com.boristenelsen.registrationTest.controllers;


import com.boristenelsen.registrationTest.dao.GehwegInformation;
import com.boristenelsen.registrationTest.dto.BauvorhabenAdresseDto;
import com.boristenelsen.registrationTest.dto.GehwegInformationDto;
import com.boristenelsen.registrationTest.services.GehwegInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.annotation.SessionScope;

import javax.validation.Valid;

@Controller
@SessionScope
public class GehwegModulController {

    @Autowired
    GehwegInformationService gehwegInformationService;


    @GetMapping("/home/gehwegabsenkung")
    public String gehwegabsenkung(Model model) {

        return "gehwegabsenkung";
    }

    /*Lädt die Informationen zur Angefragten Angebotsauffoderung für den jeweiligen Benutzer*/
    @GetMapping("/home/gehwegabsenkung2")
    public String gehwegabsenkungsec(Model model) {

        model.addAttribute("bauvorhabenAdresseDto", new BauvorhabenAdresseDto());
        return "gehwegabsenkung2";
    }

    @GetMapping("/home/gehwegabsenkung3")
    public String gehwegabsenkungthird(Model model) {


        model.addAttribute("gehwegInformationDto", new GehwegInformationDto());

        return "gehwegabsenkung3";
    }

    @PostMapping("/home/gehwegabsenkung2")
    public String bauvorhabenAdresse(@Valid @ModelAttribute("bauvorhabenAdresseDto") BauvorhabenAdresseDto bauvorhabenAdresseDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("ERRRRRORR");
            return "gehwegabsenkung2";
        }
        createBauvorhabenAdresse(bauvorhabenAdresseDto, bindingResult);


        return "redirect:/home/gehwegabsenkung3";
    }

    @PostMapping("/home/gehwegabsenkung3")
    public String gehweginformation(@ModelAttribute("gehwegInformationDto") GehwegInformationDto gehwegInformationDto, BindingResult bindingResult, Model model) {
        GehwegInformation gehwegInformation = new GehwegInformation();
        gehwegInformationDto.setUsername(getEmail());
        gehwegInformation = createGehwegInformation(gehwegInformationDto, bindingResult);
        return "redirect:/home/dashboard";
    }

    @GetMapping("/home/modulLoeschen/{gehwegid}/{bauid}")
    public String loescheModul(@PathVariable int gehwegid,@PathVariable int bauid){
        gehwegInformationService.delete(gehwegid,bauid);
        return "redirect:/home/dashboard";
    }


    private GehwegInformation createGehwegInformation(GehwegInformationDto gehwegInformationDto, BindingResult bindingResult) {
        GehwegInformation gehwegInformation = null;
        gehwegInformation = gehwegInformationService.registerGehwegInformation(gehwegInformationDto);

        return gehwegInformation;
    }

    private void createBauvorhabenAdresse(BauvorhabenAdresseDto bauvorhabenAdresseDto, BindingResult bindingResult) {

        gehwegInformationService.registerBauvorhaben(bauvorhabenAdresseDto);


    }


    private String getEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            return email;
        } else return "";
    }
}
