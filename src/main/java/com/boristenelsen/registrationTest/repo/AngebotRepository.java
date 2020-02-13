package com.boristenelsen.registrationTest.repo;

import com.boristenelsen.registrationTest.dao.Angebot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AngebotRepository extends JpaRepository<Angebot, Long> {
}
