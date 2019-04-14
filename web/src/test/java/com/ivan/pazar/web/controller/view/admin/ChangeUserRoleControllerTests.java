package com.ivan.pazar.web.controller.view.admin;

import com.ivan.pazar.domain.model.entity.Region;
import com.ivan.pazar.domain.model.entity.Role;
import com.ivan.pazar.domain.model.entity.Town;
import com.ivan.pazar.domain.model.entity.User;
import com.ivan.pazar.domain.model.enums.UserRole;
import com.ivan.pazar.persistence.repository.RegionRepository;
import com.ivan.pazar.persistence.repository.TownRepository;
import com.ivan.pazar.persistence.repository.UserRepository;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
public class ChangeUserRoleControllerTests {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private TownRepository townRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Test
    @WithMockUser(value = "pesho", roles = {"ADMIN", "ROOT"})
    public void changeUserRoleController_changeUserRole_roleChangeView() throws Exception {
        mockMvc.perform(get("/admin/change-user-role"))
                .andExpect(view().name("base-layout"))
                .andExpect(model().attribute("view", "views/admins/change-user-role"));
    }

    @Test
    @WithMockUser(value = "pesho", roles = {"ADMIN", "ROOT"})
    public void changeUserRoleController_changeUserRoleConfirm_roleChangeConfirmView() throws Exception {
        mockMvc.perform(get("/admin/change-user-role-confirm")
                .param("username", "user"))
                .andExpect(view().name("base-layout"))
                .andExpect(model().attribute("view", "views/admins/change-user-role-confirm"));
    }

    @Test
    @WithMockUser(value = "pesho", roles = {"ADMIN", "ROOT"})
    public void changeUserRoleController_changeUserRoleConfirmPost_roleChanged() throws Exception {

        createDemoUser();

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/change-user-role-confirm")
                .with(csrf())
                .param("username", "username")
                .param("roles", UserRole.ROLE_USER.toString()));

        User user = userRepository.findByUsername("username").orElse(null);
        List<Role> collect = new ArrayList<>(user.getRoles());
        assertEquals(1, collect.size());
        assertEquals(UserRole.ROLE_USER, collect.get(0).getUserRole());
    }

    private User createDemoUser() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("123123123");
        user.setActive(true);
        Region region = createDemoRegion();
        user.setRegion(region);
        user.setTown(createDemoTown(region));
        user.setRegisteredAt(LocalDateTime.now());
        user.setPhoneNumber("+123123");
        user.setEmail("asdasd@asdasd.asdasd");
        user.setFirstName("asdasd");
        user.setLastName("asdasd");
        userRepository.saveAndFlush(user);

        return user;
    }

    private Region createDemoRegion() {
        Region region = new Region();
        region.setName("Boshulq");

        regionRepository.saveAndFlush(region);

        return region;
    }

    private Town createDemoTown(Region region) {
        Town town = new Town();
        town.setName("Pazardzhik");
        town.setRegion(region);
        townRepository.saveAndFlush(town);

        return town;
    }
}
