package com.boristenelsen.registrationTest.services;

import com.boristenelsen.registrationTest.Exceptions.EmailExistsException;
import com.boristenelsen.registrationTest.dao.User;
import com.boristenelsen.registrationTest.dto.UserDto;
import com.boristenelsen.registrationTest.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    /*EXCEPTIONS NOCH NICHT IMPLEMENTIERT*/

    @Transactional
    public User registerNewAccount(UserDto userDto) throws EmailExistsException {
        if(emailExists(userDto.getEmail())){
            throw new EmailExistsException("Email existiert bereits: " + userDto.getEmail());
        }

        User user = new User();
        user.setVorname(userDto.getVorname());
        user.setNachname(userDto.getNachname());
        user.setEmail(userDto.getEmail());
        user.setPhonenumber(userDto.getPhonenumber());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return new User();
    }

    private boolean emailExists(String email) {
        User registered = userRepository.findByEmail(email);
        if(registered!=null)return true;

        return false;

    }
}
