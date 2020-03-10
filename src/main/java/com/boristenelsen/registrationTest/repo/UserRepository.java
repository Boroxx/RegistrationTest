package com.boristenelsen.registrationTest.repo;


import com.boristenelsen.registrationTest.dao.User;
import com.boristenelsen.registrationTest.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    List<User> findByRole(String role);


}
