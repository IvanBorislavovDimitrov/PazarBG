package com.ivan.pazar.web.controller.message;

import com.ivan.pazar.domain.model.entity.Advertisement;
import com.ivan.pazar.domain.model.entity.Message;
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
public class MessageControllerTests {

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
    private MessageRepository messageRepository;

    @Test
    @WithMockUser(value = "pesho", roles = {"MODERATOR"})
    public void testMessageController_sendMessage_messageSent() throws Exception {
        AdvertCreator advertCreator = new AdvertCreator();
        advertCreator.createDemoUser(regionRepository.findAll().get(0), townRepository.findAll().get(0), userRepository, "koko", "+1245613", "sdffd@adsf.dsg");

        Advertisement demoAdvertisement = advertCreator.createDemoAdvertisement(subcategoryRepository, categoryRepository, userRepository, regionRepository, townRepository, advertisementRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("/adverts/send-message")
                .with(csrf())
                .param("advertId", demoAdvertisement.getId())
                .param("content", "asd")
                .param("receiver", "koko"));

        assertEquals(1, messageRepository.count());
    }

    @Test
    @WithMockUser(value = "pesho", roles = {"MODERATOR"})
    public void testMessageController_reply_replied() throws Exception {
        AdvertCreator advertCreator = new AdvertCreator();
        advertCreator.createDemoUser(regionRepository.findAll().get(0), townRepository.findAll().get(0), userRepository, "koko", "+1245613", "sdffd@adsf.dsg");

        Advertisement demoAdvertisement = advertCreator.createDemoAdvertisement(subcategoryRepository, categoryRepository, userRepository, regionRepository, townRepository, advertisementRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("/adverts/send-message")
                .with(csrf())
                .param("advertId", demoAdvertisement.getId())
                .param("content", "asd")
                .param("receiver", "koko"));

        Message message = messageRepository.findAll().get(0);

        mockMvc.perform(get("/messages/reply")
                .param("messageId", message.getId())
                .param("sender", "pesho"))
                .andExpect(view().name("base-layout"))
                .andExpect(model().attribute("view", "views/messages/view-and-reply"));
    }

    @Test
    @WithMockUser(value = "pesho", roles = {"MODERATOR"})
    public void testMessageController_replyMessage_repliedConfirm() throws Exception {
        AdvertCreator advertCreator = new AdvertCreator();
        advertCreator.createDemoUser(regionRepository.findAll().get(0), townRepository.findAll().get(0), userRepository, "koko", "+1245613", "sdffd@adsf.dsg");

        Advertisement demoAdvertisement = advertCreator.createDemoAdvertisement(subcategoryRepository, categoryRepository, userRepository, regionRepository, townRepository, advertisementRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("/adverts/send-message")
                .with(csrf())
                .param("advertId", demoAdvertisement.getId())
                .param("content", "asd")
                .param("receiver", "koko"));

        mockMvc.perform(MockMvcRequestBuilders.post("/messages/reply")
                .with(csrf())
                .param("advertId", demoAdvertisement.getId())
                .param("sender", "koko")
                .param("receiver", "pesho")
                .param("content", "1231dsaf"))
                .andExpect(view().name("redirect:/users/profile"));

    }

    @Test
    @WithMockUser(value = "pesho", roles = {"MODERATOR"})
    public void testMessageController_deleteAdvert_advertHidden() throws Exception {
        AdvertCreator advertCreator = new AdvertCreator();
        advertCreator.createDemoUser(regionRepository.findAll().get(0), townRepository.findAll().get(0), userRepository, "koko", "+1245613", "sdffd@adsf.dsg");

        Advertisement demoAdvertisement = advertCreator.createDemoAdvertisement(subcategoryRepository, categoryRepository, userRepository, regionRepository, townRepository, advertisementRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("/adverts/send-message")
                .with(csrf())
                .param("advertId", demoAdvertisement.getId())
                .param("content", "asd")
                .param("receiver", "koko"));

        Message message = messageRepository.findAll().get(0);
        mockMvc.perform(MockMvcRequestBuilders.post("/messages/delete")
                .with(csrf())
                .param("messageId", message.getId()));

        assertTrue(messageRepository.findAll().get(0).isHidden());
    }
}
