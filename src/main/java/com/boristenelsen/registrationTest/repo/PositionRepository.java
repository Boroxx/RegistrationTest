package com.boristenelsen.registrationTest.repo;

import com.boristenelsen.registrationTest.dao.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {

    Position findByOrdnungsnummer(String ordnungsnummer);
}
