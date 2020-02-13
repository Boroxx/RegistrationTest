package com.boristenelsen.registrationTest.repo;


import com.boristenelsen.registrationTest.dao.BauvorhabenAdresse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BauvorhabenAdresseRepository extends JpaRepository<BauvorhabenAdresse, Long> {
    BauvorhabenAdresse findByID(int id);
}
