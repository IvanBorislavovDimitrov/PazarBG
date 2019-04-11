package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Region;
import com.ivan.pazar.domain.model.entity.Town;
import com.ivan.pazar.persistence.model.service.TownAddServiceModel;
import com.ivan.pazar.persistence.repository.TownRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TownServiceTests {

    @Mock
    private TownRepository townRepository;

    @Mock
    private RegionServiceImpl regionService;

    private TownServiceImpl townService;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        modelMapper = new ModelMapper();
        townService = new TownServiceImpl(townRepository, modelMapper, regionService);
    }

    @Test
    public void townService_init_townInitiated() {
        townService.init();
        verify(townRepository).save(any());
    }

    @Test
    public void townService_save_townIsSaved() {
        TownAddServiceModel townAddServiceModel = mock(TownAddServiceModel.class);
        when(townRepository.save(any())).thenReturn(mock(Town.class));
        townService.save(townAddServiceModel);
        verify(townRepository).save(any());
    }

    @Test
    public void testService_getAllByRegionRest_expectRegions() {
        when(townRepository.findAllByRegionNameLike(any())).thenReturn(Arrays.asList(mock(Town.class)));
        assertEquals(1, townService.getAllByRegionRest(any()).size());
    }

    @Test
    public void townService_findByName_expectFound() {
        String name = "123";
        Town town = mock(Town.class);
        when(townRepository.findByName(name)).thenReturn(town);
        assertEquals(townService.findByName(name), town);
    }
}
