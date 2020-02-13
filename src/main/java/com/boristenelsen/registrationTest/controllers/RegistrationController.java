package com.boristenelsen.registrationTest.controllers;

import com.boristenelsen.registrationTest.Exceptions.EmailExistsException;
import com.boristenelsen.registrationTest.dao.User;
import com.boristenelsen.registrationTest.dto.UserDto;
import com.boristenelsen.registrationTest.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@Controller

public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private GehwegInformationService gehwegInformationService;

    @Autowired
    private PositionService positionService;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private AngebotService angebotService;


    @GetMapping("/home")
    public String index(Model model) {


        return "home";
    }


    @GetMapping("/downloadFile/{folder}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String folder, @PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource

        Resource resource = fileStorageService.loadFileAsResource(fileName, folder);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {

        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String getregistration(Model model) {

        model.addAttribute("userDto", new UserDto());
        return "registration";
    }


    @PostMapping("/registration")
    public String postregistration(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult bindingResult, Model model) {
        System.out.println(model.asMap());
        User registered = new User();
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        /* Wenn Form valide ist dann createUserAccount ansonsten*/
        if (!bindingResult.hasErrors()) {
            try {
                registered = createUserAccount(userDto, bindingResult);
            } catch (EmailExistsException e) {
                e.printStackTrace();
                bindingResult.rejectValue("email", "message.regError");
                return "registration";
            }
        }
        return "redirect:/login";
    }


    /*createUserAccount() ruft Userservice auf welcher sich um das Data Transfer Objekt User kümmert*/
    private User createUserAccount(UserDto userDto, BindingResult bindingResult) throws EmailExistsException {
        User registered = null;
        registered = userService.registerNewAccount(userDto);
        return registered;
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
            String email = ((UserDetails) principal).getUsername();
            return email;
        } else return "";
    }


}
