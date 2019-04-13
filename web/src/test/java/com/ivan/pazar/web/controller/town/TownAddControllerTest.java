package com.ivan.pazar.web.controller.town;

import com.ivan.pazar.domain.model.entity.Region;
import com.ivan.pazar.persistence.repository.RegionRepository;
import com.ivan.pazar.persistence.repository.TownRepository;
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
public class TownAddControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private TownRepository townRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Test
    @WithMockUser(value = "pesho", roles = {"MODERATOR"})
    public void testCategoryController_new_viewLoaded() throws Exception {
        mockMvc.perform(get("/towns/add"))
                .andExpect(view().name("base-layout"))
                .andExpect(model().attribute("view", "views/towns/add-town"));
    }

    @Test
    @WithMockUser(value = "pesho", roles = {"MODERATOR"})
    public void testCategoryController_new_regionCreated() throws Exception {

        createDemoRegion();

        mockMvc.perform(MockMvcRequestBuilders.post("/towns/add")
                .with(csrf())
                .param("name", "Konstantinopol")
                .param("region", "Sliven"));

        assertEquals(2, townRepository.count());
    }

    private Region createDemoRegion() {
        Region region = new Region();
        region.setName("Sliven");
        regionRepository.saveAndFlush(region);

        return region;
    }
}
