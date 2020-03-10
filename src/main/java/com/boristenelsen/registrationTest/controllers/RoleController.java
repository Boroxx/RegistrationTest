package com.boristenelsen.registrationTest.controllers;

import com.boristenelsen.registrationTest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class RoleController {

    @Autowired
    UserService userService;

    @GetMapping("/rolehome")
    public String rolecontrol(Principal principal){
        if(userService.isUserUnternehmen(principal))return"subdashboard";
        else return"/home";


    }
}
