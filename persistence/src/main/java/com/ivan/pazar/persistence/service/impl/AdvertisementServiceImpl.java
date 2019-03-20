package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.*;
import com.ivan.pazar.persistence.dao.advertisements.AdvertisementPicturesManager;
import com.ivan.pazar.persistence.model.service.AdvertisementAddServiceModel;
import com.ivan.pazar.persistence.model.service.AdvertisementServiceModel;
import com.ivan.pazar.persistence.repository.AdvertisementRepository;
import com.ivan.pazar.persistence.service.service_api.*;
import com.ivan.pazar.persistence.util.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdvertisementServiceImpl implements AdvertisementServiceExtended {

    private final AdvertisementRepository advertisementRepository;
    private final ModelMapper modelMapper;
    private final AdvertisementPicturesManager advertisementPicturesManager;
    private final UserServiceExtended userService;
    private final TownServiceExtended townService;
    private final CategoryServiceExtended categoryService;
    private final SubcategoryServiceExtended subcategoryService;


    public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository, ModelMapper modelMapper, AdvertisementPicturesManager advertisementPicturesManager, UserServiceExtended userService, TownServiceExtended townService, CategoryServiceExtended categoryService, SubcategoryServiceExtended subcategoryService) {
        this.advertisementRepository = advertisementRepository;
        this.modelMapper = modelMapper;
        this.advertisementPicturesManager = advertisementPicturesManager;
        this.userService = userService;
        this.townService = townService;
        this.categoryService = categoryService;
        this.subcategoryService = subcategoryService;
    }

    @Override
    public AdvertisementServiceModel save(String username, AdvertisementAddServiceModel advertisementAddServiceModel) {
        Advertisement advertisement = modelMapper.map(advertisementAddServiceModel, Advertisement.class);
        advertisement.setPictures(new ArrayList<>());
        advertisement.setVideo(null);
        User user = userService.getUserByUsername(username);
        Town town = townService.getTownByName(advertisementAddServiceModel.getTown());
        Category category = categoryService.getCategoryByName(advertisementAddServiceModel.getCategory());
        Subcategory subcategory = subcategoryService.getSubcategoryByName(advertisementAddServiceModel.getSubcategory());
        advertisement.setAuthor(user);
        advertisement.setTown(town);
        advertisement.setCategory(category);
        advertisement.setSubcategory(subcategory);
        advertisement.setActive(true);
        advertisement.setAddedOn(LocalDate.now());

        AdvertisementServiceModel advertisementServiceModel = modelMapper.map(advertisementRepository.saveAndFlush(advertisement), AdvertisementServiceModel.class);

        savePicturesInNewThread(() -> savePictures(advertisementServiceModel.getId(), advertisementAddServiceModel.getPhotos()));

        return advertisementServiceModel;
    }

    private void savePicturesInNewThread(Runnable runnable) {
        new Thread(runnable).start();
    }

    private void savePictures(String advertisementId, List<MultipartFile> pictures) {
        List<String> picturesNames = getPicturesNames(advertisementId, pictures);
        List<byte[]> picturesContents = getPicturesContents(pictures);
        try {
            advertisementPicturesManager.savePictures(picturesNames, picturesContents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<byte[]> getPicturesContents(List<MultipartFile> pictures) {
        return pictures.stream()
                .map(picture -> {
                    try {
                        return picture.getBytes();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }).collect(Collectors.toList());

    }

    private List<String> getPicturesNames(String advertisementId, List<MultipartFile> pictures) {
        AtomicInteger atomicInteger = new AtomicInteger();
        return pictures.stream()
                .map(picture -> {
                    atomicInteger.addAndGet(1);
                    return picture.getName() + "_" + atomicInteger.toString() + "_" + advertisementId + "." + Utils.getFileNameExtension(picture.getOriginalFilename());
                })
                .collect(Collectors.toList());
    }
}
