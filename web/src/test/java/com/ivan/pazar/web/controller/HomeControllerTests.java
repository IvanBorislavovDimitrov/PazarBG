package com.ivan.pazar.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class HomeControllerTests {

    @Autowired
    public MockMvc mockMvc;

    @Test
    public void testIndex_Index_returnsIndexPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(view().name("base-layout"));
    }

    @Test
    public void homeController_advertiseRegister_userAdvertised() throws Exception {
        mockMvc.perform(get("/advertise/register"))
                .andExpect(view().name("views/advert-register"));
    }

    @Test
    public void homeController_continueAsGuest_continuedAsGuest() throws Exception {
        mockMvc.perform(get("/continue-as-guest"))
                .andExpect(view().name("redirect:/"));
    }
}
