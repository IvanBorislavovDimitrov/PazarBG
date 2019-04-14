package com.ivan.pazar.web.controller.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReviewRestControllerTests {

    @Autowired
    public MockMvc mockMvc;

    @Test
    @WithMockUser(value = "pesho", roles = {"ADMIN", "ROOT"})
    public void checkReview() throws Exception {
        mockMvc.perform(get("/api/reviews/all")
        .param("advertId", "123"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    @WithMockUser(value = "pesho", roles = {"ADMIN", "ROOT"})
    public void removeReview() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/reviews/delete")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .param("reviewId", "123")).andExpect(view().name("redirect:/"));
    }
}
