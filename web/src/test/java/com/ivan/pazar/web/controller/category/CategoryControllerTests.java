package com.ivan.pazar.web.controller.category;

import com.ivan.pazar.persistence.repository.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CategoryControllerTests {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @WithMockUser(value = "pesho", roles = {"MODERATOR"})
    public void testCategoryController_new_viewLoaded() throws Exception {
        mockMvc.perform(get("/categories/new"))
                .andExpect(view().name("base-layout"))
                .andExpect(model().attribute("view", "views/categories/add-category"));
    }

    @Test
    @WithMockUser(value = "pesho", roles = {"MODERATOR"})
    public void changeUserRoleController_changeUserRoleConfirmPost_roleChanged() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("multipartPicture",
                "request",
                MediaType.APPLICATION_JSON_VALUE,
                (byte[]) null);

        mockMvc.perform(multipart("/categories/new")
                .file(multipartFile)
                .with(csrf())
                .param("name", "Cars"));

        assertEquals(1, categoryRepository.count());
    }

}
