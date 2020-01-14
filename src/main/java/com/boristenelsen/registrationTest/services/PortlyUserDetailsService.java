package com.boristenelsen.registrationTest.services;


import com.boristenelsen.registrationTest.dao.Role;
import com.boristenelsen.registrationTest.dao.User;
import com.boristenelsen.registrationTest.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class PortlyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email);
        if(user ==null){
            throw new UsernameNotFoundException("Kein Benutzer gefunden" + email);
        }

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked= true;
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword().toLowerCase(), enabled,accountNonExpired,credentialsNonExpired,
                accountNonLocked,getAuthorities(user.getRoles()));




    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(Role r: roles){
            authorities.add(new SimpleGrantedAuthority(r.getName()));
        }
        return authorities;
    }


}
