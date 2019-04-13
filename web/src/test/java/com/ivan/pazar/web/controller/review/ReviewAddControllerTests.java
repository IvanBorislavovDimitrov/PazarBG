package com.ivan.pazar.web.controller.review;

import com.ivan.pazar.domain.model.entity.Advertisement;
import com.ivan.pazar.persistence.repository.*;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReviewAddControllerTests extends ReviewBaseMethods {

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

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @WithMockUser(value = "pesho", roles = {"USER"})
    public void testReviewAddController_addReview_reviewAdded() throws Exception {

        Advertisement demoAdvertisement = createDemoAdvertisement(subcategoryRepository, categoryRepository, userRepository, regionRepository, townRepository, advertisementRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("/reviews/add")
                .with(csrf())
                .param("text", "someContent")
                .param("advertId", demoAdvertisement.getId()));

        assertEquals(1, reviewRepository.count());
    }

}
