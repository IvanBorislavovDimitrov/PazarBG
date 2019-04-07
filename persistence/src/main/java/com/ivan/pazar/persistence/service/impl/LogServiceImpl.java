package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Log;
import com.ivan.pazar.persistence.model.service.LogServiceModel;
import com.ivan.pazar.persistence.repository.LogRepository;
import com.ivan.pazar.persistence.service.service_api.LogServiceExtended;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogServiceImpl implements LogServiceExtended {

    private static final int FIVE_DAYS = 5;

    private final LogRepository logRepository;
    private final ModelMapper modelMapper;

    public LogServiceImpl(LogRepository logRepository, ModelMapper modelMapper) {
        this.logRepository = logRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LogServiceModel save(LogServiceModel logServiceModel) {
        Log log = modelMapper.map(logServiceModel, Log.class);

        return modelMapper.map(logRepository.save(log), LogServiceModel.class);
    }

    @Override
    public void deleteLogsOlderThan5Days() {
        LocalDateTime before5Days = LocalDateTime.now().minusDays(FIVE_DAYS);

        logRepository.deleteAllByDateBefore(before5Days);
    }
}
