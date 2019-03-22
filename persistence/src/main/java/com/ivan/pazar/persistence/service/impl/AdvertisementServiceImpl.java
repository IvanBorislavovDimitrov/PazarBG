package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.*;
import com.ivan.pazar.persistence.dao.advertisements.AdvertisementPicturesManager;
import com.ivan.pazar.persistence.dao.videos.VideoManager;
import com.ivan.pazar.persistence.model.service.AdvertisementAddServiceModel;
import com.ivan.pazar.persistence.model.service.AdvertisementServiceModel;
import com.ivan.pazar.persistence.model.service.VideoAddServiceModel;
import com.ivan.pazar.persistence.model.service.VideoServiceModel;
import com.ivan.pazar.persistence.model.service.rest.AdvertisementRestServiceModel;
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
    private final VideoServiceExtended videoService;
    private final VideoManager videoManager;


    public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository, ModelMapper modelMapper, AdvertisementPicturesManager advertisementPicturesManager, UserServiceExtended userService, TownServiceExtended townService, CategoryServiceExtended categoryService, SubcategoryServiceExtended subcategoryService, VideoServiceExtended videoService, VideoManager videoManager) {
        this.advertisementRepository = advertisementRepository;
        this.modelMapper = modelMapper;
        this.advertisementPicturesManager = advertisementPicturesManager;
        this.userService = userService;
        this.townService = townService;
        this.categoryService = categoryService;
        this.subcategoryService = subcategoryService;
        this.videoService = videoService;
        this.videoManager = videoManager;
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

        List<MultipartFile> photos = advertisementAddServiceModel.getPhotos();
        String advertisementServiceModelId = advertisementServiceModel.getId();

        savePicturesIfNeeded(advertisementServiceModelId, advertisementAddServiceModel, advertisementServiceModel, photos, advertisement);

        Video videoEntity = saveVideo(advertisementAddServiceModel, advertisement, advertisementServiceModelId);

        advertisementRepository.saveAndFlush(advertisement);
        if (videoEntity != null) {
            videoEntity.setAdvertisement(advertisement);
            videoService.updateVideo(videoEntity);
        }
        return advertisementServiceModel;
    }

    @Override
    public List<AdvertisementRestServiceModel> findSixMostRecentAdvertisements() {
        return advertisementRepository.findTop6ByOrderByAddedOnDesc().stream()
                .map(advertisement -> {
                    AdvertisementRestServiceModel advertisementRestServiceModel = modelMapper.map(advertisement, AdvertisementRestServiceModel.class);
                    advertisementRestServiceModel.setPicture(getLastAdvertisementPicture(advertisement.getPictures()));
                    advertisementRestServiceModel.setUserRating(advertisement.getAuthor().getRating());

                    return advertisementRestServiceModel;
                }).collect(Collectors.toList());
    }

    private Video saveVideo(AdvertisementAddServiceModel advertisementAddServiceModel, Advertisement advertisement, String advertisementServiceModelId) {
        if (advertisementAddServiceModel.getVideo().getSize() == 0) {
            return null;
        }
        String videoName = getVideoName(advertisementServiceModelId, advertisementAddServiceModel.getVideo());
        VideoAddServiceModel videoAddServiceModel = new VideoAddServiceModel();
        videoAddServiceModel.setName(videoName);
        VideoServiceModel videoServiceModel = videoService.save(videoAddServiceModel);
        Video videoEntity = videoService.findById(videoServiceModel.getId());
        advertisement.setVideo(videoEntity);
        return videoEntity;
    }

    private void savePicturesIfNeeded(String advertisementServiceModelId, AdvertisementAddServiceModel advertisementAddServiceModel, AdvertisementServiceModel advertisementServiceModel, List<MultipartFile> photos, Advertisement advertisement) {
        if (photos.get(0).getSize() == 0) {
            return;
        }
        List<String> picturesNames = getPicturesNames(advertisementServiceModelId, advertisementAddServiceModel.getPhotos());
        executeInNewThread(() -> savePictures(picturesNames, advertisementServiceModelId, photos));
        executeInNewThread(() -> saveVideo(advertisementServiceModel.getId(), advertisementAddServiceModel.getVideo()));
        advertisement.setPictures(picturesNames);
    }

    private String getLastAdvertisementPicture(List<String> advertisementPictures) {
        return advertisementPictures.isEmpty() ? null : advertisementPictures.get(0);
    }

    private void executeInNewThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void saveVideo(String advertisementId, MultipartFile video) {
        try {
            videoManager.saveVideo(getVideoName(advertisementId, video), video.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getVideoName(String advertisementId, MultipartFile video) {
        return "_" + advertisementId + "." + Utils.getFileNameExtension(video.getOriginalFilename());
    }

    private void savePictures(List<String> picturesNames, String advertisementId, List<MultipartFile> pictures) {
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
                    return "_" + atomicInteger.toString() + "_" + advertisementId + "." + Utils.getFileNameExtension(picture.getOriginalFilename());
                })
                .collect(Collectors.toList());
    }
}
