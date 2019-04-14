package com.ivan.pazar.web.controller.user;

import com.ivan.pazar.domain.model.entity.Region;
import com.ivan.pazar.domain.model.entity.Town;
import com.ivan.pazar.persistence.repository.RegionRepository;
import com.ivan.pazar.persistence.repository.TownRepository;
import com.ivan.pazar.persistence.repository.UserRepository;
import com.ivan.pazar.web.controller.AdvertCreator;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserProfileControllerTests {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private TownRepository townRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Test
    @WithMockUser(value = "pesho", roles = {"MODERATOR"})
    public void testRegisterController_loadRegisterPage_registerPageLoaded() throws Exception {
        Region region = createDemoRegion();
        Town town = createDemoTown(region);
        AdvertCreator advertCreator = new AdvertCreator();
        advertCreator.createDemoUser(region, town, userRepository, null, null, null);
        mockMvc.perform(get("/users/profile"))
                .andExpect(view().name("base-layout"))
                .andExpect(model().attribute("view", "views/users/profile"));
    }

    @Test
    @WithMockUser(value = "pesho", roles = {"MODERATOR"})
    public void testRegisterController_otherUserProfile_lookOtherProfile() throws Exception {
        Region region = createDemoRegion();
        Town town = createDemoTown(region);
        AdvertCreator advertCreator = new AdvertCreator();
        advertCreator.createDemoUser(region, town, userRepository, null, null, null);
        mockMvc.perform(get("/users/other-user-profile")
                .param("username", "pesho"))
                .andExpect(view().name("base-layout"))
                .andExpect(model().attribute("view", "views/users/other-profile"));
    }

    private Town createDemoTown(Region region) {
        Town town = new Town();
        town.setName("vraca");
        town.setRegion(region);
        townRepository.saveAndFlush(town);

        return town;
    }

    private Region createDemoRegion() {
        Region region = new Region();
        region.setName("sliven");

        regionRepository.saveAndFlush(region);

        return region;
    }
}
