package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Log;
import com.ivan.pazar.persistence.model.service.LogServiceModel;
import com.ivan.pazar.persistence.repository.LogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LogServiceImplTests {

    private LogServiceImpl logService;
    private ModelMapper modelMapper;

    @Mock
    private LogRepository logRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        modelMapper = new ModelMapper();
        logService = new LogServiceImpl(logRepository, modelMapper);
    }

    @Test
    public void logService_save_logSaved() {
        LogServiceModel logServiceModel = mock(LogServiceModel.class);
        Log log = mock(Log.class);
        when(logRepository.save(any())).thenReturn(log);
        logService.save(logServiceModel);
        verify(logRepository).save(any());
    }

    @Test
    public void logService_deleteLogsOlderThanFiveDays_logsOlderThanFiveDaysAreDeleted() {
        logService.deleteLogsOlderThan5Days();
        verify(logRepository).deleteAllByDateBefore(any());
    }
}
