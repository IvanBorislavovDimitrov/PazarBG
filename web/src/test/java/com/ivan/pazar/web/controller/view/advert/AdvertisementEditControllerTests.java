package com.ivan.pazar.web.controller.view.advert;

import com.ivan.pazar.domain.model.entity.Advertisement;
import com.ivan.pazar.domain.model.entity.Region;
import com.ivan.pazar.domain.model.entity.Town;
import com.ivan.pazar.persistence.repository.*;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
public class AdvertisementEditControllerTests {
    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private TownRepository townRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Test
    @WithMockUser(value = "pesho", roles = {"MODERATOR"})
    public void testAdvertisement() throws Exception {
        Region region = createDemoRegion();
        Town town = createDemoTown(region);
        AdvertCreator advertCreator = new AdvertCreator();
        Advertisement demoAdvertisement = advertCreator.createDemoAdvertisement(subcategoryRepository, categoryRepository, userRepository, regionRepository, townRepository, advertisementRepository);

        mockMvc.perform(get("/adverts/edit").param("advertId", demoAdvertisement.getId()))
                .andExpect(view().name("base-layout"))
                .andExpect(model().attribute("view", "views/adverts/edit-advert"));
    }

    @Test
    @WithMockUser(value = "pesho", roles = {"MODERATOR"})
    public void testAdvertisementPOST() throws Exception {
        Region region = createDemoRegion();
        Town town = createDemoTown(region);
        AdvertCreator advertCreator = new AdvertCreator();
        Advertisement demoAdvertisement = advertCreator.createDemoAdvertisement(subcategoryRepository, categoryRepository, userRepository, regionRepository, townRepository, advertisementRepository);

        mockMvc.perform(MockMvcRequestBuilders.patch("/adverts/edit")
                .with(csrf())
                .param("id", demoAdvertisement.getId())
                .param("title", "someContent")
                .param("shipment", "PAID_BY_THE_SELLER")
                .param("region", "sliven")
                .param("town", "vraca")
                .param("state", "USED")
                .param("category", "Auto")
                .param("price", BigDecimal.TEN.toString())
                .param("subcategory", "Cjasd"));

        assertEquals(1, advertisementRepository.count());
        assertEquals("someContent", advertisementRepository.findAll().get(0).getTitle());
    }

    @Test
    @WithMockUser(value = "pesho", roles = {"MODERATOR"})
    public void testAddsToConfirm() throws Exception {
        mockMvc.perform(get("/adverts/adds-to-confirm"))
                .andExpect(view().name("base-layout"))
                .andExpect(model().attribute("view", "views/adverts/adverts-home"));
    }

    @Test
    @WithMockUser(value = "pesho", roles = {"MODERATOR"})
    public void confirmAdd() throws Exception {
        Region region = createDemoRegion();
        Town town = createDemoTown(region);
        AdvertCreator advertCreator = new AdvertCreator();
        Advertisement demoAdvertisement = advertCreator.createDemoAdvertisement(subcategoryRepository, categoryRepository, userRepository, regionRepository, townRepository, advertisementRepository);

        mockMvc.perform(MockMvcRequestBuilders.patch("/confirm-add")
                .with(csrf())
                .param("id", demoAdvertisement.getId()));

        assertTrue(advertisementRepository.findAll().get(0).getActive());
    }

    private Town createDemoTown(Region region) throws Exception {
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
