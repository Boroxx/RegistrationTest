package com.boristenelsen.registrationTest.Wrapper;

import com.boristenelsen.registrationTest.dao.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AngebotListeWrapper {

    private List<Position> positionen;


}
