package com.ivan.pazar.web.controller.view.subcategory;

import com.ivan.pazar.domain.model.entity.Category;
import com.ivan.pazar.persistence.repository.CategoryRepository;
import com.ivan.pazar.persistence.repository.SubcategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SubcategoryControllerTests {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Test
    @WithMockUser(value = "pesho", roles = {"MODERATOR"})
    public void testCategoryController_new_viewLoaded() throws Exception {
        mockMvc.perform(get("/subcategories/new"))
                .andExpect(view().name("base-layout"))
                .andExpect(model().attribute("view", "views/subcategories/add-subcategory"));
    }

    @Test
    @WithMockUser(value = "pesho", roles = {"MODERATOR"})
    public void testCategoryController_new_regionCreated() throws Exception {

        createDemoCategory();

        mockMvc.perform(MockMvcRequestBuilders.post("/subcategories/new")
                .with(csrf())
                .param("name", "dogs")
                .param("category", "Animals"));

        assertEquals(1, subcategoryRepository.count());
    }

    private Category createDemoCategory() {
        Category category = new Category();
        category.setName("Animals");
        categoryRepository.saveAndFlush(category);

        return category;
    }
}
