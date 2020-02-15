package com.boristenelsen.registrationTest.controllers;


import com.boristenelsen.registrationTest.dao.GehwegInformation;
import com.boristenelsen.registrationTest.dto.ClientBestellung;
import com.boristenelsen.registrationTest.services.GehwegInformationService;
import com.boristenelsen.registrationTest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class UserDashboardController {

    @Autowired
    GehwegInformationService gehwegInformationService;

    @Autowired
    UserService userService;

    @GetMapping("/home/dashboard")
    public String dashboard(Model model) {

        String email = getEmail();
        List<GehwegInformation> gehwegInformation = gehwegInformationService.getGehWegInformationObjects(email);
        model.addAttribute("fullusername", getEmail());
        model.addAttribute("gehwegInformationList", gehwegInformation);
        return "uebersicht";
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
