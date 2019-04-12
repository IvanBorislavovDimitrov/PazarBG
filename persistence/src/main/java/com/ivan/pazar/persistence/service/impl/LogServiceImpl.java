package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Log;
import com.ivan.pazar.persistence.constants.Messages;
import com.ivan.pazar.persistence.model.service.LogServiceModel;
import com.ivan.pazar.persistence.repository.LogRepository;
import com.ivan.pazar.persistence.service.service_api.LogServiceExtended;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogServiceImpl implements LogServiceExtended {

    private final Logger LOGGER = LoggerFactory.getLogger(LogServiceImpl.class);

    private static final int FIVE_DAYS = 5;

    private final LogRepository logRepository;
    private final ModelMapper modelMapper;

    public LogServiceImpl(LogRepository logRepository, ModelMapper modelMapper) {
        this.logRepository = logRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LogServiceModel save(LogServiceModel logServiceModel) {
        LOGGER.info(Messages.LOGGING_A_LOG);
        Log log = modelMapper.map(logServiceModel, Log.class);

        return modelMapper.map(logRepository.save(log), LogServiceModel.class);
    }

    @Override
    public void deleteLogsOlderThan5Days() {
        LOGGER.info(Messages.DELETING_OLD_LOGS);
        LocalDateTime before5Days = LocalDateTime.now().minusDays(FIVE_DAYS);

        logRepository.deleteAllByDateBefore(before5Days);
    }
}
