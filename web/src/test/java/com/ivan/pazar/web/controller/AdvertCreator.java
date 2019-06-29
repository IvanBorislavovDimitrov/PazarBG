package com.ivan.pazar.web.controller;

import com.ivan.pazar.domain.model.entity.*;
import com.ivan.pazar.domain.model.enums.Shipment;
import com.ivan.pazar.domain.model.enums.State;
import com.ivan.pazar.persistence.repository.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AdvertCreator {

    public Advertisement createDemoAdvertisement(SubcategoryRepository subcategoryRepository, CategoryRepository categoryRepository,
                                                 UserRepository userRepository, RegionRepository regionRepository, TownRepository townRepository, AdvertisementRepository advertisementRepository) {
        Region region = createDemoRegion(regionRepository);
        Town town = createDemoTown(region, townRepository);
        Category category = createDemoCategory(categoryRepository);
        Subcategory subcategory = createDemoSubcategory(category, subcategoryRepository);
        Advertisement advertisement = new Advertisement();
        advertisement.setAuthor(createDemoUser(region, town, userRepository, null, null, null));
        advertisement.setActive(true);
        advertisement.setPrice(BigDecimal.TEN);
        advertisement.setState(State.NEW);
        advertisement.setDescription("123dasdfsadfdsafaf");
        advertisement.setTitle("Some title");
        advertisement.setCategory(category);
        advertisement.setSubcategory(subcategory);
        advertisement.setAddedOn(LocalDateTime.now());
        advertisement.setShipment(Shipment.PAID_BY_THE_BUYER);

        advertisementRepository.saveAndFlush(advertisement);

        return advertisement;
    }

    public Subcategory createDemoSubcategory(Category category, SubcategoryRepository subcategoryRepository) {
        Subcategory subcategory = new Subcategory();
        subcategory.setName("Cjasd");
        subcategory.setCategory(category);
        subcategoryRepository.saveAndFlush(subcategory);

        return subcategory;
    }

    public Category createDemoCategory(CategoryRepository categoryRepository) {
        Category category = new Category();
        category.setName("Auto");

        categoryRepository.saveAndFlush(category);

        return category;
    }

    public User createDemoUser(Region region, Town town, UserRepository userRepository, String name, String phoneNumber, String email) {
        User user = new User();

        user.setUsername("pesho");
        user.setPassword("123123123");
        user.setActive(true);
        user.setRegion(region);
        user.setTown(town);
        user.setRegisteredAt(LocalDateTime.now());

        user.setPhoneNumber("+123123");
        user.setEmail("asdasd@asdasd.asdasd");
        user.setFirstName("asdasd");
        user.setLastName("asdasd");
        user.setTown(town);

        if (name != null) {
            user.setUsername(name);
        }
        if (phoneNumber != null) {
            user.setPhoneNumber(phoneNumber);
        }
        if (email != null) {
            user.setEmail(email);
        }
        userRepository.saveAndFlush(user);

        return user;
    }

    public Region createDemoRegion(RegionRepository regionRepository) {
        Region region = new Region();
        region.setName("Boshulq1");

        regionRepository.saveAndFlush(region);

        return region;
    }

    public Town createDemoTown(Region region, TownRepository townRepository) {
        Town town = new Town();
        town.setName("Pazardzhik1");
        town.setRegion(region);
        townRepository.saveAndFlush(town);

        return town;
    }

}
