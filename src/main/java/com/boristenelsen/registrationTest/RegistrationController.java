package com.boristenelsen.registrationTest;

import com.boristenelsen.registrationTest.Exceptions.EmailExistsException;
import com.boristenelsen.registrationTest.dao.User;
import com.boristenelsen.registrationTest.dto.UserDto;
import com.boristenelsen.registrationTest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
