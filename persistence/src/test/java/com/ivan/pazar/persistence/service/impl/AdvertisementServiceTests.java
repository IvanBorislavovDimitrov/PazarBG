package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.*;
import com.ivan.pazar.domain.model.enums.Shipment;
import com.ivan.pazar.domain.model.enums.State;
import com.ivan.pazar.persistence.dao.advertisements.AdvertisementPicturesManager;
import com.ivan.pazar.persistence.dao.videos.VideoManager;
import com.ivan.pazar.persistence.json.JsonParser;
import com.ivan.pazar.persistence.model.service.AdvertisementAddServiceModel;
import com.ivan.pazar.persistence.model.service.AdvertisementPageServiceModel;
import com.ivan.pazar.persistence.model.service.AdvertisementViewServiceModel;
import com.ivan.pazar.persistence.model.service.RegionServiceModel;
import com.ivan.pazar.persistence.repository.AdvertisementRepository;
import com.ivan.pazar.persistence.service.api.AdvertisementService;
import com.ivan.pazar.persistence.service.service_api.*;
import com.sun.org.apache.xml.internal.utils.MutableAttrListImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AdvertisementServiceTests {

    private static final String FAKE_ADVERT_ID = "123";

    @Mock
    private AdvertisementRepository advertisementRepository;
    private ModelMapper modelMapper;
    @Mock
    private AdvertisementPicturesManager advertisementPicturesManager;
    @Mock
    private UserServiceExtended userService;
    @Mock
    private CategoryServiceExtended categoryService;
    @Mock
    private SubcategoryServiceExtended subcategoryService;
    @Mock
    private VideoServiceExtended videoService;
    @Mock
    private VideoManager videoManager;
    @Mock
    private RegionServiceExtended regionServiceExtended;
    @Mock
    private JsonParser jsonParser;
    private AdvertisementServiceImpl advertisementService;

    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();
        MockitoAnnotations.initMocks(this);
        advertisementService = new AdvertisementServiceImpl(advertisementRepository, modelMapper, advertisementPicturesManager, userService, categoryService, subcategoryService, videoService, videoManager, regionServiceExtended, jsonParser);
    }

    @Test
    public void advertisementService_findById_expectAdvertisementIsFound() {
        Advertisement advertisement = new Advertisement();
        String title = "title";
        String description = "some_desc";
        State state = State.NEW;
        BigDecimal ten = BigDecimal.TEN;
        String townName = "Slien";
        String regionName = "Pernik";
        advertisement.setPrice(ten);
        advertisement.setTitle(title);
        advertisement.setDescription(description);
        advertisement.setState(state);
        User author = mock(User.class);
        Region region = mock(Region.class);
        Town town = mock(Town.class);
        when(town.getName()).thenReturn(townName);
        when(author.getTown()).thenReturn(town);
        RegionServiceModel regionServiceModel = mock(RegionServiceModel.class);
        when(regionServiceModel.getName()).thenReturn(regionName);
        when(regionServiceModel.getName()).thenReturn(townName);
        when(regionServiceExtended.getRegionByTownName(townName)).thenReturn(regionServiceModel);
        when(author.getRegion()).thenReturn(region);
        advertisement.setAuthor(author);
        Optional<Advertisement> optionalAdvertisement = Optional.of(advertisement);
        when(regionServiceExtended.findByName(any())).thenReturn(region);
        when(advertisementRepository.findById(any())).thenReturn(optionalAdvertisement);
        AdvertisementViewServiceModel advertisementViewServiceModel = advertisementService.findById(FAKE_ADVERT_ID);
        assertEquals(title, advertisementViewServiceModel.getTitle());
        assertEquals(description, advertisementViewServiceModel.getDescription());
        assertEquals(state.toString(), advertisementViewServiceModel.getState());
        assertEquals(ten, advertisementViewServiceModel.getPrice());
    }

    @Test
    public void advertisementService_saveAdvertisement_expectSaveIsInvoked() {
        AdvertisementAddServiceModel advertisementAddServiceModel = mock(AdvertisementAddServiceModel.class);
        String fakeUserUsername = "fakeUser";
        User user = mock(User.class);
        Category category = mock(Category.class);
        Subcategory subcategory = mock(Subcategory.class);
        when(userService.getUserByUsername(anyString())).thenReturn(user);
        when(categoryService.findCategoryByName(any())).thenReturn(category);
        when(subcategoryService.findSubcategoryByName(anyString())).thenReturn(subcategory);
        Advertisement advertisement = mock(Advertisement.class);

        when(advertisementRepository.saveAndFlush(any())).thenReturn(advertisement);

        advertisementService.save(fakeUserUsername, advertisementAddServiceModel);
        verify(advertisementRepository, times(2)).saveAndFlush(any());
    }

    @Test
    public void advertisementService_findSixMostRecentAdvertisement_expectSixMostRecentAdvertisements() {
        when(advertisementRepository.findTop6ByActiveOrderByAddedOnDesc(true)).thenReturn(get6DummyAdvertisements());
        assertEquals(6, advertisementService.findSixMostRecentAdvertisements().size());
    }

    @Test
    public void advertisementService_findAllByCategoryLike_expectAllByCategoryLike() {
        Page<Advertisement> page = mock(Page.class);
        when(page.getTotalPages()).thenReturn(1);
        List<Advertisement> dummyAdvertisements = get6DummyAdvertisements();
        when(page.getContent()).thenReturn(dummyAdvertisements);
        when(advertisementRepository.findAllByCategoryNameLikeAndActive(anyString(), any(), anyBoolean()))
                .thenReturn(page);
        AdvertisementPageServiceModel advertisementPageServiceModel = advertisementService
                .findAllByCategoryLikeWithPage(anyString(), eq(any()));
        assertEquals(1, advertisementPageServiceModel.getPages());
        assertEquals(6, advertisementPageServiceModel.getAdvertisementViewServiceModels().size());
    }

    @Test
    public void advertisementService_findAllNonConfirmedAdvertisements_expectAllNonConfirmedAdvertisements() {
        Page<Advertisement> page = mock(Page.class);
        when(page.getTotalPages()).thenReturn(1);
        when(page.getContent()).thenReturn(get6DummyAdvertisements());
        PageRequest pageRequest = PageRequest.of(1, 1);
        when(advertisementRepository.findAllByActive(false, pageRequest)).thenReturn(page);
        AdvertisementPageServiceModel nonConfirmedAdvertisements = advertisementService
                .findNonConfirmedAdvertisements(pageRequest);
        assertEquals(1, nonConfirmedAdvertisements.getPages());
        assertEquals(6, nonConfirmedAdvertisements.getAdvertisementViewServiceModels().size());
    }

    @Test
    public void advertisementService_activateAdvert_advertActivated() {
        Advertisement advertisement = mock(Advertisement.class);
        Optional<Advertisement> optionalAdvertisement = Optional.of(advertisement);
        when(advertisementRepository.findById(anyString())).thenReturn(optionalAdvertisement);
        advertisementService.activateAdvertisement("id");
        verify(advertisement).setActive(true);
    }

    @Test
    public void advertisementService_findByKeyword_foundByKeyword() {
        Page<Advertisement> page = mock(Page.class);
        when(page.getTotalPages()).thenReturn(1);
        when(page.getContent()).thenReturn(get6DummyAdvertisements());
        PageRequest pageRequest = PageRequest.of(1, 1);
        String keyword = "keyworkd";
        when(advertisementRepository.findAllByTitleLikeAndActiveIsTrue("%" + keyword + "%", pageRequest)).thenReturn(page);
        AdvertisementPageServiceModel advertisementServiceByKeyword = advertisementService
                .findByKeyword(keyword, pageRequest);
        assertEquals(1, advertisementServiceByKeyword.getPages());
        assertEquals(6, advertisementServiceByKeyword.getAdvertisementViewServiceModels().size());
    }

    @Test
    public void advertisementService_incrementViews_viewsIncremented() {
        Advertisement advertisement = mock(Advertisement.class);
        Optional<Advertisement> optionalAdvertisement = Optional.of(advertisement);
        when(advertisementRepository.findById(anyString())).thenReturn(optionalAdvertisement);
        advertisementService.incrementViews("id");
        verify(advertisement).setViews(anyInt());
        verify(advertisementRepository).saveAndFlush(any());
    }

    @Test
    public void advertisementService_deleteById_advertIsDeleted() {
        Advertisement advertisement = mock(Advertisement.class);
        when(advertisement.getPictures()).thenReturn(Collections.emptyList());
        Optional<Advertisement> optionalAdvertisement = Optional.of(advertisement);
        when(advertisementRepository.findById(anyString())).thenReturn(optionalAdvertisement);
        advertisementService.deleteById("id");
        verify(advertisementRepository).delete(advertisement);
    }

    @Test
    public void advertisementService_findAllByUsername_allByUsernameFound() {
        Page<Advertisement> page = mock(Page.class);
        when(page.getTotalPages()).thenReturn(1);
        when(page.getContent()).thenReturn(get6DummyAdvertisements());
        PageRequest pageRequest = PageRequest.of(1, 1);
        String username = "username";
        when(advertisementRepository.findAllByAuthorUsernameAndActiveIsTrue(username, pageRequest)).thenReturn(page);
        AdvertisementPageServiceModel advertisementServiceByKeyword = advertisementService
                .findAllByUsername(username, pageRequest);
        assertEquals(1, advertisementServiceByKeyword.getPages());
        assertEquals(6, advertisementServiceByKeyword.getAdvertisementViewServiceModels().size());
    }

    @Test
    public void advertisementService_editAdvertisement_advertisementIsEdited() {
        AdvertisementAddServiceModel advertisementAddServiceModel = mock(AdvertisementAddServiceModel.class);
        when(advertisementAddServiceModel.getId()).thenReturn("id");
        when(advertisementAddServiceModel.getShipment()).thenReturn(Shipment.PAID_BY_THE_BUYER.name());
        when(advertisementAddServiceModel.getState()).thenReturn(State.NEW.name());
        Advertisement advertisement = mock(Advertisement.class);
        when(advertisement.getPictures()).thenReturn(Collections.emptyList());
        when(advertisement.getState()).thenReturn(State.NEW);
        when(advertisement.getShipment()).thenReturn(Shipment.PAID_BY_THE_BUYER);
        Optional<Advertisement> optionalAdvertisement = Optional.of(advertisement);
        when(advertisementRepository.findById(anyString())).thenReturn(optionalAdvertisement);
        when(advertisementRepository.saveAndFlush(advertisement)).thenReturn(advertisement);

        advertisementService.edit(advertisementAddServiceModel);
        verify(advertisementRepository, times(3)).saveAndFlush(any());
    }

    @Test
    public void advertisementService_getAdvertisementById_advertisement() {
        Advertisement advertisement = mock(Advertisement.class);
        when(advertisement.getPictures()).thenReturn(Collections.emptyList());
        Optional<Advertisement> optionalAdvertisement = Optional.of(advertisement);
        when(advertisementRepository.findById(anyString())).thenReturn(optionalAdvertisement);
        advertisementService.getAdvertisementById("id");
        verify(advertisementRepository).findById(anyString());
    }

    @Test
    public void advertisementService_getVideoName_videoName() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getVideoName = advertisementService.getClass().getDeclaredMethod("getVideoName", String.class, MultipartFile.class);
        getVideoName.setAccessible(true);
        String advertId = "id";
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn("file.mp4");
        Object invoke = getVideoName.invoke(advertisementService, advertId, multipartFile);
        assertEquals("_id.mp4", invoke.toString());
    }

    @Test
    public void advertisementService_saveVideo_videoSaved() throws NoSuchMethodException, IOException, InvocationTargetException, IllegalAccessException {
        Method saveVideo = advertisementService.getClass().getDeclaredMethod("saveVideo", String.class, MultipartFile.class);
        saveVideo.setAccessible(true);
        String advertId = "id";
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn("file.mp4");
        when(multipartFile.getBytes()).thenReturn(new byte[200]);

        saveVideo.invoke(advertisementService, advertId, multipartFile);
        verify(videoManager).saveVideo(anyString(), any());
    }

    private List<Advertisement> get6DummyAdvertisements() {
        return Arrays.asList(mock(Advertisement.class), mock(Advertisement.class), mock(Advertisement.class), mock(Advertisement.class), mock(Advertisement.class), mock(Advertisement.class));
    }


}
