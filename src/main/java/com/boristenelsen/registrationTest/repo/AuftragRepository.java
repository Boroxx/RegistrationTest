package com.boristenelsen.registrationTest.repo;

import com.boristenelsen.registrationTest.dao.Auftrag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuftragRepository extends JpaRepository<Auftrag, Long> {

}
