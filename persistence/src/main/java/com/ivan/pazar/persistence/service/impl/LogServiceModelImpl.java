package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Log;
import com.ivan.pazar.persistence.model.service.LogServiceModel;
import com.ivan.pazar.persistence.repository.LogRepository;
import com.ivan.pazar.persistence.service.api.LogService;
import com.ivan.pazar.persistence.service.service_api.LogServiceExtended;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class LogServiceModelImpl implements LogServiceExtended {

    private final LogRepository logRepository;
    private final ModelMapper modelMapper;

    public LogServiceModelImpl(LogRepository logRepository, ModelMapper modelMapper) {
        this.logRepository = logRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LogServiceModel save(LogServiceModel logServiceModel) {
        Log log = modelMapper.map(logServiceModel, Log.class);

        return modelMapper.map(logRepository.save(log), LogServiceModel.class);
    }
}
