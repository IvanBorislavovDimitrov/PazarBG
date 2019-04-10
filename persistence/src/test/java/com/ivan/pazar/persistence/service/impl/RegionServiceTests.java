package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Region;
import com.ivan.pazar.persistence.model.service.RegionAddServiceModel;
import com.ivan.pazar.persistence.model.service.RegionServiceModel;
import com.ivan.pazar.persistence.model.service.rest.RegionRestServiceModel;
import com.ivan.pazar.persistence.repository.RegionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RegionServiceTests {

    @Mock
    private RegionRepository regionRepository;

    private ModelMapper modelMapper;
    private RegionServiceImpl regionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        modelMapper = new ModelMapper();
        regionService = new RegionServiceImpl(regionRepository, modelMapper);
    }

    @Test
    public void test_init_expectNewRegionAdded() {
        regionService.init();
        verify(regionRepository).save(any());
    }

    @Test
    public void test_save_regionSaved() {
        RegionAddServiceModel regionServiceModel = mock(RegionAddServiceModel.class);
        when(regionRepository.save(any())).thenReturn(mock(Region.class));
        regionService.save(regionServiceModel);
        verify(regionRepository).save(any());
    }

    @Test
    public void test_getRegionByTownName_regionFound() {
        Region region = mock(Region.class);
        String regionName = "Pazardzhik";
        when(region.getName()).thenReturn(regionName);
        when(regionRepository.findByTownName(anyString())).thenReturn(region);
        RegionServiceModel regionByTownName = regionService.getRegionByTownName(regionName);
        assertEquals(regionName, regionByTownName.getName());
    }

    @Test
    public void test_findByName_regionFound() {
        Region region = mock(Region.class);
        String regionName = "region";
        region.setName(regionName);
        when(regionRepository.findByName(anyString())).thenReturn(region);
        Region byName = regionService.findByName(regionName);
        assertEquals(region, byName);
    }

    @Test
    public void test_getAllRegionsRest_regionsFound() {
        Region region = mock(Region.class);
        when(regionRepository.findAll()).thenReturn(Arrays.asList(region));
        List<RegionRestServiceModel> allRegionsRest = regionService.getAllRegionsRest();
        assertEquals(1, allRegionsRest.size());
    }
}
