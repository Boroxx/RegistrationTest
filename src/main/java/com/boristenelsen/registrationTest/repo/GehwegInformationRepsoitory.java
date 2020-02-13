package com.boristenelsen.registrationTest.repo;

import com.boristenelsen.registrationTest.dao.GehwegInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GehwegInformationRepsoitory extends JpaRepository<GehwegInformation, Long> {
    GehwegInformation findByUsername(String email);

    GehwegInformation findByID(int id);

    List<GehwegInformation> findAllByUsername(String email);
}
