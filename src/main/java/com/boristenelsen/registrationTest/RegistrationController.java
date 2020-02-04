package com.boristenelsen.registrationTest;

import com.boristenelsen.registrationTest.Exceptions.EmailExistsException;
import com.boristenelsen.registrationTest.dao.User;
import com.boristenelsen.registrationTest.dto.UserDto;
import com.boristenelsen.registrationTest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller

public class RegistrationController {

   @Autowired
   private UserService userService;


    @GetMapping("/home")
    public String index(Model model){


        return "home";
    }

    @GetMapping("/home/gehwegabsenkung")
    public String gehwegabsenkung(Model model){


        return "gehwegabsenkung";
    }

    @GetMapping("/home/gehwegabsenkung2")
    public String gehwegabsenkungsec(Model model){


        return "gehwegabsenkung2";
    }
    @GetMapping("/home/dashboard")
    public String dashboard(Model model){

        model.addAttribute("fullusername", getFullUserName());
        model.addAttribute("telefon", getTelefon());
        return"uebersicht";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/registration")
    public String getregistration(Model model){

        model.addAttribute("userDto", new UserDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String postregistration(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult bindingResult,Model model){
        System.out.println(model.asMap());
        User registered = new User();
        if(bindingResult.hasErrors()){
            return "registration";
            }

        /* Wenn Form valide ist dann createUserAccount ansonsten*/
        if(!bindingResult.hasErrors()){
            try {
                registered = createUserAccount(userDto,bindingResult);
            } catch (EmailExistsException e) {
                e.printStackTrace();
                bindingResult.rejectValue("email","message.regError");
                return "registration";
            }
        }
        return"redirect:/login" ;
    }





    /*createUserAccount() ruft Userservice auf welcher sich um das Data Transfer Objekt User kümmert*/
    private User createUserAccount(UserDto userDto, BindingResult bindingResult) throws EmailExistsException {
        User registered = null;
        registered = userService.registerNewAccount(userDto);
        return registered;
    }

    private String getFullUserName(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String email = ((UserDetails)principal).getUsername();
            String name = userService.getUserName(email);
            return name;

        } else {
            return "";
        }
    }

    private String getTelefon(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String email = ((UserDetails)principal).getUsername();
            Long telefon = userService.getTelefon(email);
            return telefon.toString();

        } else {
            return "";
        }
    }



}
