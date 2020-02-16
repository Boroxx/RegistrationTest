package com.boristenelsen.registrationTest;


import com.boristenelsen.registrationTest.dao.BauvorhabenAdresse;
import com.boristenelsen.registrationTest.dao.GehwegInformation;
import com.boristenelsen.registrationTest.dao.User;
import com.boristenelsen.registrationTest.dto.ClientBestellung;
import com.boristenelsen.registrationTest.services.GehwegInformationService;
import com.boristenelsen.registrationTest.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserDashboardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GehwegInformationService gehwegInformationServiceMock;

    @MockBean
    UserService userServiceMock;

    @Test
    @DirtiesContext
    public void dashboard_FuegeAlleGehwegModulEintraegeInViewZu() throws Exception {
        GehwegInformation first = GehwegInformation.builder()
                .ID(1)
                .fullName("Max Mustermann")
                .hindernis("Wasserkappe")
                .vorgarten("ja")
                .username("max@test.de")
                .anmerkung("")
                .gehwegbreite(160)
                .plattenbreite(30)
                .plattenlaenge(30)
                .status(true)
                .genehmigungDownload("")
                .gehwegBilderListe("")
                .bauvorhabenAdresse(new BauvorhabenAdresse())
                .build();

        GehwegInformation second = GehwegInformation.builder()
                .ID(1)
                .fullName("MaxMustermann")
                .hindernis("Laterne")
                .vorgarten("ja")
                .username("max@test.de")
                .anmerkung("")
                .gehwegbreite(160)
                .plattenbreite(30)
                .plattenlaenge(30)
                .status(true)
                .genehmigungDownload("")
                .gehwegBilderListe("")
                .bauvorhabenAdresse(new BauvorhabenAdresse())
                .build();

        when(gehwegInformationServiceMock.getGehWegInformationObjects(anyString()))
                .thenReturn(Arrays.asList(first, second));
        List<GehwegInformation> temp = gehwegInformationServiceMock.getGehWegInformationObjects("max@test.de");


        mockMvc.perform(get("/home/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("uebersicht"))
                .andExpect(model().attribute("gehwegInformationList", hasSize(2)));

        verify(gehwegInformationServiceMock, times(1))
                .getGehWegInformationObjects("max@test.de");


    }

    @Test
    @DirtiesContext
    public void profil_FuegeUserObjectHinzu() throws Exception {
        User user = User.builder()
                .email("test@test.de")
                .ID(1)
                .nachname("Tenelsen")
                .vorname("boris")
                .password("test")
                .phonenumber(0123456)
                .role("USER_ROLE")
                .stadt_plz("Stadt 1234")
                .strasse_hausnummer("strasse 12")
                .build();
        when(userServiceMock.loadUser(anyString())).thenReturn(user);

        mockMvc.perform(get("/home/dashboard/profil"))
                .andExpect(status().isOk())
                .andExpect(view().name("profil"))
                .andExpect(model().attribute("userObject", not(nullValue())));

        verify(userServiceMock, times(1)).loadUser("");


    }

    @Test
    @DirtiesContext
    public void angebotsaufforderungUebersicht_AngebotWirdAufSeiteAngezeigt() throws Exception {
        ClientBestellung cb = ClientBestellung.builder()
                .id(123)
                .build();

        when(gehwegInformationServiceMock.loadClientBestellungByID(123)).thenReturn(cb);

        mockMvc.perform(get("/home/dashboard/123"))
                .andExpect(status().isOk())
                .andExpect(view().name("angebotsaufforderung"))
                .andExpect(model().attribute("clientbestellung", not(nullValue())));

        verify(gehwegInformationServiceMock, times(1)).loadClientBestellungByID(123);

    }


}
