package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.*;
import com.ivan.pazar.persistence.dao.advertisements.AdvertisementPicturesManager;
import com.ivan.pazar.persistence.dao.videos.VideoManager;
import com.ivan.pazar.persistence.model.service.*;
import com.ivan.pazar.persistence.model.service.rest.AdvertisementRestServiceModel;
import com.ivan.pazar.persistence.repository.AdvertisementRepository;
import com.ivan.pazar.persistence.service.service_api.*;
import com.ivan.pazar.persistence.util.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdvertisementServiceImpl implements AdvertisementServiceExtended {

    private static final int MAX_LENGTH_OF_DESCRIPTION = 45;

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
    public AdvertisementViewServiceModel findById(String id) {
        Advertisement advertisement = advertisementRepository.findById(id).orElse(null);
        if (advertisement == null) {
            throw new IllegalStateException();
        }

        return modelMapper.map(advertisement, AdvertisementViewServiceModel.class);
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
        advertisement.setAddedOn(LocalDateTime.now());

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
        return advertisementRepository.findTop6ByActiveOrderByAddedOnDesc(true).stream()
                .map(this::mapAdvertisement).collect(Collectors.toList());
    }

    @Override
    public AdvertisementHomePageServiceModel findAllByCategoryLikeWithPage(String categoryName, PageRequest pageRequest) {
        Page<Advertisement> advertisementPage = advertisementRepository.findAllByCategoryNameLikeAndActive(categoryName, pageRequest, true);

        return getAdvertisementHomePageServiceModel(advertisementPage);
    }

    @Override
    public AdvertisementHomePageServiceModel findNonConfirmedAdvertisements(PageRequest pageRequest) {
        Page<Advertisement> advertisementPage = advertisementRepository.findAllByActive(false, pageRequest);

        return getAdvertisementHomePageServiceModel(advertisementPage);
    }

    @Override
    public void activateAdvertisement(String advertId) {
        Advertisement advertisement = advertisementRepository.findById(advertId).orElse(null);
        if (advertisement == null) {
            return;
        }
        advertisement.setActive(true);
        advertisementRepository.save(advertisement);
    }

    @Override
    public AdvertisementHomePageServiceModel findByKeyword(String keyword, PageRequest pageRequest) {
        String keywordWithExtensions = "%" + keyword + "%";
        Page<Advertisement> advertisementPage = advertisementRepository.findAllByTitleLike(keywordWithExtensions, pageRequest);

        return getAdvertisementHomePageServiceModel(advertisementPage);
    }

    @Override
    public Advertisement getAdvertisementById(String id) {
        return advertisementRepository.findById(id).orElse(null);
    }

    private AdvertisementHomePageServiceModel getAdvertisementHomePageServiceModel(Page<Advertisement> advertisementPage) {
        List<AdvertisementViewServiceModel> advertisementViewServiceModels = advertisementPage.getContent().stream()
                .map(advertisement -> {
                    AdvertisementViewServiceModel advertisementViewServiceModel = modelMapper.map(advertisement, AdvertisementViewServiceModel.class);
                    advertisementViewServiceModel.setUserUsername(advertisement.getAuthor().getUsername());

                    return advertisementViewServiceModel;
                })
                .collect(Collectors.toList());

        AdvertisementHomePageServiceModel advertisementHomePageServiceModel = new AdvertisementHomePageServiceModel();
        advertisementHomePageServiceModel.setPages(advertisementPage.getTotalPages());
        advertisementHomePageServiceModel.setAdvertisementViewServiceModels(advertisementViewServiceModels);

        return advertisementHomePageServiceModel;
    }

    private AdvertisementRestServiceModel mapAdvertisement(Advertisement advertisement) {
        AdvertisementRestServiceModel advertisementRestServiceModel = modelMapper.map(advertisement, AdvertisementRestServiceModel.class);
        advertisementRestServiceModel.setDescription(advertisement.getDescription().substring(0, Math.min(MAX_LENGTH_OF_DESCRIPTION, advertisement.getDescription().length())));
        advertisementRestServiceModel.setPicture(getLastAdvertisementPicture(advertisement.getPictures()));
        if (advertisement.getVideo() != null) {
            advertisementRestServiceModel.setVideo(advertisement.getVideo().getName());
        }
        advertisementRestServiceModel.setUserUsername(advertisement.getAuthor().getUsername());

        return advertisementRestServiceModel;
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
        executeInNewThread(() -> savePictures(picturesNames, photos));
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

    private void savePictures(List<String> picturesNames, List<MultipartFile> pictures) {
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