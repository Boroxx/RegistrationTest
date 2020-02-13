package com.boristenelsen.registrationTest.services;

import com.boristenelsen.registrationTest.Exceptions.EmailExistsException;
import com.boristenelsen.registrationTest.dao.User;
import com.boristenelsen.registrationTest.dto.UserDto;
import com.boristenelsen.registrationTest.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    /*EXCEPTIONS NOCH NICHT IMPLEMENTIERT*/

    @Transactional
    public User registerNewAccount(UserDto userDto) throws EmailExistsException {
        if (emailExists(userDto.getEmail())) {
            throw new EmailExistsException("Email existiert bereits: " + userDto.getEmail());
        }

        User user = new User();
        user.setVorname(userDto.getVorname());
        user.setNachname(userDto.getNachname());
        user.setEmail(userDto.getEmail());
        user.setPhonenumber(userDto.getPhonenumber());
        user.setStrasse_hausnummer(userDto.getStrasse_hausnummer());
        user.setStadt_plz(userDto.getStadt_plz());
        user.setRole("ROLE_USER");
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));


        userRepository.save(user);
        return new User();
    }


    private boolean emailExists(String email) {
        User registered = userRepository.findByEmail(email);
        return registered != null;

    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }

    /*Gibt Vor und Nachnamen für die Übersicht im Dashboard zurück*/
    public String getUserName(String email) {
        User user = userRepository.findByEmail(email);
        return user.getVorname() + " " + user.getNachname();
    }

    public long getTelefon(String email) {
        User user = userRepository.findByEmail(email);
        return user.getPhonenumber();
    }

    public User loadUser(String email) {
        return userRepository.findByEmail(email);
    }
}
