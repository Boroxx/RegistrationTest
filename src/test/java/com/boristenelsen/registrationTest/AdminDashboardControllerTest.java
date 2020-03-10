package com.boristenelsen.registrationTest;

import com.boristenelsen.registrationTest.Wrapper.PositionPreisWrapper;
import com.boristenelsen.registrationTest.dao.Position;
import com.boristenelsen.registrationTest.dto.ClientBestellung;
import com.boristenelsen.registrationTest.services.AngebotService;
import com.boristenelsen.registrationTest.services.GehwegInformationService;
import com.boristenelsen.registrationTest.services.PositionService;
import com.boristenelsen.registrationTest.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;


import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminDashboardControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    GehwegInformationService gehwegInformationServiceMock;

    @MockBean
    UserService userServiceMock;

    @MockBean
    AngebotService angebotServiceMock;

    @MockBean
    PositionService positionServiceMock;


    @Test
    @DirtiesContext
    public void adminDashboard_WerdenAlleAnfragenInViewGeladen() throws Exception{
        ClientBestellung first = ClientBestellung.builder()
                .id(1)
                .build();

        ClientBestellung second = ClientBestellung.builder()
                .id(2)
                .build();


        when(gehwegInformationServiceMock.getAllAnfragen()).thenReturn(Arrays.asList(first,second));

        mockMvc.perform(get("/home/adminDashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminDashboard"))
                .andExpect(model().attribute("bestellungen",hasSize(2)));

        verify(gehwegInformationServiceMock,times(1)).getAllAnfragen();
    }

    @Test
    @DirtiesContext
    public void adminPositionen_wurdenAllePositionenGeladen() throws Exception{
        Position first = Position.builder().ordnungsnummer("1").build();
        Position second = Position.builder().ordnungsnummer("2").build();



        when(positionServiceMock.getAllPositionen()).thenReturn(Arrays.asList(first,second));
        mockMvc.perform(get("/admin/positionen"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminPosition"))
                .andExpect(model().attribute("positionen",hasSize(2)));

        verify(positionServiceMock,times(1)).getAllPositionen();
    }

    @Test
    @DirtiesContext
    public void adminPositionenEdit_PositionenWurdenAlleGeladen() throws Exception{
        Position first = Position.builder().ordnungsnummer("1").build();
        Position second = Position.builder().ordnungsnummer("2").build();



        when(positionServiceMock.getAllPositionen()).thenReturn(Arrays.asList(first,second));
        mockMvc.perform(get("/admin/positionenEdit"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminPositionEdit"))
                .andExpect(model().attribute("positionen",hasSize(2)));

        verify(positionServiceMock,times(1)).getAllPositionen();
    }

    @Test
    @DirtiesContext
    public void adminVergabeStatus_GehWegStatusAenderungWirdAufgerufen() throws Exception{


        when(gehwegInformationServiceMock.changeStatus(123)).thenReturn(1);
        mockMvc.perform(get("/admin/vergabe/123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/home/adminDashboard"));


        verify(gehwegInformationServiceMock,times(1)).changeStatus(123);
    }

    @Test
    @DirtiesContext
    public void adminPosition_SetupDatabase() throws Exception{




        mockMvc.perform(get("/admin/updateDatabase"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));


        verify(positionServiceMock,times(1)).registerPositions();
    }

    @Test
    @DirtiesContext
    public void createAngebot_BestellungUndChosenPositionListInModel() throws Exception{


        Position first = Position.builder().ordnungsnummer("1").build();
        Position second = Position.builder().ordnungsnummer("2").build();
        ClientBestellung cfirst = ClientBestellung.builder()
                .id(1)
                .build();





    }

    @Test
    @DirtiesContext
    public void successAngebot_RegisterAngebotWirdAugerufenUndAufTemplateWeiterGeleitet() throws Exception{

    }

   @Test
   @DirtiesContext
   public void adminPositionenEdit_PostPreisIntoDatabseController() throws Exception{


       mockMvc.perform(post("/admin/positionenEdit").
               param("positionordnungsnummer","0000001")
               .param("positionpreis","20.0")
               .contentType("application/x-www-form-urlencoded"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/admin/positionenEdit"));
       verify(positionServiceMock,times(1)).findPositionByOrdnungsnummer("0000001");

   }
}
