package com.ivan.pazar.web.controller.view.about;

import com.ivan.pazar.persistence.repository.ContactUsMessageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ContactUsControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ContactUsMessageRepository contactUsMessageRepository;

    @Test
    public void testAboutUsController_contactUs_contacted() throws Exception {
        mockMvc.perform(get("/contact-us"))
                .andExpect(view().name("base-layout"))
                .andExpect(model().attribute("view", "views/about/contact"));
    }

    @Test
    public void testAboutController_contactUs_post() throws Exception {

        mockMvc.perform(post("/contact-us")
                .with(csrf())
                .param("username", "username")
                .param("email", "email@asd.asd")
                .param("phoneNumber", "+01231")
                .param("content", "asdasdsdf"));

        assertEquals(1, contactUsMessageRepository.count());
    }

}
