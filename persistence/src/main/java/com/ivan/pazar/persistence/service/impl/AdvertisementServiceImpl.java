package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.*;
import com.ivan.pazar.domain.model.enums.Shipment;
import com.ivan.pazar.domain.model.enums.State;
import com.ivan.pazar.persistence.constants.Messages;
import com.ivan.pazar.persistence.dao.advertisements.AdvertisementPicturesManager;
import com.ivan.pazar.persistence.dao.videos.VideoManager;
import com.ivan.pazar.persistence.exceptions.advertisement.AdvertisementNotFoundException;
import com.ivan.pazar.persistence.json.JsonParser;
import com.ivan.pazar.persistence.model.service.*;
import com.ivan.pazar.persistence.model.service.rest.AdvertisementRestServiceModel;
import com.ivan.pazar.persistence.repository.AdvertisementRepository;
import com.ivan.pazar.persistence.service.service_api.*;
import com.ivan.pazar.persistence.util.Utils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdvertisementServiceImpl implements AdvertisementServiceExtended {

    private static final int MAX_LENGTH_OF_DESCRIPTION = 45;
    private static final String DOTS = "...";

    private static final Logger LOGGER = LoggerFactory.getLogger(AdvertisementServiceImpl.class);

    private final AdvertisementRepository advertisementRepository;
    private final ModelMapper modelMapper;
    private final AdvertisementPicturesManager advertisementPicturesManager;
    private final UserServiceExtended userService;
    private final CategoryServiceExtended categoryService;
    private final SubcategoryServiceExtended subcategoryService;
    private final VideoServiceExtended videoService;
    private final VideoManager videoManager;
    private final RegionServiceExtended regionServiceExtended;
    private final JsonParser jsonParser;


    public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository, ModelMapper modelMapper, AdvertisementPicturesManager advertisementPicturesManager, UserServiceExtended userService, CategoryServiceExtended categoryService, SubcategoryServiceExtended subcategoryService, VideoServiceExtended videoService, VideoManager videoManager, RegionServiceExtended regionServiceExtended, JsonParser jsonParser) {
        this.advertisementRepository = advertisementRepository;
        this.modelMapper = modelMapper;
        this.advertisementPicturesManager = advertisementPicturesManager;
        this.userService = userService;
        this.categoryService = categoryService;
        this.subcategoryService = subcategoryService;
        this.videoService = videoService;
        this.videoManager = videoManager;
        this.regionServiceExtended = regionServiceExtended;
        this.jsonParser = jsonParser;
    }

    @Override
    public AdvertisementViewServiceModel findById(String id) {
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow(() -> new AdvertisementNotFoundException(Messages.ADVERTISEMENT_IS_NULL));

        AdvertisementViewServiceModel advertisementViewServiceModel = modelMapper.map(advertisement, AdvertisementViewServiceModel.class);
        RegionServiceModel regionServiceModel = regionServiceExtended.getRegionByTownName(advertisement.getAuthor().getTown().getName());

        advertisementViewServiceModel.setTown(advertisement.getAuthor().getTown().getName());
        advertisementViewServiceModel.setRegion(regionServiceModel.getName());
        advertisementViewServiceModel.setUserUsername(advertisement.getAuthor().getUsername());

        return advertisementViewServiceModel;
    }

    @Override
    public AdvertisementServiceModel save(String username, AdvertisementAddServiceModel advertisementAddServiceModel) {
        LOGGER.info(Messages.SAVING_ADVERTISEMENT);
        Advertisement advertisement = modelMapper.map(advertisementAddServiceModel, Advertisement.class);
        advertisement.setPictures(new ArrayList<>());
        advertisement.setVideo(null);
        User user = userService.getUserByUsername(username);
        Category category = categoryService.findCategoryByName(advertisementAddServiceModel.getCategory());
        Subcategory subcategory = subcategoryService.getSubcategoryByName(advertisementAddServiceModel.getSubcategory());
        advertisement.setAuthor(user);
        advertisement.setCategory(category);
        advertisement.setSubcategory(subcategory);
        advertisement.setAddedOn(LocalDateTime.now());

        AdvertisementServiceModel advertisementServiceModel = modelMapper.map(advertisementRepository.saveAndFlush(advertisement), AdvertisementServiceModel.class);

        saveVideoToAdvert(advertisementAddServiceModel, advertisement, advertisementServiceModel);

        LOGGER.info(Messages.ADVERTISEMENT_SAVED + jsonParser.toJson(advertisementServiceModel));

        return advertisementServiceModel;
    }

    @Override
    public List<AdvertisementRestServiceModel> findSixMostRecentAdvertisements() {
        return advertisementRepository.findTop6ByActiveOrderByAddedOnDesc(true).stream()
                .map(this::mapAdvertisement).collect(Collectors.toList());
    }

    @Override
    public AdvertisementPageServiceModel findAllByCategoryLikeWithPage(String categoryName, PageRequest pageRequest) {
        Page<Advertisement> advertisementPage = advertisementRepository.findAllByCategoryNameLikeAndActive(categoryName, pageRequest, true);

        AdvertisementPageServiceModel advertisementHomePageServiceModel = getAdvertisementHomePageServiceModel(advertisementPage);
        trimDescription(advertisementHomePageServiceModel);

        return advertisementHomePageServiceModel;
    }

    @Override
    public AdvertisementPageServiceModel findNonConfirmedAdvertisements(PageRequest pageRequest) {
        Page<Advertisement> advertisementPage = advertisementRepository.findAllByActive(false, pageRequest);

        return getAdvertisementHomePageServiceModel(advertisementPage);
    }

    @Override
    public void activateAdvertisement(String advertId) {
        Advertisement advertisement = advertisementRepository.findById(advertId).orElseThrow(() -> new AdvertisementNotFoundException(Messages.ADVERTISEMENT_IS_NULL));

        advertisement.setActive(true);
        advertisementRepository.save(advertisement);
    }

    @Override
    public AdvertisementPageServiceModel findByKeyword(String keyword, PageRequest pageRequest) {
        String keywordWithExtensions = "%" + keyword + "%";
        Page<Advertisement> advertisementPage = advertisementRepository.findAllByTitleLikeAndActiveIsTrue(keywordWithExtensions, pageRequest);

        return getAdvertisementHomePageServiceModel(advertisementPage);
    }

    @Override
    public void incrementViews(String id) {
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow(() -> new AdvertisementNotFoundException(Messages.ADVERTISEMENT_IS_NULL));

        advertisement.setViews(advertisement.getViews() + 1);
        advertisementRepository.saveAndFlush(advertisement);
    }

    @Override
    public void deleteById(String advertId) {
        Advertisement advertisement = advertisementRepository.findById(advertId).orElseThrow(() -> new AdvertisementNotFoundException(Messages.ADVERTISEMENT_IS_NULL));

        advertisementPicturesManager.deletePicturesIfExist(advertisement.getPictures());
        videoManager.deleteVideo(advertisement.getVideo() != null ? advertisement.getVideo().getName() : null);
        advertisementRepository.delete(advertisement);
    }

    @Override
    public AdvertisementPageServiceModel findAllByUsername(String username, PageRequest pageRequest) {
        Page<Advertisement> advertisementPage = advertisementRepository.findAllByAuthorUsernameAndActiveIsTrue(username, pageRequest);

        AdvertisementPageServiceModel advertisementHomePageServiceModel = getAdvertisementHomePageServiceModel(advertisementPage);
        trimDescription(advertisementHomePageServiceModel);

        return advertisementHomePageServiceModel;
    }

    private void trimDescription(AdvertisementPageServiceModel advertisementHomePageServiceModel) {
        advertisementHomePageServiceModel.getAdvertisementViewServiceModels().forEach(advertisementViewServiceModel -> {
            advertisementViewServiceModel.setDescription(advertisementViewServiceModel.getDescription().substring(0, Math.min(advertisementViewServiceModel.getDescription().length(), MAX_LENGTH_OF_DESCRIPTION)) + DOTS);
        });
    }

    @Override
    public void edit(AdvertisementAddServiceModel advertisementAddServiceModel) {
        Advertisement advertisement = advertisementRepository.findById(advertisementAddServiceModel.getId()).orElseThrow(() -> new AdvertisementNotFoundException(Messages.ADVERTISEMENT_IS_NULL));
        advertisement.setCategory(categoryService.findCategoryByName(advertisementAddServiceModel.getCategory()));
        advertisement.setSubcategory(subcategoryService.getSubcategoryByName(advertisementAddServiceModel.getSubcategory()));
        advertisement.setTitle(advertisementAddServiceModel.getTitle());
        advertisement.setShipment(Shipment.valueOf(advertisementAddServiceModel.getShipment()));
        advertisement.setPrice(advertisementAddServiceModel.getPrice());
        advertisement.setDescription(advertisementAddServiceModel.getDescription());
        advertisement.setState(State.valueOf(advertisementAddServiceModel.getState()));

        AdvertisementServiceModel advertisementServiceModel = modelMapper.map(advertisementRepository.saveAndFlush(advertisement), AdvertisementServiceModel.class);

        advertisementPicturesManager.deletePicturesIfExist(advertisement.getPictures());
        videoManager.deleteVideo(advertisement.getVideo() != null ? advertisement.getVideo().getName() : null);
        if (advertisement.getVideo() != null) {
            videoService.deleteById(advertisement.getVideo().getName());
        }

        advertisement.setVideo(null);
        advertisement.setPictures(Collections.emptyList());
        advertisementRepository.saveAndFlush(advertisement);

        saveVideoToAdvert(advertisementAddServiceModel, advertisement, advertisementServiceModel);
    }

    @Override
    public Advertisement getAdvertisementById(String id) {
        return advertisementRepository.findById(id).orElseThrow(() -> new AdvertisementNotFoundException(Messages.ADVERTISEMENT_IS_NULL));
    }

    private AdvertisementPageServiceModel getAdvertisementHomePageServiceModel(Page<Advertisement> advertisementPage) {
        List<AdvertisementViewServiceModel> advertisementViewServiceModels = advertisementPage.getContent().stream()
                .map(advertisement -> {
                    AdvertisementViewServiceModel advertisementViewServiceModel = modelMapper.map(advertisement, AdvertisementViewServiceModel.class);
                    advertisementViewServiceModel.setUserUsername(advertisement.getAuthor().getUsername());

                    return advertisementViewServiceModel;
                })
                .collect(Collectors.toList());

        AdvertisementPageServiceModel advertisementPageServiceModel = new AdvertisementPageServiceModel();
        advertisementPageServiceModel.setPages(advertisementPage.getTotalPages());
        advertisementPageServiceModel.setAdvertisementViewServiceModels(advertisementViewServiceModels);

        return advertisementPageServiceModel;
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
            LOGGER.error(Messages.ADVERTISEMENT_IS_NULL);
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
        executeInNewThread(() -> savePicturesAndVideoFile(picturesNames, photos));
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
            if (video.getBytes().length > 0) {
                videoManager.saveVideo(getVideoName(advertisementId, video), video.getBytes());
            }
        } catch (IOException e) {
            LOGGER.error(e.toString());
            e.printStackTrace();
        }
    }

    private String getVideoName(String advertisementId, MultipartFile video) {
        return "_" + advertisementId + "." + Utils.getFileNameExtension(video.getOriginalFilename());
    }

    private void savePicturesAndVideoFile(List<String> picturesNames, List<MultipartFile> pictures) {
        List<byte[]> picturesContents = getPicturesContents(pictures);
        try {
            advertisementPicturesManager.savePictures(picturesNames, picturesContents);
        } catch (IOException e) {
            LOGGER.error(e.toString());
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
                        LOGGER.error(e.toString());
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

    private String savePicturesAndVideoFile(AdvertisementAddServiceModel advertisementAddServiceModel, Advertisement advertisement, AdvertisementServiceModel advertisementServiceModel) {
        List<MultipartFile> photos = advertisementAddServiceModel.getPhotos();
        String advertisementServiceModelId = advertisementServiceModel.getId();

        savePicturesIfNeeded(advertisementServiceModelId, advertisementAddServiceModel, advertisementServiceModel, photos, advertisement);
        return advertisementServiceModelId;
    }

    private void saveVideoToAdvert(AdvertisementAddServiceModel advertisementAddServiceModel, Advertisement advertisement, AdvertisementServiceModel advertisementServiceModel) {
        String advertisementServiceModelId = savePicturesAndVideoFile(advertisementAddServiceModel, advertisement, advertisementServiceModel);

        Video videoEntity = saveVideo(advertisementAddServiceModel, advertisement, advertisementServiceModelId);

        advertisementRepository.saveAndFlush(advertisement);
        if (videoEntity != null) {
            videoEntity.setAdvertisement(advertisement);
            videoService.updateVideo(videoEntity);
        }
    }
}